package io.github.sykoram.sis

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    lateinit var refreshLayout: SwipeRefreshLayout

    var pageFinished = true

    /* The first path part after "is.cuni.cz/studium/" (ev. with ".php" removed):
         index, rozvrhng, predmety, term_st2, predm_st2, szz_st, zkous_st, anketa, dipl_st, grupicek, omne, role (...)
       This will be added as a class to <body>. */
    var pageIdentifier = ""

    var webClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            if (isUrlAllowed(request.url)) {
                return false
            }

            startActivity(Intent(Intent.ACTION_VIEW, request.url))
            return true
        }

        override fun onPageStarted(view: WebView, url: String, bitmap: Bitmap?) {
            super.onPageStarted(view, url, bitmap)

            pageFinished = false

            if (!refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = true
            }

            pageIdentifier = getPageIdentifier(url)
            view.evaluateJavascript("document.body.classList.add('$pageIdentifier');", null)

            resources.openRawResource(R.raw.script0)
                    .use { ins -> view.evaluateJavascript(readFileToString(ins), null) }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            pageIdentifier = getPageIdentifier(url)
            view.evaluateJavascript("document.body.classList.add('$pageIdentifier');", null)

            resources.openRawResource(R.raw.script0)
                    .use{ ins -> view.evaluateJavascript(readFileToString(ins), null) }
            resources.openRawResource(R.raw.script1)
                    .use{ ins -> view.evaluateJavascript(readFileToString(ins), null) }

            pageFinished = true
            onPageLoaded(view)
        }
    }

    var webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress < 100 && !refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = true
            }
            if (newProgress == 100 && pageFinished) {
                onPageLoaded(view)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        webView.webViewClient = webClient
        webView.webChromeClient = webChromeClient
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptEnabled = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.visibility = View.GONE
        webView.loadUrl("https://is.cuni.cz/studium")

        refreshLayout = findViewById(R.id.swipeRefreshLayout)
        refreshLayout.setColorSchemeResources(R.color.primary_blue)
        refreshLayout.isEnabled = false
    }

    fun onPageLoaded(view: WebView) {
        refreshLayout.isRefreshing = false
        if (view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        }

        CookieManager.getInstance().flush()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}