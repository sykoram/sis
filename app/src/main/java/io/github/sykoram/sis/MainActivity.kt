package io.github.sykoram.sis

import android.app.DownloadManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    lateinit var refreshLayout: SwipeRefreshLayout

    var pageFinished = true

    val webClient: WebViewClient = object : WebViewClient() {
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

            addPageIdentifiersToBody(view, url)

            resources.openRawResource(R.raw.script0)
                    .use { ins -> view.evaluateJavascript(readFileToString(ins), null) }
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            addPageIdentifiersToBody(view, url)

            resources.openRawResource(R.raw.script0)
                    .use{ ins -> view.evaluateJavascript(readFileToString(ins), null) }
            resources.openRawResource(R.raw.script1)
                    .use{ ins -> view.evaluateJavascript(readFileToString(ins), null) }

            pageFinished = true
            onPageLoaded(view)
        }

        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return replaceRequestWithAssets(request, assets) ?: super.shouldInterceptRequest(view, request)
        }
    }

    val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress < 100 && !refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = true
            }
            if (newProgress == 100 && pageFinished) {
                onPageLoaded(view)
            }
        }
    }

    val downloadListener: DownloadListener = object : DownloadListener {
        override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?, contentLength: Long) {
            val request = DownloadManager.Request(Uri.parse(url))
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setMimeType(mimeType)
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType))
            (getSystemService(DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)

            Toast.makeText(applicationContext, R.string.downloading_file, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // open drawer on first run
        val settings = getSharedPreferences("SharedPrefsFile", 0)
        if (settings.getBoolean("first_run", true)) {
            findViewById<DrawerLayout>(R.id.drawerLayout).openDrawer(GravityCompat.START, true)
            settings.edit().putBoolean("first_run", false).apply()
        }

        webView = findViewById(R.id.webView)
        webView.webViewClient = webClient
        webView.webChromeClient = webChromeClient
        webView.setDownloadListener(downloadListener)
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptEnabled = true
        webView.visibility = View.GONE
        webView.loadUrl("https://is.cuni.cz/studium/index.php")

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

    fun openPageExternally(view: View) {
        if (webView.url != null) {
            startActivity(Intent(Intent.ACTION_VIEW, webView.url!!.toUri()))
            findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawers()
        }
    }

    fun refreshPage(view: View) {
        webView.reload()
        findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawers()
    }
}