package io.github.sykoram.sis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    SwipeRefreshLayout refreshLayout;
    boolean pageFinished = true;

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

            try {
                String styleScript = fileToString(getResources().openRawResource(R.raw.script0));
                view.evaluateJavascript(styleScript, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            try {
                String styleScript = fileToString(getResources().openRawResource(R.raw.script0));
                view.evaluateJavascript(styleScript, null);
                String script = fileToString(getResources().openRawResource(R.raw.script1));
                view.evaluateJavascript(script, null);
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

    public boolean isUrlAllowed(Uri url) {
        List<String> allowedSchemes = Arrays.asList("http", "https");
        if (!allowedSchemes.contains(url.getScheme())) {
            return false;
        }

        List<String> allowedBeginnings = Arrays.asList(
                "is.cuni.cz/studium", "idp.cuni.cz", "ldapuser.cuni.cz");
        List<String> disallowedBeginnings = Arrays.asList(
                "is.cuni.cz/studium/v4");

        String hostAndPath = url.getHost().concat(url.getPath());
        boolean allowed = false;
        for (String bgn : allowedBeginnings) {
            if (hostAndPath.startsWith(bgn)) {
                allowed = true;
                break;
            }
        }
        if (allowed) {
            for (String bgn : disallowedBeginnings) {
                if (hostAndPath.startsWith(bgn)) {
                    allowed = false;
                    break;
                }
            }
        }

        return allowed;
    }

    public static String fileToString(InputStream is) throws IOException {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String str = s.hasNext() ? s.next() : "";
        is.close();
        return str;
    }
}