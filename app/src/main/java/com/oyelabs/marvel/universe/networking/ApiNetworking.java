package com.oyelabs.marvel.universe.networking;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.oyelabs.marvel.universe.adapter.MainAdapter;
import com.oyelabs.marvel.universe.model.CharacterModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ApiNetworking {

    Context mContext;
    List<CharacterModel> characterModelList;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    String HASH = "e86e35bf2c79ad52515c1a489b0b0196";
    String API_KEY = "6c48c7611a465b1bd13b4bf319b361b0";
    String BASE_URL = "https://gateway.marvel.com/v1/public/characters?apikey=" + API_KEY + "&hash=" + HASH + "&ts=1634692299";
    String url;

    public ApiNetworking(Context mContext, List<CharacterModel> characterModelList, MainAdapter mainAdapter, RecyclerView recyclerView, ProgressBar progressBar) {
        this.mContext = mContext;
        this.characterModelList = characterModelList;
        this.mainAdapter = mainAdapter;
        this.recyclerView = recyclerView;
        this.progressBar = progressBar;
    }

    public void getData(int pageNum, int limit) {
        if (pageNum >= limit) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(mContext, "No more data available", Toast.LENGTH_SHORT).show();
            return;
        }


        url = BASE_URL + "&limit=10&offset=" + pageNum;


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray array = data.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String strId = String.valueOf(id);
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        String imageLink = jsonObject.getJSONObject("thumbnail").getString("path");
                        String imageExtension = jsonObject.getJSONObject("thumbnail").getString("extension");
                        String moreDetailsUrl = jsonObject.getJSONArray("urls").getJSONObject(0).getString("url");

                        String mediumImage = imageLink + "/portrait_medium." + imageExtension;

                        Log.d("myapp", mediumImage);


                        CharacterModel model = new CharacterModel(strId, name, description, mediumImage, moreDetailsUrl);
                        characterModelList.add(model);
                    }

                    mainAdapter = new MainAdapter(mContext, characterModelList);
                    recyclerView.setAdapter(mainAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

    }

    public void getSearchData(String name) {

        if (name.equals("")) {
            name = "a";
        }


        characterModelList.clear();


        try {
            url = BASE_URL + "&nameStartsWith=" + name;


        } catch (Exception e) {
            e.printStackTrace();
        }


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data");
                    JSONArray array = data.getJSONArray("results");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String strId = String.valueOf(id);
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        String imageLink = jsonObject.getJSONObject("thumbnail").getString("path");
                        String imageExtension = jsonObject.getJSONObject("thumbnail").getString("extension");
                        String moreDetailsUrl = jsonObject.getJSONArray("urls").getJSONObject(0).getString("url");

                        String mediumImage = imageLink + "/portrait_medium." + imageExtension;


                        CharacterModel model = new CharacterModel(strId, name, description, mediumImage, moreDetailsUrl);
                        characterModelList.add(model);
                    }

                    mainAdapter = new MainAdapter(mContext, characterModelList);
                    recyclerView.setAdapter(mainAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);

    }

    public void updateView() {
        mainAdapter.notifyDataSetChanged();
    }
}
