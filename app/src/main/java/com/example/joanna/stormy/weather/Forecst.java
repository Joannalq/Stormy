package com.example.joanna.stormy.weather;


import com.example.joanna.stormy.R;

public class Forecst {
    private Weather mWeather;
    private Hour[] mHour;
    private Day[] mDay;


    public Weather getWeather() {
        return mWeather;
    }

    public void setWeather(Weather weather) {
        mWeather = weather;
    }

    public Day[] getDay() {
        return mDay;
    }

    public void setDay(Day[] day) {
        mDay = day;
    }

    public Hour[] getHour() {
        return mHour;
    }

    public void setHour(Hour[] hour) {
        mHour = hour;
    }
    public static int getIconId(String mIcon){

        int iconID= R.drawable.clear_day;
        if(mIcon.equals("clear-day")){
            iconID=R.drawable.clear_day;
        }else if(mIcon.equals("clear-night")){
            iconID=R.drawable.clear_night;
        }else if (mIcon.equals("rain")) {
            iconID = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconID = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconID = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconID = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconID = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconID = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconID = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconID = R.drawable.cloudy_night;
        }
        return iconID;
    }
}
