package free.imei.check.and.network.unlocker.Activities;

import static com.google.firebase.messaging.Constants.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.Utils.APIS;
import free.imei.check.and.network.unlocker.Utils.NetworkUtils;
import free.imei.check.and.network.unlocker.Utils.ShowNow;
import free.imei.check.and.network.unlocker.Utils.VolleyService;

public class IMEI_CHECKER extends AppCompatActivity {

    private EditText imei_edt;
    private Button btnGo;

    private VolleyService volleyService;

    private ShowNow showNow;

    private String my_imei;

    private LinearLayout layout1,layout2;

    private ImageView device_img;
    private TextView brand_name,model_name,black_list_status;
    private Button btnUnlockNow;
    private Button btnMoreServices;

    private AdView adView;
    private InterstitialAd interstitialAd;

    private RelativeLayout ad_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imei_checker);

        initViews();

        showNow = new ShowNow(this);
        volleyService = new VolleyService(this);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isNetworkConnected(IMEI_CHECKER.this))
                {
                    showInterstitial(1);
                    my_imei = imei_edt.getText().toString().trim();
                    if (TextUtils.isEmpty(my_imei)){
                        imei_edt.setError("Enter IMEI");
                    }else {
                        vollyRequest();
                    }
                }
                else {
                    Toast.makeText(IMEI_CHECKER.this, "Check your internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().
                        setTestDeviceIds(Arrays.asList("4EFF8C8415B6B5BDA8F6122037A9BCE2")).build());

        ad_layout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
            }
        });

        /*load intrestials */
        loadAd(0);
    }

    private void showInterstitial(int position) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            //startActivityRun(postions);
            Log.i(TAG,"interstitial wasnt ready");
        }
    }

    private void loadAd(int i) {
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
                        IMEI_CHECKER.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        IMEI_CHECKER.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                        //startActivityRun(globalPosition);

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        IMEI_CHECKER.this.interstitialAd = null;
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
                        Log.i(TAG, loadAdError.getMessage());
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
        ad_layout.removeAllViews();
        ad_layout.addView(adView);

        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
            loadAd(0);
        }
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private AdSize getAdSize() {
        // Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;

        float adWidthPixels = ad_layout.getWidth();

        // If the ad hasn't been laid out, default to the full screen width.
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void vollyRequest() {
        showNow.showLoadingDialog(this);
        String url = APIS.DEVICE_URL;

        volleyService.imei_checker(url+"?imei="+my_imei, "88",
                new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        showNow.scheduleDismiss();
                        Log.d("responce", response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String BRAND       = jsonObj.getString("device_make");
                            String MODEL       = jsonObj.getString("device_name");
                            String IMG        = jsonObj.getString("device_image");

                            String img = "https:"+IMG;

                            Log.e("device_make",BRAND + "device_name" + MODEL
                                    + "device_img" + img);

                            layout1.setVisibility(View.GONE);
                            layout2.setVisibility(View.VISIBLE);
                            brand_name.setText(BRAND);
                            model_name.setText(MODEL);
                            black_list_status.setText("False");

                            Picasso.get()
                                    .load(img)
                                    .placeholder(R.drawable.ic_baseline_image_24)
                                    .into(device_img, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.e("Success","img loaded successfully");
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Toast.makeText(IMEI_CHECKER.this, ""+e.getMessage()
                                                    ,Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            btnUnlockNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layout2.setVisibility(View.GONE);
                                    layout1.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(IMEI_CHECKER.this,
                                            SearchModel.class));
                                }
                            });

                            btnMoreServices.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(IMEI_CHECKER.this,MoreServices.class));
                                }
                            });

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        showNow.scheduleDismiss();
                    }
                });
    }

    private void initViews() {
        imei_edt = findViewById(R.id.imei_edt);
        btnGo = findViewById(R.id.btnGo);
        device_img = findViewById(R.id.device_img);
        brand_name = findViewById(R.id.brand_name);
        model_name = findViewById(R.id.model_name);
        black_list_status = findViewById(R.id.black_list_status);
        btnUnlockNow = findViewById(R.id.btnUnlockNow);
        btnMoreServices = findViewById(R.id.btnMoreServices);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        ad_layout = findViewById(R.id.addVieww);
    }
}