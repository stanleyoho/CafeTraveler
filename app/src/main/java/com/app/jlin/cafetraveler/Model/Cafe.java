package com.app.jlin.cafetraveler.Model;

import java.io.Serializable;

/**
 * Created by stanley.lin on 2018/3/27.
 */

public class Cafe implements Serializable {
    private String id;
    private String name;
    private String city;
    private int wifi;
    private int seat;
    private int quiet;
    private int tasty;
    private int cheap;
    private int music;
    private String url;
    private String address;
    private double latitude;
    private double longitude;
    private String limited_time;
    private String socket;
    private String standing_desk;
    private String mrt;
    private String open_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getQuiet() {
        return quiet;
    }

    public void setQuiet(int quiet) {
        this.quiet = quiet;
    }

    public int getTasty() {
        return tasty;
    }

    public void setTasty(int tasty) {
        this.tasty = tasty;
    }

    public int getCheap() {
        return cheap;
    }

    public void setCheap(int cheap) {
        this.cheap = cheap;
    }

    public int getMusic() {
        return music;
    }

    public void setMusic(int music) {
        this.music = music;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLimited_time() {
        return limited_time;
    }

    public void setLimited_time(String limited_time) {
        this.limited_time = limited_time;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getStanding_desk() {
        return standing_desk;
    }

    public void setStanding_desk(String standing_desk) {
        this.standing_desk = standing_desk;
    }

    public String getMrt() {
        return mrt;
    }

    public void setMrt(String mrt) {
        this.mrt = mrt;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }
}
