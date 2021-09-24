package free.imei.check.and.network.unlocker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.VolleyError;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.Utils.APIS;
import free.imei.check.and.network.unlocker.Utils.NetworkUtils;
import free.imei.check.and.network.unlocker.Utils.ShowNow;
import free.imei.check.and.network.unlocker.Utils.VolleyService;

public class CompleteStatus extends AppCompatActivity {

    private String imei;
    private String date;

    private EditText imei_edt;
    private Button btnGo;

    private VolleyService volleyService;

    private ShowNow showNow;

    private LinearLayout layout1,layout2;

    private LottieAnimationView animationView;

    private ImageView device_img;
    private TextView brand_name,model_name,black_list_status, received_date;
    private Button btnUnlockNow;

    private ScrollView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_status);

        initViews();

        showNow = new ShowNow(this);
        volleyService = new VolleyService(this);

        showNow.showLoadingDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            imei = extras.getString("my_imei");
            date = extras.getString("date");
            if (NetworkUtils.isNetworkConnected(this))
            {
                //call api
                vollyRequest();
                card.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);
            }
            else {
                showNow.scheduleDismiss();
                card.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
            }
        }
        else {
            Log.e("EXTRAS","Null");
        }
    }

    private void vollyRequest() {
        String url = APIS.DEVICE_URL;

        volleyService.imei_checker(url+"?imei="+imei, "88",
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

                            if (BRAND != null && MODEL != null){
                                brand_name.setText(BRAND);
                                model_name.setText(MODEL);
                                black_list_status.setText("False");
                                received_date.setText(date);
                            }


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
                                            Toast.makeText(CompleteStatus.this, ""+e.getMessage()
                                                    ,Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            btnUnlockNow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(CompleteStatus.this,
                                            MoreServices.class));
                                    finish();
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
        device_img = findViewById(R.id.device_img);
        brand_name = findViewById(R.id.brand_name);
        model_name = findViewById(R.id.model_name);
        black_list_status = findViewById(R.id.black_list_status);
        btnUnlockNow = findViewById(R.id.btnUnlockNow);
        received_date = findViewById(R.id.received_date);
        animationView = findViewById(R.id.animationView);
        layout2 = findViewById(R.id.layout2);
        card = findViewById(R.id.card);
    }
}