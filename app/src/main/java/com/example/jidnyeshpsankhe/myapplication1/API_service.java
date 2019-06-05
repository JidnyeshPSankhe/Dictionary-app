package com.example.jidnyeshpsankhe.myapplication1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class API_service  extends AsyncTask<String, Integer, String> {

    private final static String TAG = "API";
    private static final API_service ourInstance = new API_service();
    String searchResult;
    public static API_service getInstance() {
        return ourInstance;
    }
    private Context apiCtx;
    public void initAPI(Context ctx) {
        apiCtx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String term = params[0];
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://mashape-community-urban-dictionary.p.rapidapi.com/define?term="+term )
                    .header("X-RapidAPI-Host", "mashape-community-urban-dictionary.p.rapidapi.com")
                    .header("X-RapidAPI-Key", "e3d65ef4bbmshbce65ce217c1b15p11486ejsn254a219e26d7")
                    .asJson();
            searchResult = response.getBody().toString();

        } catch (UnirestException e) {
            e.printStackTrace();
            Log.e(TAG,"The error is"+e);
        }
        return searchResult;
    }

    protected void onPostExecute(String result) {
        JSONObject root = null;
        try {
            root = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(root.has("list")){
            // Get the startups array
            try {
                JSONArray ans = root.getJSONArray("list");
                List<Object> list = new ArrayList<>();
                for(int i =0;i<ans.length();i++){
                   JSONObject ans1 = new JSONObject(ans.get(i).toString());
                    String definition = ans1.getString("definition");
                    list.add(definition)
                    Log.e(TAG,"Hmmm"+definition);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG,"Error"+e);
            }

        }


        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();
    }

}
