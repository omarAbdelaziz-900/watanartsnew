package com.WattanArt.ui.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.ui.Home.HomeActivity;
import com.WattanArt.ui.base.BaseActivity;

public class WebviewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = findViewById(R.id.webview);

        findViewById(R.id.toolbar_image_view).setOnClickListener(v -> onBackPressed());

        //mainUrl + "/Payment/PaymentOrder?OrderId=" + String(orderId) + "&lang=" + String(lng)

        int id = getIntent().getIntExtra("id", 0);
        int lang = new UserData().getLocalization(WebviewActivity.this);
        String url = Constants.BASE_URL + "/Payment/PaymentOrder?OrderId=" + String.valueOf(id) + "&lang=" + String.valueOf(lang);
        Log.e("Url" , url);
        showLoading();
        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                hideLoading();
            }
        });
    }

    @Override
    protected void setUpActivityOrFragmentRequirment() {

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("From", " shipping order");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
