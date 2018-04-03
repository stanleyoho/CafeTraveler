package com.app.jlin.cafetraveler.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.app.jlin.cafetraveler.BR;

/**
 * Created by stanley.lin on 2018/4/2.
 */

public class CafeViewModel extends BaseObservable{
    private String name;
    private String lon;
    private String lat;
    private String mrt;
    private String line;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
        notifyPropertyChanged(BR.lon);
    }

    @Bindable
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
        notifyPropertyChanged(BR.lat);
    }

    @Bindable
    public String getMrt() {
        return mrt;
    }

    public void setMrt(String mrt) {
        this.mrt = mrt;
        notifyPropertyChanged(BR.mrt);
    }

    @Bindable
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
        notifyPropertyChanged(BR.line);
    }
}
