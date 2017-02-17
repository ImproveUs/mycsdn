package improveus.com.mycsdn.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import improveus.com.mycsdn.R;

import static improveus.com.mycsdn.R.id.webView;

/**
 * Created by pszh on 2017/2/16.
 * pengsizheng@qq.com
 */

public class DetailArticleActivity extends Activity{
    WebView wb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        wb = (WebView) findViewById(webView);

        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.setEnabled(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings settings = wb.getSettings();
        settings.setJavaScriptEnabled(true);
        wb.loadUrl("http://blog.csdn.net/u013424496/article/details/55104125");
    }
}
