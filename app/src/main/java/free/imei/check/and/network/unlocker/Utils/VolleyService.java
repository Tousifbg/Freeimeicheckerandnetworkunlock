package free.imei.check.and.network.unlocker.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import free.imei.check.and.network.unlocker.Models.IMEI_SEARCH;
import free.imei.check.and.network.unlocker.Models.ModelClass;


/**
 * Created by Jaffar on 11/22/2016.
 */
public class VolleyService {

    Context mContext;

    public VolleyService(Context mContext) {
        this.mContext = mContext;
    }

    //method1
    public void volley_Call(String url, final ModelClass model
            , final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                        Log.d("error","TimeoutError");
                    } else if (volleyError instanceof AuthFailureError) {
                        Log.d("error","AuthFailureError");

                        //TODO
                    } else if (volleyError instanceof ServerError) {
                        Log.d("error","ServerError");
                        //TODO
                    } else if (volleyError instanceof NetworkError) {
                        //TODO
                        Log.d("NetworkError","AuthFailureError");

                    } else if (volleyError instanceof ParseError) {
                        //TODO
                        Log.d("ParseError","AuthFailureError");
                    }                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.d("parseNetworkResponse",response.statusCode+"here");

                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();


                    params.put("imei",model.getImei());
                    params.put("country",model.getCountry());
                    params.put("network",model.getNetwork());
                    params.put("date",model.getDate());


                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("updated responce",e.toString());

        }
    }

    //method2
    public void imei_checker(String url, final String message
            , final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                        Log.d("error","TimeoutError");
                    } else if (volleyError instanceof AuthFailureError) {
                        Log.d("error","AuthFailureError");

                        //TODO
                    } else if (volleyError instanceof ServerError) {
                        Log.d("error","ServerError");
                        //TODO
                    } else if (volleyError instanceof NetworkError) {
                        //TODO
                        Log.d("NetworkError","AuthFailureError");

                    } else if (volleyError instanceof ParseError) {
                        //TODO
                        Log.d("ParseError","AuthFailureError");
                    }                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.d("parseNetworkResponse",response.statusCode+"here");

                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();


                    params.put("message",message);


                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("updated responce",e.toString());

        }

    }

    //method3
    public void imei_search(String url, final IMEI_SEARCH imei_search_model
            , final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                        Log.d("error","TimeoutError");
                    } else if (volleyError instanceof AuthFailureError) {
                        Log.d("error","AuthFailureError");

                        //TODO
                    } else if (volleyError instanceof ServerError) {
                        Log.d("error","ServerError");
                        //TODO
                    } else if (volleyError instanceof NetworkError) {
                        //TODO
                        Log.d("NetworkError","AuthFailureError");

                    } else if (volleyError instanceof ParseError) {
                        //TODO
                        Log.d("ParseError","AuthFailureError");
                    }                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.d("parseNetworkResponse",response.statusCode+"here");

                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();


                    params.put("imei_search",imei_search_model.getImei());


                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("updated responce",e.toString());

        }

    }

    /*------------------------------------------------- /For Headers----------------------------------------------------------*/
    public interface VolleyResponseListener {
        void onSuccess(String response);
        void onError(VolleyError error);

    }

}
