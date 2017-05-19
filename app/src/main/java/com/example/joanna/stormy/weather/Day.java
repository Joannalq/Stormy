package com.example.joanna.stormy.weather;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Day {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private String mTimezone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperatureMax() {
        return (int)Math.round(mTemperatureMax);
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }
    public int getIconId(){ return Forecst.getIconId(mIcon); }

    public String getDayOfWeek(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(mTimezone));
        Date data=new Date(mTime*1000);
        return simpleDateFormat.format(data);
    }
}
