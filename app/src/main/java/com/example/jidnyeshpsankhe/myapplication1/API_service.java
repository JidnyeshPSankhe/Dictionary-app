package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_service  {

    private final static String TAG = "API";
    private static final API_service ourInstance = new API_service();
    String searchResult;
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

                        List<SearchObject> resultList = new ArrayList<>();
                        for(int i = 0;i< response.getJSONArray("list").length();i++){
                            String definition = response.getJSONArray("list").getJSONObject(0).get("definition").toString();
                            int upVotes = (int)response.getJSONArray("list").getJSONObject(0).get("thumbs_up");
                            int downVotes = (int)response.getJSONArray("list").getJSONObject(0).get("thumbs_down");
                            resultList.add(new SearchObject(definition,upVotes,downVotes));
                        }
                        //Log.e(TAG,resultList.toString());
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com");
                params.put("X-RapidAPI-Key", "e3d65ef4bbmshbce65ce217c1b15p11486ejsn254a219e26d7");

                return params;
            }
        };

        queue.add(getRequest);

    }

    public interface Api_results{
        void getResults(List<SearchObject> result);

    }


//    @Override
//    protected String doInBackground(String... params) {
//        String term = params[0];
//        try {
//            HttpResponse<JsonNode> response = Unirest.get("https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="+term )
//                    .header("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com")
//                    .header("X-RapidAPI-Key", "e3d65ef4bbmshbce65ce217c1b15p11486ejsn254a219e26d7")
//                    .asJson();
//            searchResult = response.getBody().toString();
//
//        } catch (UnirestException e) {
//            e.printStackTrace();
//            Log.e(TAG,"The error is"+e);
//        }
//        return searchResult;
//    }
//
//    protected void onPostExecute(String result) {
//        JSONObject root = null;
//        try {
//            root = new JSONObject(result);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        if(root.has("list")){
//            // Get the startups array
//            try {
//                JSONArray ans = root.getJSONArray("list");
//                List<Object> list = new ArrayList<>();
//                for(int i =0;i<ans.length();i++){
//                   JSONObject ans1 = new JSONObject(ans.get(i).toString());
//                    String definition = ans1.getString("definition");
//                    list.add(definition);
//                    Log.e(TAG,"Hmmm"+definition);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                Log.e(TAG,"Error"+e);
//            }
//
//        }
//
//
//        // do something with the result, for example display the received Data in a ListView
//        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
//    }



}
