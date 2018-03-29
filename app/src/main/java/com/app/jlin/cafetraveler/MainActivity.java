package com.app.jlin.cafetraveler;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.jlin.cafetraveler.Model.Cafe;
import com.app.jlin.cafetraveler.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });


        binding.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://cafenomad.tw/api/v1.2/cafes/taipei")
                        .build();
                okHttpClient.newCall(request).enqueue(callback);
            }
        });
    }

    private Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e("onFailure",e.toString());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseString = response.body().string();
            JsonArray jsonElements = new Gson().fromJson(responseString,JsonArray.class);
            ArrayList<Cafe> cafeArrayList = new ArrayList<>();
            for(int i = 0 ; i < jsonElements.size() ; i++){
                Cafe cafe = new Gson().fromJson(jsonElements.get(i),Cafe.class);
                cafeArrayList.add(cafe);
            }
            Log.e("cafeArrayList",String.valueOf(cafeArrayList.size()));
        }
    };
}
