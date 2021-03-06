package com.example.joanna.stormy.weather;


import com.example.joanna.stormy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Weather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidt;
    private double mPrecipChange;
    private String mSummary;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }
    public int getIconId(){
        return Forecst.getIconId(mIcon);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getFormatTime(){
        SimpleDateFormat formatter=new SimpleDateFormat("hh:mm");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime=new Date(getTime()*1000);
        String timeString=formatter.format(dateTime);
        return timeString;
    }

    public double getTemperature() {
        //get int part
        return (int)Math.round(mTemperature);
        //return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidt() {
        return mHumidt;
    }

    public void setHumidt(double humidt) {
        mHumidt = humidt;
    }

    public double getPrecipChange() {
        double precipPercentage=mPrecipChange*100;
        return Math.round(precipPercentage);
    }

    public void setPrecipChange(double precipChange) {
        mPrecipChange = precipChange;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
