package free.imei.check.and.network.unlocker.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;


import java.util.ArrayList;
import java.util.Arrays;

import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.SharePreferences.MySharePreferences;

public class CodeDetails extends AppCompatActivity {

    private static final String TAG = "CodeDetails.java";
    /*Declarations*/
    private FrameLayout adContainerView;


    ArrayList<String> codes=new ArrayList<>();
    ArrayList<String>codesDetails=new ArrayList<>();
    String position;
    int positionInt;
    TextView codename;
    TextView description;
    private AdView mAdView;
    Button showme;
    private InterstitialAd mInterstitialAd;
    private MySharePreferences updatecounter=new MySharePreferences();
    private AdView adView;
    private int pos;
    /*Declarations*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_details);
        getSupportActionBar().hide();


        /*regestration*/
        showme=findViewById(R.id.showme);
        codename=findViewById(R.id.codename);
        description=findViewById(R.id.description);
        codename.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);

        MySharePreferences mySharePreferences=new MySharePreferences();
        positionInt= mySharePreferences.getPosition(this);
        addlistDetails();
        codename.setText(codes.get(positionInt));
        description.setText(codesDetails.get(positionInt));
        /*regestration*/
         pos=getIntent().getIntExtra("position",0);

        Log.d(TAG, ": onCreate :"+codesDetails.size()+" "+codesDetails.get(pos));




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
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("25A64F2D6415F155140C98CE682CB4D5")).build());

        adContainerView = findViewById(R.id.ad_view_container);

        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        adContainerView.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
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


    public void addlistDetails(){
        codes.add("*#06#");
        codesDetails.add("If you type in *#06#, you'll immediately see your iPhone's IMEI number. This trick does not seem to work on Verizon Wireless, but it does on all other carriers that we've tested");


        codes.add("All Carriers:*67XXXXXXXXXX AND T-Mobile (Alternative):#31#XXXXXXXXXX");
        codesDetails.add("To do it, just enter *67 before dialing the number you're calling, which works with most carriers. All they will see on their end once the call goes through is \"unknown,\" \"private,\" or some other indicator of the like. They are less likely to pick up for an unknown number, as they get a bad rap for being associated with spammers, but you never know. T-Mobile also has another code available");

        codes.add("All Carriers:*82XXXXXXXXXX AND T-Mobile (Alternative):#31#XXXXXXXXXX");
        codesDetails.add("If you make all of your outgoing calls anonymous with the setting available from your carrier instead of a call-by-call basis using the tip above, you can break anonymity on a call-by-call basis using the code *82 before dialing the number you're calling.");


        codes.add("511");
        codesDetails.add("If you're not getting accurate traffic information on Apple Maps, Google Maps, Waze, or SigAlert, or some other app that has live traffic updates, give 511 a call. This is the number that gives local traffic conditions and updates, and it should work on all carriers.\n" +
                "511\n" +
                "However, carriers have to work with each state to implement this, so there may be a few states that still haven't connected with certain carriers. For instance, Sprint 511 does NOT work in Alabama, Alaska, Arkansas, Connecticut, Delaware, Michigan, Montana, North Dakota, and Oklahoma, but does in all other states plus DC\n");

        codes.add("AT&T:       *3282#\n" +
                "T-Mobile:   #932#\n" +
                "Verizon:    #3282\n");
        codesDetails.add("Normally, in order to check up on how much data you have left on your cellular plan, you'd have to use the provider's own app or widget to get an accurate reading. Some carriers, such as T-Mobile, will even include accurate data usage stats in your Settings app. But there's an easy way to check used data without installing any apps or switching providers.\n" +
                "AT&T:       *3282#\n" +
                "T-Mobile:   #932#\n" +
                "Verizon:    #3282\n" +
                "After calling one of the codes listed above, based on what carrier you have, you should either get a verbal readout from a robotic answerer or a text message that states your total usage for the month so far. Note that Sprint does not have a service for this. You used to be able to text 1311 to get data usage stats, but they discontinued that service.\n");


        codes.add("\tCode: *#5005*7672#");
        codesDetails.add("Every text message that you send from your phone first goes to a server or SM S center number, which then sends it to the number you send it to. If you have been facing a few issues regarding SMS, it’s best to check the SMS center number and you can use this secret code to do exactly that. You can just enter the code in the iPhone’s dialer and press “call”.");


        codes.add("Code: *#43# (Check Status), *43# (Enable Call Waiting), #43# (Disable Call Waiting)");
        codesDetails.add("Every text message that you send from your phone first goes to a server or SM S center number, which then sends it to the number you send it to. If you have been facing a few issues regarding SMS, it’s best to check the SMS center number and you can use this secret code to do exactly that. You can just enter the code in the iPhone’s dialer and press “call”.");


        codes.add("Code: *#33# (Check status), *33*pin# (Enable Call Barring), #33*pin# (Call Barring)");
        codesDetails.add("Call Barring lets you block all incoming or outgoing calls on your phone and if you want to check its status or enable/disable it on your iPhone, you can use this code. The “pin” here is the SIM Pin, which is the lock on your SIM card. You can enable it in iPhone Settings->Phone->SIM PIN.");


        codes.add("Code: *#21# (Check Status), *21# (Enable or Disable Call Forwarding), *21mobilenumber# (Divert Calls to this Number)");
        codesDetails.add("Don’t want to be disturbed by a phone call while you watch your favorite sports team fight it out? Well, for people unaware, Call Forwarding is a feature that lets you divert incoming calls to your other number or even a voicemail and you can use these secret codes to check its status on your phone or to enable it and divert calls to another number.");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CodeDetails.this,MainActivity.class));
        finish();
    }



    @Override
    protected void onPause() {
        super.onPause();
//        mRewardedVideoAd.pause(CodeDetails.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mRewardedVideoAd.resume(CodeDetails.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mRewardedVideoAd.destroy(CodeDetails.this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRewardedVideoAd.destroy(CodeDetails.this);

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

    public void showme(View view) {
        codename.setVisibility(View.VISIBLE);
        codename.setText(codesDetails.get(pos).toString());
        Toast.makeText(CodeDetails.this, "show", Toast.LENGTH_SHORT).show();

    }
}
