package io.github.sykoram.sis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    ProgressBar progressBar;
    boolean pageFinished = true;
    boolean progress100 = true;

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
            if (progressBar.getVisibility() == ProgressBar.GONE){
                progressBar.setVisibility(ProgressBar.VISIBLE);
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
            progressBar.setVisibility(ProgressBar.GONE);
        }
    };

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }

            if (newProgress == 100 && pageFinished) {
                progressBar.setVisibility(ProgressBar.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        webView.setWebViewClient(webClient);
        webView.setWebChromeClient(webChromeClient);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.loadUrl("https://is.cuni.cz/studium");
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
        String hostAndPath = url.getHost().concat(url.getPath());

        boolean allowed = false;
        for (String bgn : allowedBeginnings) {
            if (hostAndPath.startsWith(bgn)) {
                allowed = true;
                break;
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