package com.zk.my.demo01.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zk.my.demo01.R;
import com.zk.my.demo01.constant.MyConstants;
import com.zk.my.demo01.constant.URLConstants;
import com.zk.my.demo01.util.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "Tag";
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_webview)
    WebView detailWebview;
    private String url;
    private String title;

    private String htmlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        initData();
        initToolBar();
        initWebView();
        initWebViewData();
    }

    private void initWebViewData() {

        OkHttpUtils.doGET(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure---------------------->");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //解析数据response
                String result = OkHttpUtils.getStringFromResponse(response);
                Log.e(result, "htmlStr---------------------->" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject dataJsonObj = jsonObject.getJSONObject("data");
                    htmlStr = dataJsonObj.getString("wap_content");
                    Log.e(TAG, "htmlStr---------------------->" + htmlStr);
                    //解析json字符串
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            detailWebview.loadDataWithBaseURL(null, htmlStr, "text/html", "UTF-8", null);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "---------------------->" + e.getMessage());
                }


            }
        });
    }

    private void initWebView() {
        detailWebview.getSettings().setJavaScriptEnabled(true);
        detailWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private void initToolBar() {
        detailToolbar.setTitle(title);
        detailToolbar.setBackgroundColor(getResources().getColor(R.color.powderblue));

        setSupportActionBar(detailToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    private void initData() {
        Intent intent = getIntent();
        //url
        String id = intent.getStringExtra(MyConstants.KEY_DETAIL_ID);//点击传id
        url = String.format(URLConstants.DETAIL_URL, id);
        Log.e(TAG, "url---------------------->" + url);
        //title
        title = intent.getStringExtra(MyConstants.KEY_DETAIL_TITLE);
        Log.e(TAG, "title---------------------->" + title);
    }
}
