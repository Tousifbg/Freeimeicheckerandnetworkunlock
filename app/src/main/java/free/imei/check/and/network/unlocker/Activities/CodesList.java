package free.imei.check.and.network.unlocker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import free.imei.check.and.network.unlocker.Adapters.ListViewAdapter;
import free.imei.check.and.network.unlocker.R;

public class CodesList extends AppCompatActivity {

    /*Declarations*/

    private ArrayList<String> listItem=new ArrayList<>();
    private ListView lv;
    private ListViewAdapter listViewAdapter;
    private AdView mAdView;
    /*Declarations*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codes_list);
        getSupportActionBar().hide();

        /*register every thing*/
        lv = (ListView) findViewById(R.id.lv);
        listItem = new ArrayList<String>();
        popuLateList();
        listViewAdapter=new ListViewAdapter(this,listItem);
        setAdapter();
        /*register every thing*/

        /*app id Declaration*/
//        MobileAds.initialize(this, "ca-app-pub-1473667165783666~3104013280");
        /*app id Declaration*/

        /*Banner*/
        /*Banner*/
      /*  mAdView = (AdView) findViewById(R.id.adView);
        AdRequest bannerAdRequest = new AdRequest.Builder().addTestDevice("687922C79A66425B6FCFCA83C38726F2")
                .build();
        mAdView.loadAd(bannerAdRequest);*/
        /*Banner*/
        /*Banner*/
    }

    private void setAdapter() {
        listViewAdapter=new ListViewAdapter(this,listItem);
        lv.setAdapter(listViewAdapter);
    }
    private void popuLateList() {

        listItem.add("Check Your IMEI Number");
        listItem.add("Make Anonymous Calls");
        listItem.add("Show Your Number on Caller ID");
        listItem.add("Get Local Traffic Information");
        listItem.add("View Your Data Usage");
        listItem.add("Check SMS center");
        listItem.add("Call Waiting");
        listItem.add("Call Barring");
        listItem.add("Call Forwarding");





    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
