package com.movie.exoplayerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main4Activity extends AppCompatActivity {

    private static final String APPLICATION_ID = "rt446RntQtb1hIw0boagVA";
    private static final String API_KEY = "ac5yuahlgn70pr70lhsg93vyw";

    //View Pager
    private ViewPager view_pager;
    private LiveAdapter liveAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> urlList;

    final OkHttpClient mOkHttpClient = new OkHttpClient();

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        view_pager = findViewById(R.id.view_pager);
        view_pager.setPageTransformer(false, new CubeOutRotationTransformation());
        //view_pager.setOffscreenPageLimit(5);
        arrayList = new ArrayList<>();
        urlList = new ArrayList<>();

        arrayList.add("ffa06aec-b960-46bd-acba-426e38966883");
        arrayList.add("6b1c13a1-ce06-4893-8614-13dea618bf98");
        arrayList.add("978cbc60-2501-4a5f-b0ae-608ddafc7d76");
        arrayList.add("29fd6023-7caf-4ff9-83a6-cf43783af0d2");

        getLatestResourceUri();

    }

    private void getLatestResourceUri() {

        for (int i = 0; i <= arrayList.size() - 1; i++){

            url = "https://api.bambuser.com/broadcasts/" + arrayList.get(i) + "/";

            Request request = new Request.Builder()
                    //.url("https://api.bambuser.com/broadcasts/ffa06aec-b960-46bd-acba-426e38966883/")
                    .url(url)
                    .addHeader("Accept", "application/vnd.bambuser.v1+json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .get()
                    .build();

            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    runOnUiThread(new Runnable() { @Override public void run() {
                    /*if (mPlayerStatusTextView != null)
                        mPlayerStatusTextView.setText("Http exception: " + e);*/
                    }});
                }
                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    String body = response.body().string();
                    String resourceUri = null;
                    try {
                        JSONObject json = new JSONObject(body);
                        resourceUri = (String) json.get("resourceUri");
                        urlList.add(resourceUri);
                    /*JSONArray results = json.getJSONArray("results");
                    JSONObject latestBroadcast = results.optJSONObject(0);
                    resourceUri = latestBroadcast.optString("resourceUri");*/
                    } catch (Exception ignored) {}
                    final String uri = resourceUri;
                    runOnUiThread(new Runnable() { @Override public void run() {
                        //initPlayer(uri);
                        //urlList.add(uri);
                    }});
                }
            });
        }
        liveAdapter = new LiveAdapter(getSupportFragmentManager(), 0, this, urlList);
        view_pager.setAdapter(liveAdapter);
    }
}
