package com.example.joanna.stormy;


import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public  static final String TAG =MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // String forecastUrl="https://api.darksky.net/forecast/07bcc84588043f5213022979342c76ba/37.8267,-122.4233";
        String apiKey="07bcc84588043f5213022979342c76ba";
        double latitude=37.8267;
        double longitude=-122.4233;
        String forecastUrl="https://api.darksky.net/forecast/"+apiKey+"/"+latitude+","+longitude;

        if(isNetAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        Log.v(TAG, response.body().string());
                        if (response.isSuccessful()) {
                        } else {
                            alertError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception:", e);
                    }
                }
            });
        }else{
            Toast.makeText(this,getString(R.string.net_message),Toast.LENGTH_LONG).show();
        }
        Log.d(TAG,"mainActivity is running");
    }

    private boolean isNetAvailable() {
        ConnectivityManager manage= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netWork=manage.getActiveNetworkInfo();
        boolean isavailable=false;
        if(netWork!=null&&netWork.isConnected()){
            isavailable=true;
        }
        return  isavailable;
    }

    private void alertError() {
        AlertError alertError=new AlertError();
        alertError.show(getFragmentManager(),"Error message");
    }
}
