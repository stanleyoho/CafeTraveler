package com.app.jlin.cafetraveler.Model;

import java.io.Serializable;

/**
 * Created by stanley.lin on 2018/4/2.
 */

public class MrtModel implements Serializable  {
    private int station_location_code;
    private String station_id;
    private String station_address;
    private String station_name_english;
    private String station_name_chinese;
    private String station_line_name;
    private String station_line_id;
    private double latitude;
    private double longitude;

    public int getStation_location_code() {
        return station_location_code;
    }

    public void setStation_location_code(int station_location_code) {
        this.station_location_code = station_location_code;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_address() {
        return station_address;
    }

    public void setStation_address(String station_address) {
        this.station_address = station_address;
    }

    public String getStation_name_english() {
        return station_name_english;
    }

    public void setStation_name_english(String station_name_english) {
        this.station_name_english = station_name_english;
    }

    public String getStation_name_chinese() {
        return station_name_chinese;
    }

    public void setStation_name_chinese(String station_name_chinese) {
        this.station_name_chinese = station_name_chinese;
    }

    public String getStation_line_name() {
        return station_line_name;
    }

    public void setStation_line_name(String station_line_name) {
        this.station_line_name = station_line_name;
    }

    public String getStation_line_id() {
        return station_line_id;
    }

    public void setStation_line_id(String station_line_id) {
        this.station_line_id = station_line_id;
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
}
