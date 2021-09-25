package io.github.sykoram.sis;

import static io.github.sykoram.sis.MainActivityUtils.readFileToString;
import static io.github.sykoram.sis.MainActivityUtils.getPageIdentifier;
import static io.github.sykoram.sis.MainActivityUtils.isUrlAllowed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    SwipeRefreshLayout refreshLayout;
    boolean pageFinished = true;

    /* The first path part after "is.cuni.cz/studium/" (ev. with ".php" removed):
         index, rozvrhng, predmety, term_st2, predm_st2, szz_st, zkous_st, anketa, dipl_st, grupicek, omne, role (...)
       This will be added as a class to <body>. */
    String pageIdentifier = "";

    WebViewClient webClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (isUrlAllowed(request.getUrl())) {
                return false;
            }
            Intent i = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(i);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap bitmap) {
            super.onPageStarted(view, url, bitmap);

            pageFinished = false;
            if (!refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(true);
            }

            pageIdentifier = getPageIdentifier(url);
            try {
                view.evaluateJavascript("document.body.classList.add('" + pageIdentifier + "');", null);
                String styleScript = readFileToString(getResources().openRawResource(R.raw.script0));
                view.evaluateJavascript(styleScript, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            pageIdentifier = getPageIdentifier(url);
            view.evaluateJavascript("document.body.classList.add('" + pageIdentifier + "');", null);
            try (InputStream styleScriptIs = getResources().openRawResource(R.raw.script0);
                 InputStream scriptIs = getResources().openRawResource(R.raw.script1)) {
                view.evaluateJavascript(readFileToString(styleScriptIs), null);
                view.evaluateJavascript(readFileToString(scriptIs), null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            pageFinished = true;
            onPageLoaded(view);
        }
    };

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress < 100 && !refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(true);
            }

            if (newProgress == 100 && pageFinished) {
                onPageLoaded(view);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(webClient);
        webView.setWebChromeClient(webChromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setVisibility(View.GONE);
        webView.loadUrl("https://is.cuni.cz/studium");

        refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setColorSchemeResources(R.color.primary_blue);
        refreshLayout.setEnabled(false);
    }

    public void onPageLoaded(WebView view) {
        refreshLayout.setRefreshing(false);
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
        CookieManager.getInstance().flush();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}