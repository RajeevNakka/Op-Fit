package com.hackathon.optfit.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class WebApiWrapper<TEntity> {

    private final String EndPoint;
    private Context Context;

    WebApiWrapper(String endPoint, Context context){
        this.Context = context;
        this.EndPoint = endPoint + "/";
    }

    public List<TEntity> get(){
        return  null;
    }

    public TEntity get(int id){
        return  null;
    }

    public TEntity post(TEntity entity){
        RequestQueue requestQueue = Volley.newRequestQueue(Context);
        //String URL = "http://192.168.0.105:81/Users";
        final String mRequestBody = new Gson().toJson(entity);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.statusCode);

                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);

        return  null;
    }

    public TEntity put(TEntity entity){
        return  null;
    }

    public TEntity delete(int id){
        return  null;
    }
}


