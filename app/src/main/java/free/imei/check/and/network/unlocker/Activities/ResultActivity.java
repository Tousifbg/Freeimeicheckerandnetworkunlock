package free.imei.check.and.network.unlocker.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


import java.util.Arrays;

import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.SharePreferences.MySharePreferences;

public class ResultActivity extends AppCompatActivity  {
    private Runnable mStatusChecker;

    /*Declarations*/
    private WebView wv1;
    String myurl;
//    RotateLoading rotateloading;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    Handler handle =new Handler();

    int TIME_OUT=190000;
    Runnable runnable;
//    private RewardedVideoAd mRewardedVideoAd;
    private MySharePreferences updatecounter=new MySharePreferences();
    Boolean isAActivityRunning;
    private Handler mHandler;
    /*Declarations*/
    private int mInterval = 20000; // 5 seconds by default, can be changed later
    private int counter;
    private Dialog openDialog;
    private InterstitialAd interstitialAd;
    private AdView adView;
    private FrameLayout adContainerView;
    private int handler_time = 350000;//5 seconds in milliseconds
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();
        isAActivityRunning=true;
        myurl = getIntent().getStringExtra("url");
        mHandler = new Handler();

        openDialog = new Dialog(this);
        openDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        openDialog.setContentView(R.layout.sessions);
        openDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        openDialog.setCancelable(false);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("4EFF8C8415B6B5BDA8F6122037A9BCE2")).build());

        adContainerView = findViewById(R.id.ad_view_container);
        loadBanner();



        /*app id */

/*
        MobileAds.initialize(this, "ca-app-pub-1473667165783666~3104013280");
*/
        /*app id */

        /*webview*/
        wv1 = (WebView) findViewById(R.id.webview);
//        rotateloading =  findViewById(R.id.rotateloading);
//        rotateloading.setLoadingColor(getResources().getColor(R.color.colorAccent));
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        wv1.loadUrl(myurl);
        /*webview*/

        loadAd();
        startRunningForAd();
        startRepeatingTask();

    }


/*webview*/

    private class MyBrowser extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            rotateloading.start();







        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

//            rotateloading.stop();


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
/*webview*/

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }


    /*apear dialog*/
    public void apearDialog(String messagehere) {
loadAd();
        Button okay=openDialog.findViewById(R.id.button8);
        TextView message=openDialog.findViewById(R.id.btn1);
        message.setText(messagehere);
        okay.setText("Continue");
        openDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    openDialog.dismiss();
                }
                return true;
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySharePreferences counterMySharePreferences=new MySharePreferences();
                counter=counterMySharePreferences.getcounter(ResultActivity.this);
              showInterstitial();
                openDialog.dismiss();


            }
        });
        if (isAActivityRunning) {
            openDialog.show();
        }

    }




    @Override
    protected void onPause() {
        super.onPause();
//        mRewardedVideoAd.pause(ResultActivity.this);
        isAActivityRunning=false;
        stopRepeatingTask();

    }

    @Override
    protected void onResume() {
        super.onResume();
/*
        mRewardedVideoAd.resume(ResultActivity.this);
*/

        isAActivityRunning=true;
        startRunningForAd();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        mRewardedVideoAd.destroy(ResultActivity.this);
        isAActivityRunning=false;

        stopRepeatingTask();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRewardedVideoAd.destroy(ResultActivity.this);
        isAActivityRunning=false;
        stopRepeatingTask();

    }



    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                getResources().getString(R.string.intrestials),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        ResultActivity.this.interstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ResultActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                        startRepeatingTask();
                                        if (openDialog.isShowing()){
                                            openDialog.dismiss();

                                        }

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ResultActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());

                    }
                });
    }


    private void loadBanner() {
        // Create an ad request.
        adView = new AdView(this);
        adView.setAdUnitId(getResources().getString(R.string.bannerads));
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = adContainerView.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }


    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
//            startActivityRun(postions);
        }
    }

    public void startRunningForAd(){

        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("TAG", ": run");
                    if (!openDialog.isShowing()) {
                        apearDialog("Welcom to the Unlocker. .");
                    }
                    //this function can change value of mInterval.
                } finally {


                    // 100% guarantee that this always happens, even if
                    // your update method throws an exception

                    mHandler.postDelayed(mStatusChecker, mInterval);
                }

            }
        };


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
