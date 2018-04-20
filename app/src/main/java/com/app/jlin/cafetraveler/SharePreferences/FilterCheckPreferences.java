package com.app.jlin.cafetraveler.SharePreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.R;

/**
 * Created by jimmy on 2018/4/15.
 */

public class FilterCheckPreferences {
    private SharedPreferences checkedFilter;
    private static FilterCheckPreferences filterCheckPreferences;
    private Context mContext;
    private SharedPreferences.Editor filterEditor;

    public static FilterCheckPreferences getInstance(Context context){
        if(filterCheckPreferences == null){
            filterCheckPreferences = new FilterCheckPreferences(context);
        }
        return filterCheckPreferences;
    }

    private FilterCheckPreferences(Context context){
        this.checkedFilter = context.getApplicationContext().getSharedPreferences(Constants.FILTER_CHECKED_PREFERENCES,Context.MODE_PRIVATE);
        this.mContext = context;
        filterEditor = this.checkedFilter.edit();
    }

    public void setWifi(int wifi){
        filterEditor.putInt(Constants.PREFERENCES_WIFI_LEVEL,wifi);
        filterEditor.commit();
    }

    public int getWifi(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_WIFI_LEVEL, R.id.radio_wifi_lv1);
    }

    public void setSeat(int seat){
        filterEditor.putInt(Constants.PREFERENCES_SEAT_LEVEL,seat);
        filterEditor.commit();
    }

    public int getSeat(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_SEAT_LEVEL,R.id.radio_seat_lv1);
    }

    public void setCheap(int cheap){
        filterEditor.putInt(Constants.PREFERENCES_CHEAP_LEVEL,cheap);
        filterEditor.commit();
    }

    public int getCheap(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_CHEAP_LEVEL,R.id.radio_cheap_lv1);
    }

    public void setSocket(int socket){
        filterEditor.putInt(Constants.PREFERENCES_SOCKET_CHECK,socket);
        filterEditor.commit();
    }

    public int getSocket(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_SOCKET_CHECK,R.id.radio_socket_no);
    }

    public void setTimeLimit(int timeLimit){
        filterEditor.putInt(Constants.PREFERENCES_TIMELIMIT_CHECK,timeLimit);
        filterEditor.commit();
    }

    public int getTimeLimit(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_TIMELIMIT_CHECK,R.id.radio_timeLimit_no);
    }

    public void setLine(int line){
        filterEditor.putInt(Constants.PREFERENCES_LINE_ID,line);
        filterEditor.commit();
    }

    public int getLine(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_LINE_ID,0);
    }

    public void setStation(int station){
        filterEditor.putInt(Constants.PREFERENCES_STATION_POSITION,station);
        filterEditor.commit();
    }

    public int getStation(){
        return this.checkedFilter.getInt(Constants.PREFERENCES_STATION_POSITION,0);
    }

    public void clean(){
        filterEditor.clear();
        filterEditor.commit();
    }
}
