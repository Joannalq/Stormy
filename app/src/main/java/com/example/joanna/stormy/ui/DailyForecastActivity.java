package com.example.joanna.stormy.ui;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.example.joanna.stormy.R;
import com.example.joanna.stormy.adapters.DayAdapter;
import com.example.joanna.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
       // String[] daysOfTheWeek = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        //1st param:context,2nd:id of layout, 3rd:array
      //  ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,daysOfTheWeek);
        //setListAdapter(adapter);
        DayAdapter adapter=new DayAdapter(this,mDays);
    }

}
