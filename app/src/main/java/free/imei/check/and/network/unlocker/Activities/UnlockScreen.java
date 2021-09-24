package free.imei.check.and.network.unlocker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import free.imei.check.and.network.unlocker.Models.ModelClass;
import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.Utils.APIS;
import free.imei.check.and.network.unlocker.Utils.NetworkUtils;
import free.imei.check.and.network.unlocker.Utils.ShowNow;
import free.imei.check.and.network.unlocker.Utils.VolleyService;

public class UnlockScreen extends AppCompatActivity {

    private ImageView model_img;
    private TextView txt1,txt2;
    private LinearLayout country_btn,network_btn,currency_btn;

    private TextView country,network,currency;

    private ArrayList<String> countryArrayList;
    private ArrayList<String> networkArrayList;
    private ArrayList<String> currencyArrayList;

    private Dialog dialog1;
    private Dialog dialog2;

    private AppCompatButton btnSubmit;
    private EditText imei;

    ShowNow showNow;

    private String my_imei;

    private VolleyService volleyService;

    private TextView modelNAME, deviceTxt;
    private ImageView modelIMAGE;
    private LinearLayout device_info_layout;

    private String selected_country, selected_network, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_screen);

        initViews();

        showNow=new ShowNow(this);
        volleyService = new VolleyService(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String name = extras.getString("name");
            String name2 = extras.getString("name2");
            int image_link = getIntent().getIntExtra("img",R.drawable.realmegt);
            txt1.setText(name);
            txt2.setText(name2);
            model_img.setImageResource(image_link);

        }
        else {
            Log.e("EXTRAS","Null");
        }

        //get today date
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = simpleDateFormat.format(currentDate);

        countryArrayList = new ArrayList<>();
        countryArrayList.add("United States");
        countryArrayList.add("Pakistan");
        countryArrayList.add("Bangladesh");
        countryArrayList.add("India");
        countryArrayList.add("Dubai");

        networkArrayList = new ArrayList<>();
        networkArrayList.add("Verizon USA");
        networkArrayList.add("AT&T USA");
        networkArrayList.add("T-Mobile USA");
        networkArrayList.add("Cellcom USA");
        networkArrayList.add("MetroPCS USA");
        networkArrayList.add("Sprint USA");

        currencyArrayList = new ArrayList<>();
        currencyArrayList.add("US Dollar - USD");
        currencyArrayList.add("Pakistani Rupee - PKR");
        currencyArrayList.add("Bangladeshi Taka");
        currencyArrayList.add("Indian Rupee - INR");
        currencyArrayList.add("United Arab Emirates Dirham - AED");

        country_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1 = new Dialog(UnlockScreen.this);
                dialog1.setContentView(R.layout.country_spinner);
                dialog1.getWindow().setLayout(650,800);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                EditText editText = dialog1.findViewById(R.id.edt_txt);
                ListView listView = dialog1.findViewById(R.id.country_list);

                ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(UnlockScreen.this,
                        android.R.layout.simple_list_item_1, countryArrayList);
                listView.setAdapter(countryAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        countryAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        country.setText(countryAdapter.getItem(position));
                        selected_country = country.getText().toString();
                        dialog1.dismiss();
                    }
                });
            }
        });

        network_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1 = new Dialog(UnlockScreen.this);
                dialog1.setContentView(R.layout.country_spinner);
                dialog1.getWindow().setLayout(650,800);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                EditText editText = dialog1.findViewById(R.id.edt_txt);
                ListView listView = dialog1.findViewById(R.id.country_list);

                ArrayAdapter<String> networkAdapter = new ArrayAdapter<>(UnlockScreen.this,
                        android.R.layout.simple_list_item_1, networkArrayList);
                listView.setAdapter(networkAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        networkAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        network.setText(networkAdapter.getItem(position));
                        selected_network = network.getText().toString();
                        dialog1.dismiss();
                    }
                });
            }
        });

    /*    currency_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1 = new Dialog(UnlockScreen.this);
                dialog1.setContentView(R.layout.country_spinner);
                dialog1.getWindow().setLayout(650,800);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.show();

                EditText editText = dialog1.findViewById(R.id.edt_txt);
                ListView listView = dialog1.findViewById(R.id.country_list);

                ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(UnlockScreen.this,
                        android.R.layout.simple_list_item_1, currencyArrayList);
                listView.setAdapter(currencyAdapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        currencyAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        currency.setText(currencyAdapter.getItem(position));
                        dialog1.dismiss();
                    }
                });
            }
        });*/

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isNetworkConnected(UnlockScreen.this)){
                    my_imei = imei.getText().toString().trim();
                    if (TextUtils.isEmpty(my_imei)){
                        imei.setError("Enter your IMEI first");
                    }else {
                        vollyRequest();
                    }
                }

                /*dialog2 = new Dialog(UnlockScreen.this);
                dialog2.setContentView(R.layout.result_dialog);
                dialog2.getWindow().setLayout(650,800);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();

                Button btnOk = (Button) dialog2.findViewById(R.id.btnOk);
                Button btnMoreServices = (Button) dialog2.findViewById(R.id.btnMoreServices);

                btnMoreServices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(UnlockScreen.this,MoreServices.class));
                        dialog2.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });*/
            }
        });
    }

    private void vollyRequest() {
        showNow.showLoadingDialog(this);
        String url = APIS.IMEI_SUBMIT_URL;

        ModelClass modelClass=new ModelClass();
        modelClass.setImei(my_imei);
        modelClass.setCountry(selected_country);
        modelClass.setNetwork(selected_network);
        modelClass.setDate(date);

        volleyService.volley_Call(url, modelClass,
                new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        showNow.scheduleDismiss();
                        Log.d("responce", response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String succ = String.valueOf(jsonObj.optBoolean("success"));

                            if (succ.equals("true")){
                                //open dialog
                                dialog2 = new Dialog(UnlockScreen.this);
                                dialog2.setContentView(R.layout.result_dialog);
                                dialog2.getWindow().setLayout(650, 800);
                                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog2.show();

                                Button btnOk = (Button) dialog2.findViewById(R.id.btnOk);
                                Button btnMoreServices = (Button) dialog2.findViewById(R.id.btnMoreServices);

                                btnMoreServices.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(UnlockScreen.this, MoreServices.class));
                                        dialog2.dismiss();
                                    }
                                });

                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog2.dismiss();
                                    }
                                });
                            }
                            else if (succ.equals("false")){
                                Toast.makeText(UnlockScreen.this, "Record already exist", Toast.LENGTH_SHORT).show();
                            }else {

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        showNow.scheduleDismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UnlockScreen.this, ""+error.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }

    private void initViews() {
        model_img = findViewById(R.id.model_img);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        country_btn = findViewById(R.id.country_btn);
        network_btn = findViewById(R.id.network_btn);
        country = findViewById(R.id.country);
        network = findViewById(R.id.network);
        imei = findViewById(R.id.imei);
        btnSubmit = findViewById(R.id.btnSubmit);
        modelNAME = findViewById(R.id.modelName);
        modelIMAGE = findViewById(R.id.modelImg);
        deviceTxt = findViewById(R.id.deviceTxt);
        device_info_layout = findViewById(R.id.device_info_layout);
    }
}