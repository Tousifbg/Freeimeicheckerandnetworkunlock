package free.imei.check.and.network.unlocker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.loading.rotate.RotateLoading;

import free.imei.check.and.network.unlocker.R;
import im.delight.android.webview.AdvancedWebView;

public class MoreServices extends AppCompatActivity implements AdvancedWebView.Listener {

    private ImageView btnBack;

    private LinearLayout layout_one;
    private CardView method_one,method_two,method_three,method_four;

    private RelativeLayout layout_two;
    private ImageView btnBacks;
    private TextView toolbar_txt;
    private AdvancedWebView webview;
    private RotateLoading rotateloading;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_services);

        initViews();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        webview.setListener(this, this);
        webview.setMixedContentAllowed(false);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }

        method_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                toolbar_txt.setText("Method One");
                btnBacks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_two.setVisibility(View.GONE);
                        layout_one.setVisibility(View.VISIBLE);
                    }
                });

                rotateloading.start();
                //webview.setListener(MoreServices.this, this);
                url = "https://www.flaticon.com/";
                webview.loadUrl(url);
            }
        });

        method_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                toolbar_txt.setText("Method Two");
                btnBacks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_two.setVisibility(View.GONE);
                        layout_one.setVisibility(View.VISIBLE);
                    }
                });

                rotateloading.start();
                url = "https://stackoverflow.com/";
                webview.loadUrl(url);

            }
        });

        method_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                toolbar_txt.setText("Method Three");
                btnBacks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_two.setVisibility(View.GONE);
                        layout_one.setVisibility(View.VISIBLE);
                    }
                });

                rotateloading.start();
                url = "https://www.google.com/";
                webview.loadUrl(url);
            }
        });

        method_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_one.setVisibility(View.GONE);
                layout_two.setVisibility(View.VISIBLE);
                toolbar_txt.setText("Method Four");
                btnBacks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout_two.setVisibility(View.GONE);
                        layout_one.setVisibility(View.VISIBLE);
                    }
                });

                rotateloading.start();
                url = "https://www.facebook.com/";
                webview.loadUrl(url);
            }
        });
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        layout_one = findViewById(R.id.layout_one);
        method_one = findViewById(R.id.method_one);
        method_two = findViewById(R.id.method_two);
        method_three = findViewById(R.id.method_three);
        method_four = findViewById(R.id.method_four);
        layout_two = findViewById(R.id.layout_two);
        btnBacks = findViewById(R.id.btnBacks);
        toolbar_txt = findViewById(R.id.toolbar_txt);
        webview = findViewById(R.id.webview);
        rotateloading = findViewById(R.id.rotateloading);
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
        rotateloading.stop();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}