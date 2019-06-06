package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;

import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class API_service  {

    private final static String TAG = "API";
    private static final API_service ourInstance = new API_service();
    public static API_service getInstance() {
        return ourInstance;
    }
    private Context apiCtx;
    private RequestQueue queue;
    Api_results listener;
    public void initAPI(Context ctx, Api_results listener) {
        apiCtx = ctx;
        queue = Volley.newRequestQueue(apiCtx);
        this.listener = listener;
    }


    public void getResults(String term, final Context ctx) {

        String url = "https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="+term;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                response -> {
                    // response
                    try {
                        ArrayList<SearchObject> resultList = new ArrayList<>();
                        for(int i = 0;i< response.getJSONArray("list").length();i++){
                            String definition = response.getJSONArray("list").getJSONObject(i).get("definition").toString();
                            int upVotes = (int)response.getJSONArray("list").getJSONObject(i).get("thumbs_up");
                            int downVotes = (int)response.getJSONArray("list").getJSONObject(i).get("thumbs_down");
                            resultList.add(new SearchObject(definition,upVotes,downVotes));
                        }
                        if(resultList.isEmpty()){
                            resultList.add(new SearchObject("No results",0,0));
                        }
                        listener.getResults(resultList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // TODO Auto-generated method stub
                    Log.d("ERROR","error => "+error.toString());
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "e3d65ef4bbmshbce65ce217c1b15p11486ejsn254a219e26d7");

                return params;
            }
        };

        queue.add(getRequest);

    }

    public interface Api_results{
        void getResults(ArrayList<SearchObject> result);

    }
}
