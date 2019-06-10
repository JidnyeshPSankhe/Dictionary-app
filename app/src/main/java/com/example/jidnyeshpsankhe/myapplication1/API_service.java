package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;

import android.util.Log;


import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class API_service  {

    private final static String TAG = "API";
    private static final API_service ourInstance = new API_service();


    public static API_service getInstance() {
        return ourInstance;
    }

    private RequestQueue queue;
    private Api_results listener;

    void initAPI(Context ctx, Api_results listener) {
        queue = Volley.newRequestQueue(ctx);
        this.listener = listener;
    }


    void getResults(String term, final Context ctx) {

        String url = "https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="+term;
        ArrayList<SearchObject> resultList = new ArrayList<>();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    // response
                    try {

                        for(int i = 0;i< response.getJSONArray("list").length();i++){
                            String definition = response.getJSONArray("list").getJSONObject(i).get("definition").toString();
                            int upVotes = (int)response.getJSONArray("list").getJSONObject(i).get("thumbs_up");
                            int downVotes = (int)response.getJSONArray("list").getJSONObject(i).get("thumbs_down");
                            resultList.add(new SearchObject(definition,upVotes,downVotes));
                        }
                        if(resultList.isEmpty()){

                        }
                        listener.getResults(resultList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR","error => "+error.toString());
                    resultList.add(new SearchObject("No results",0,0));
                }
        )

        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("X-RapidAPI-Host", "include your key here");
                params.put("X-RapidAPI-Key", "include your key here");

                return params;
            }

            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                if (cacheEntry == null) {
                    cacheEntry = new Cache.Entry();
                }
                final long cacheHitButRefreshed = 3 * 60 * 1000;
                final long cacheExpired = 24 * 60 * 60 * 1000;
                long now = System.currentTimeMillis();
                final long softExpire = now + cacheHitButRefreshed;
                final long ttl = now + cacheExpired;
                cacheEntry.data = response.data;
                cacheEntry.softTtl = softExpire;
                cacheEntry.ttl = ttl;
                String headerValue;
                headerValue = response.headers.get("Date");
                if (headerValue != null) {
                    cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                headerValue = response.headers.get("Last-Modified");
                if (headerValue != null) {
                    cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                }
                cacheEntry.responseHeaders = response.headers;
                final String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString), cacheEntry);
            } catch (UnsupportedEncodingException | JSONException e) {
                return Response.error(new ParseError(e));
            }
            }
            @Override
            protected void deliverResponse(JSONObject response) {
            super.deliverResponse(response);
        }

            @Override
            public void deliverError(VolleyError error) {
            super.deliverError(error);
        }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
            return super.parseNetworkError(volleyError);
        }

        };

        queue.add(getRequest);

    }

    public interface Api_results{
        void getResults(ArrayList<SearchObject> result);

    }
}
