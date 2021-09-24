package free.imei.check.and.network.unlocker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import free.imei.check.and.network.unlocker.Adapters.AutoCompleteModelAdapter;
import free.imei.check.and.network.unlocker.Models.ModelNames;
import free.imei.check.and.network.unlocker.R;

public class SearchModel extends AppCompatActivity {

    private AutoCompleteTextView actv;

    private List<ModelNames> modelList;

    private AutoCompleteModelAdapter adapter;

    private String text1, text2;

    private TextView txt1, txt2;

    private String img;

    ModelNames student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_model);

        initViews();

        fillModelList();

        adapter = new AutoCompleteModelAdapter(this, modelList);
        actv.setAdapter(adapter);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item = actv.getText().toString();

                //get item of autocomplete textview
                Object model_img = adapterView.getItemAtPosition(position);
                if (model_img instanceof ModelNames){
                    student =(ModelNames) model_img;
                }

                if (item.equals("Apple iPhone X")) {

                    Intent intent = new Intent(SearchModel.this,UnlockScreen.class);
                    intent.putExtra("name","Apple iPhone X");
                    intent.putExtra("name2","Unlocking to USA, AT&T, T-mobile, MetroPC, Cricket, Sprint, Verizon, Tracfone, Boost Mobile, Xfinity, etc...");
                    intent.putExtra("img",student.getModelImage());
                    startActivity(intent);

                } else if (item.equals("Apple iPhone 12 Pro Max")) {

                    Intent intent = new Intent(SearchModel.this,UnlockScreen.class);
                    intent.putExtra("name","Apple iPhone 12 Pro Max");
                    intent.putExtra("name2","Unlocking to USA, AT&T, T-mobile, MetroPC, Cricket, Sprint, Verizon, Tracfone, Boost Mobile, Xfinity, etc...");
                    intent.putExtra("img",student.getModelImage());
                    startActivity(intent);

                } else if (item.equals("Apple iPhone 7")) {

                    Intent intent = new Intent(SearchModel.this,UnlockScreen.class);
                    intent.putExtra("name","Apple iPhone 7");
                    intent.putExtra("name2","Unlocking to USA, AT&T, T-mobile, MetroPC, Cricket, Sprint, Verizon, Tracfone, Boost Mobile, Xfinity, etc...");
                    intent.putExtra("img",student.getModelImage());
                    startActivity(intent);

                }
            }
        });
    }

    private void fillModelList() {
        modelList = new ArrayList<>();
        modelList.add(new ModelNames("Apple iPhone X", R.drawable.iphonex));
        modelList.add(new ModelNames("Apple iPhone 12 Pro Max", R.drawable.iphone12promax));
        modelList.add(new ModelNames("Apple iPhone 7", R.drawable.iphone7));
    }

    private void initViews() {
        actv = findViewById(R.id.actv);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
    }
}