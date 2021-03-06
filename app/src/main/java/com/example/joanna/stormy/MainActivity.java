package com.example.joanna.stormy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joanna.stormy.ui.AlertError;
import com.example.joanna.stormy.ui.DailyForecastActivity;
import com.example.joanna.stormy.weather.Day;
import com.example.joanna.stormy.weather.Forecst;
import com.example.joanna.stormy.weather.Hour;
import com.example.joanna.stormy.weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public  static final String TAG =MainActivity.class.getSimpleName();
    private Forecst mForecst;
    @BindView(R.id.teperature) TextView mTemperatureLabel;
    @BindView(R.id.time) TextView mTime;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summary) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshButton) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProcessBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mProcessBar.setVisibility(View.INVISIBLE);
        final double latitude=37.8267;
        final double longitude=-122.4233;
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude,longitude);
            }
        });
       // String forecastUrl="https://api.darksky.net/forecast/07bcc84588043f5213022979342c76ba/37.8267,-122.4233";
        getForecast(latitude,longitude);
        Log.d(TAG,"mainActivity is running");
    }

    private void getForecast(double latitude,double longitude) {
        String apiKey="07bcc84588043f5213022979342c76ba";
        String forecastUrl="https://api.darksky.net/forecast/"+apiKey+"/"+latitude+","+longitude;

        if(isNetAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String data=response.body().string();
                        Log.v(TAG, data);
                        if (response.isSuccessful()) {
                            mForecst=parseForecastDetail(data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   updateDisplay();
                                }
                            });

                        } else {
                            alertError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception:", e);
                    } catch (JSONException e){
                        Log.e(TAG, "Exception:", e);
                    }
                }
            });
        }else{
            Toast.makeText(this,getString(R.string.net_message),Toast.LENGTH_LONG).show();
        }
    }

    private void toggleRefresh() {
        if(mProcessBar.getVisibility()==View.INVISIBLE) {
            mProcessBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }else{
            mProcessBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        Weather mWeather=mForecst.getWeather();
        mTemperatureLabel.setText(mWeather.getTemperature() + "");
        mTime.setText("At " + mWeather.getFormatTime() + " it will be");
        mHumidityValue.setText(mWeather.getHumidt()+ "");
        mPrecipValue.setText(mWeather.getPrecipChange() + "%");
        mSummaryLabel.setText(mWeather.getSummary());

       Drawable drawable = getResources().getDrawable(mWeather.getIconId());
       mIconImageView.setImageDrawable(drawable);
  }

  private Forecst parseForecastDetail(String data) throws JSONException {
      Forecst forecst=new Forecst();
      forecst.setWeather(getCurrentDetail(data));
      forecst.setDay(getDailyForecast(data));
      forecst.setHour(getHourlyForecast(data));
      return forecst;
  }

    private Hour[] getHourlyForecast(String data) throws JSONException {
        JSONObject forecastDate=new JSONObject(data);
        String timeZone=forecastDate.getString("timezone");
        JSONObject hourly=forecastDate.getJSONObject("hourly");
        JSONArray HourData=hourly.getJSONArray("data");

        Hour[] hours=new Hour[HourData.length()];
        for(int i=0;i<HourData.length();i++){
            JSONObject jsonHour=HourData.getJSONObject(i);
            Hour hour=new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timeZone);
            hours[i]=hour;
        }
        return hours;
    }

    private Day[] getDailyForecast(String data) throws JSONException {
        JSONObject forecastDate=new JSONObject(data);
        String timeZone=forecastDate.getString("timezone");

        JSONObject dailyObj=forecastDate.getJSONObject("daily");
        JSONArray dailyDate=dailyObj.getJSONArray("data");
        Day[] days=new Day[dailyDate.length()];
        for(int i=0;i<dailyDate.length();i++){
            JSONObject jsonDaily=dailyDate.getJSONObject(i);
            Day day=new Day();
            day.setSummary(jsonDaily.getString("summary"));
            day.setTemperatureMax(jsonDaily.getDouble("temperatureMax"));
            day.setIcon(jsonDaily.getString("icon"));
            day.setTime(jsonDaily.getLong("time"));
            day.setTimezone(timeZone);
            days[i]=day;
        }
        return days;
    }

    private Weather getCurrentDetail(String data) throws JSONException {
        JSONObject forecastDate=new JSONObject(data);
        String timeZone=forecastDate.getString("timezone");
        Log.i(TAG,"from JSON:"+timeZone);

        JSONObject current=forecastDate.getJSONObject("currently");
        Weather currentWea=new Weather();
        currentWea.setHumidt(current.getDouble("humidity"));
        currentWea.setIcon(current.getString("icon"));
        currentWea.setPrecipChange(current.getDouble("precipIntensity"));
        currentWea.setTime(current.getLong("time"));
        currentWea.setSummary(current.getString("summary"));
        currentWea.setTemperature(current.getDouble("temperature"));
        currentWea.setTimeZone(timeZone);

        Log.d(TAG,currentWea.getFormatTime());
        return currentWea;
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

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent=new Intent(this, DailyForecastActivity.class);
        startActivity(intent);
    }
}
