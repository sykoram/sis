package io.github.sykoram.sis

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.net.URLConnection

class MainActivity : AppCompatActivity() {
    lateinit var webView: WebView

    lateinit var refreshLayout: SwipeRefreshLayout

    var pageFinished = true

    /* The first path part after "is.cuni.cz/studium/" (ev. with ".php" removed):
         index, rozvrhng, predmety, term_st2, predm_st2, szz_st, zkous_st, anketa, dipl_st, grupicek, omne, role (...)
       This will be added as a class to <body>. */
    var pageIdentifier = ""

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

        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
            return replaceRequestWith(request) ?: super.shouldInterceptRequest(view, request)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webView)
        webView.webViewClient = webClient
        webView.webChromeClient = webChromeClient
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.javaScriptEnabled = true
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

    private val imageAssetsReplacements = mapOf(
            // header icons
            "stev_home.gif" to "openmoji-black/E25E.png",
            "stev_settings.gif" to "openmoji-black/2699.png",
            "stev_login.gif" to "openmoji-black/1F513.png",
            "stev_logoff.gif" to "openmoji-black/1F512.png",
            "stev_en.gif" to "openmoji-color/1F1EC-1F1E7.png",
            "stev_cz.gif" to "openmoji-color/1F1E8-1F1FF.png",
            "stev_help.gif" to "openmoji-black/2754.png",
            "stev_menu_home.gif" to "openmoji-black/E25E.png",
            "stev_menu_bookmarks.gif" to "openmoji-black/1F516.png",
            // homepage icons
            "v4.gif" to "openmoji-color/0034-FE0F-20E3.png",
            "term_st2.gif" to "openmoji-color/1F4C5.png",
            "szz_st.gif" to "openmoji-color/1F393.png",
            "predm_st2.gif" to "openmoji-color/1F4DD.png",
            "predmety.gif" to "openmoji-color/269B.png",
            "grupicek.gif" to "openmoji-color/1F4C8.png",
            "dipl_st.gif" to "openmoji-color/1F4DC.png",
            "anketa.gif" to "openmoji-color/2705.png",
            "zkous_st.gif" to "openmoji-color/1F4D2.png",
            "rozvrhng.gif" to "openmoji-color/E0AB.png",
            "prijimacky.gif" to "openmoji-color/E266.png",
            "prihlastaz.gif" to "openmoji-color/1F4C3.png",
            "publikace.gif" to "openmoji-color/1F4F0.png",
            "rozcestnik.gif" to "openmoji-color/E094.png",
            "komise.gif" to "openmoji-color/1F465.png",
            "szz.gif" to "openmoji-color/1F393.png",
            "deda_amu.gif" to "openmoji-color/1F30D.png",
            "ckis.gif" to "openmoji-color/1F4DA.png",
            "pez.gif" to "openmoji-color/E1CC.png",
            "ukaz.gif" to "icons/ukaz.png",
            "moodle.gif" to "icons/moodle.png",
            "iforum.gif" to "icons/iforum.png",
            "sis_uk_point.gif" to "icons/uk_point.png",
            "sis_centrum_carolina.gif" to "icons/centrum_carolina.png",
            "sis_phd_platform.gif" to "icons/phd_platform.png",
            "sis_klub_alumni.gif" to "icons/klub_alumni.png",
            "bookmarks.gif" to "openmoji-color/1F516.png",
            "ciselniky.gif" to "openmoji-color/E1C1.png",
            "ekczv.gif" to "openmoji-color/1F4D8.png",
            "harmonogram.gif" to "openmoji-color/1F5D3.png",
            "kdojekdo.gif" to "openmoji-color/1F50E.png",
            "loginy.gif" to "openmoji-color/002A-FE0F-20E3.png",
            "nastenka.gif" to "openmoji-color/1F4CC.png",
            "omne.gif" to "openmoji-color/1F464.png",
            "promoce.gif" to "openmoji-color/1F9D1-200D-1F393.png",
            "skolitel.gif" to "openmoji-color/1F9D1-200D-1F3EB.png",
            "soub_mana.gif" to "openmoji-color/1F4C2.png",
            "wstip_st.gif" to "openmoji-color/1F4B6.png",
            "prezkumy_st.gif" to "openmoji-color/1F9FE.png",
            // other - in filters, tables, ...
            "div_tip.gif" to "openmoji-color/2139.png",
            "ico_invert.gif" to "openmoji-color/E25B.png",
            "ico_n_style.png" to "openmoji-color/E265.png",
            "ico_predmety.png" to "openmoji-color/269B.png",
            "ico_rozvrhng.png" to "openmoji-color/E0AB.png",
            "ico_ucitel_small.png" to "openmoji-color/1F9D1-200D-1F3EB.png",
            "ico_detail.png" to "openmoji-color/E259.png",
            "term_st_false.gif" to "openmoji-color/274C.png",
            "ico_delete.png" to "openmoji-color/E262.png",
            "ico_date.png" to "openmoji-color/1F5D3.png",
            "chk_true.gif" to "openmoji-color/2714.png",
            "chk_false.gif" to "openmoji-color/274C.png",
            "ico_list.png" to "openmoji-color/1F4D1.png",
            "div_legend.gif" to "openmoji-color/2139.png",
            "ico_select_all.gif" to "openmoji-color/2714.png",
            "ico_unselect_all.gif" to "openmoji-color/274C.png",
            "filtr_minus.gif" to "openmoji-color/2796.png",
            "ico_n_bin.png" to "openmoji-color/E262.png",
            "ico_dialog_find.png" to "openmoji-color/1F50E.png",
            "ico_dialog_null.png" to "openmoji-color/274C.png",
            "ico_dialog_plus.png" to "openmoji-color/2795.png",
            "ico_n_link_go.png" to "openmoji-color/E269.png",
    )

    private fun replaceRequestWith(request: WebResourceRequest): WebResourceResponse? {

        val file = request.url.lastPathSegment
        if (imageAssetsReplacements.containsKey(file)) {
            val newFile = imageAssetsReplacements[file]!!
            return WebResourceResponse(URLConnection.guessContentTypeFromName(newFile), "", assets.open(newFile))
        }

        return null
    }
}