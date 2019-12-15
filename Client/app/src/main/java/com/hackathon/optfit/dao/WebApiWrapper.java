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
import com.google.gson.reflect.TypeToken;
import com.hackathon.optfit.Util.ResponseListener;
import com.hackathon.optfit.entities.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class WebApiWrapper<TEntity> {

    private final String EndPoint;
    private Context Context;
    final Class<TEntity> TypeParameterClass;

    WebApiWrapper(String endPoint, Context context, Class<TEntity> typeParameterClass) {
        this.Context = context;
        this.EndPoint = endPoint + "/";
        this.TypeParameterClass = typeParameterClass;
    }

    public void getUnSafeUsers(final ResponseListener<List<User>> responseListener) {
        getUnSafeUsers(responseListener, "");
    }

    public void getUnSafeUsers(final ResponseListener<List<User>> responseListener, String endPointSuffix) {

        RequestQueue requestQueue = Volley.newRequestQueue(Context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoint + endPointSuffix, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
                try {
                    List<User> entities = (List<User>) new Gson().fromJson(response, new TypeToken<List<User>>() {
                    }.getType());
                    FireEvent(entities, responseListener);
                } catch (Exception e) {
                    FireEvent(null, responseListener);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
                FireEvent(null, responseListener);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return null;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    try {
                        responseString = new String(response.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    public TEntity get(int id) {
        return null;
    }

    public void get(String id, final ResponseListener<TEntity> responseListener) {
        RequestQueue queue = Volley.newRequestQueue(Context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoint + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TEntity entity = new Gson().fromJson(response, TypeParameterClass);
                        FireEvent(entity, responseListener);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FireEvent(null, responseListener);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void post(TEntity entity) {
        post(entity, null);
    }

    public void post(TEntity entity, final ResponseListener<TEntity> responseListener) {
        RequestQueue requestQueue = Volley.newRequestQueue(Context);
        //String URL = "http://192.168.0.105:81/Users";
        final String mRequestBody = new Gson().toJson(entity);
        Log.i("LOG_VOLLEY", mRequestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("LOG_VOLLEY", response);
                try {
                    TEntity entity = new Gson().fromJson(response, TypeParameterClass);
                    FireEvent(entity, responseListener);
                } catch (Exception e) {
                    FireEvent(null, responseListener);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_VOLLEY", error.toString());
                FireEvent(null, responseListener);
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
                    VolleyLog.wtf("Unsupported Encoding while trying to getUnSafeUsers the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    try {
                        responseString = new String(response.data, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

    private void FireEvent(TEntity entity, ResponseListener<TEntity> responseListener) {
        if (responseListener != null) {
            responseListener.onComplete(entity);
        }
    }

    private void FireEvent(List<User> entity, ResponseListener<List<User>> responseListener) {
        if (responseListener != null) {
            responseListener.onComplete(entity);
        }
    }

    public TEntity put(TEntity entity) {
        return null;
    }

    public TEntity delete(int id) {
        return null;
    }
}


