package com.app.jlin.cafetraveler.RealmModel;

import android.util.Log;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.Utils.Utils;

import java.io.Serializable;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jennifer on 2018/3/29.
 */

public class RMCafe extends RealmObject implements Serializable {
    @PrimaryKey
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

    private String myMrt;
    private String mrtLine;
    private boolean isRedLine;
    private boolean isGreenLine;
    private boolean isBlueLine;
    private boolean isOrangeLine;
    private boolean isBrownLine;

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
        this.name = Utils.unicodeToUtf8(name);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = Utils.unicodeToUtf8(city);
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

    public String getMyMrt() {
        return myMrt;
    }

    public void setMyMrt(String myMrt) {
        this.myMrt = myMrt;
    }

    public boolean isRedLine() {
        return isRedLine;
    }

    public void setRedLine(boolean redLine) {
        isRedLine = redLine;
    }

    public boolean isGreenLine() {
        return isGreenLine;
    }

    public void setGreenLine(boolean greenLine) {
        isGreenLine = greenLine;
    }

    public boolean isBlueLine() {
        return isBlueLine;
    }

    public void setBlueLine(boolean blueLine) {
        isBlueLine = blueLine;
    }

    public boolean isOrangeLine() {
        return isOrangeLine;
    }

    public void setOrangeLine(boolean orangeLine) {
        isOrangeLine = orangeLine;
    }

    public boolean isBrownLine() {
        return isBrownLine;
    }

    public void setBrownLine(boolean brownLine) {
        isBrownLine = brownLine;
    }

    public String getMrtLine() {
        return mrtLine;
    }

    public void setMrtLine() {
        StringBuffer sb = new StringBuffer();
        sb.append(isRedLine() ? "紅 " : "")
                .append(isBlueLine() ? "藍 " : "")
                .append(isGreenLine() ? "綠 " : "")
                .append(isBrownLine() ? "咖啡 " : "")
                .append(isOrangeLine() ? "橘 " : "");
        mrtLine = sb.toString();
    }

    /**
     * 新增單筆訊息
     */
    public static void add(RMCafe cafe) {
        Realm realm = RealmManager.getInstance().getRealm();
        realm.beginTransaction();
        realm.copyToRealm(cafe);
        realm.commitTransaction();
    }

    /**
     * 一次新增多筆訊息
     */
    public static void addAll(List<RMCafe> cafeList) {
        Realm realm = RealmManager.getInstance().getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(cafeList);
        realm.commitTransaction();
    }

    /**
     * 取回 單筆訊息
     */
    public static RMCafe getOne(String id) {
        Realm realm = RealmManager.getInstance().getRealm();
        return realm.where(RMCafe.class).equalTo("id", id).findFirst();
    }

    /**
     * 取回 全部訊息
     */
    public static RealmResults<RMCafe> getAll() {
        Realm realm = RealmManager.getInstance().getRealm();

        return realm.where(RMCafe.class).findAll();
    }

    /**
     * 刪除全部訊息
     */
    public static void deleteAll() {
        Realm realm = RealmManager.getInstance().getRealm();
        RealmResults<RMCafe> cafeList = realm.where(RMCafe.class).findAll();
        if (cafeList == null) return;
        realm.beginTransaction();
        cafeList.deleteAllFromRealm();
        realm.commitTransaction();
    }

    /**
     * 取得某條或特定捷運站咖啡店
     *
     * @param lineType    MrtType
     * @param stationName Station chinese name
     */
    public static RealmResults<RMCafe> getFilterResultByLine(int lineType, String stationName) {
        Realm realm = RealmManager.getInstance().getRealm();
        RealmQuery<RMCafe> query = realm.where(RMCafe.class);

        switch (lineType) {
            case Constants.LINE_BLUE:
                query.equalTo("isBlueLine", true);
                break;
            case Constants.LINE_BROWN:
                query.equalTo("isBrownLine", true);
                break;
            case Constants.LINE_RED:
                query.equalTo("isRedLine", true);
                break;
            case Constants.LINE_GREEN:
                query.equalTo("isGreenLine", true);
                break;
            case Constants.LINE_ORANGE:
                query.equalTo("isOrangeLine", true);
                break;
        }
        if (stationName != null) {
            query.and().equalTo("myMrt", stationName);
        }
        return query.findAll();
    }

    public static RealmResults<RMCafe> getFilterResult(Object[] checkChoice) {
        Realm realm = RealmManager.getInstance().getRealm();
        RealmQuery<RMCafe> query = realm.where(RMCafe.class);
        int wifi, seat, quiet, lineType;
        String stationName;
        wifi = (int) checkChoice[0];
        seat = (int) checkChoice[1];
        quiet = (int) checkChoice[2];
        lineType = (int) checkChoice[3];
        Log.d("line",Integer.toString(lineType));
        stationName = (String) checkChoice[4];
        switch (lineType) {
            case Constants.LINE_BLUE:
                query.equalTo("isBlueLine", true);
                break;
            case Constants.LINE_BROWN:
                query.equalTo("isBrownLine", true);
                break;
            case Constants.LINE_RED:
                query.equalTo("isRedLine", true);
                break;
            case Constants.LINE_GREEN:
                query.equalTo("isGreenLine", true);
                break;
            case Constants.LINE_ORANGE:
                query.equalTo("isOrangeLine", true);
                break;
        }
        if (stationName != null) {
            query.and().equalTo("myMrt", stationName);
        }
        query.and().greaterThan("wifi", wifi);
        query.and().greaterThan("seat", seat);
        query.and().greaterThan("quiet", quiet);
        return query.findAll();
    }
}
