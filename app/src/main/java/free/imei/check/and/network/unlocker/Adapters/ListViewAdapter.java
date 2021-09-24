package free.imei.check.and.network.unlocker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import free.imei.check.and.network.unlocker.Activities.CodeDetails;
import free.imei.check.and.network.unlocker.R;
import free.imei.check.and.network.unlocker.SharePreferences.MySharePreferences;

public class ListViewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<String> list;
    int[] imagess={R.drawable.e_checkurimenumber,R.drawable.e_makeananomyscall,R.drawable.e_callerid,
            R.drawable.e_getlocaltraffice,R.drawable.e_viewdatausage,R.drawable.e_checksmscenter,
            R.drawable.e_callfarwarding,R.drawable.e_callbaring,R.drawable.e_callfarwarding
    };
    MySharePreferences mySharePreference=new MySharePreferences();
    private static LayoutInflater inflater = null;
    public ListViewAdapter(Activity activity, ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return list.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.custom_list_view, null);
        LinearLayout linearLayout=vi.findViewById(R.id.mainLayout);
        TextView name = (TextView) vi.findViewById(R.id.checkButton);// title
        ImageView codeImage=vi.findViewById(R.id.imageView2);
        name.setText((list.get(position)));
        codeImage.setImageResource(imagess[position]);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity,CodeDetails.class).putExtra("position",position));
                mySharePreference.setPosition(activity,position);
                activity.finish();
            }
        });
        return vi;
    }
}
