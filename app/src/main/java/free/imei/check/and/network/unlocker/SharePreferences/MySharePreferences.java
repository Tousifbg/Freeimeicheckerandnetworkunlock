package free.imei.check.and.network.unlocker.SharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Naveed on 22/09/2018.
 */

public class MySharePreferences {

    public static final String MyPREFERENCES = "imeiUnlocker";
    public static SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;



    public void setPosition(Context mContext, int coins){
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putInt("activity", coins);
        editor.commit();


    }

    public int getPosition(Context ctx){

        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int coinshere=prfs.getInt("activity",0);
        return coinshere;
    }



    public void setcounter(Context mContext, int coins){
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putInt("counter", coins);
        editor.commit();


    }

    public int getcounter(Context ctx){

        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int coinshere=prfs.getInt("counter",0);
        return coinshere;
    }



    public void setactivitypostion(Context mContext, int coins){
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putInt("activitypos", coins);
        editor.commit();


    }

    public int getactivityposition(Context ctx){

        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int coinshere=prfs.getInt("activitypos",0);
        return coinshere;
    }
}
