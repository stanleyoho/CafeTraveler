package com.app.jlin.cafetraveler.Manager;

import android.content.Context;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Model.MrtModel;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.Utils.LogUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stanley.lin on 2018/4/16.
 */

public class MrtDataManager {
    private static MrtDataManager mrtDataManager;
    private String[] redStationArray, blueStationArray, greenStationArray, orangeStationArray, brownStationArray;
    private List<MrtModel> mrtInfoList;

    public static MrtDataManager getInstance(){
        if(mrtDataManager == null){
            mrtDataManager = new MrtDataManager();
        }
        return mrtDataManager;
    }

    public void initData(Context context){
        redStationArray = getLineStations(context,Constants.LINE_RED);
        blueStationArray = getLineStations(context,Constants.LINE_BLUE);
        greenStationArray = getLineStations(context,Constants.LINE_GREEN);
        orangeStationArray = getLineStations(context,Constants.LINE_ORANGE);
        brownStationArray = getLineStations(context,Constants.LINE_BROWN);
        mrtInfoList = getMrtInfoList(context);
    }

    public String[] getRedStationArray() {
        return redStationArray;
    }

    public String[] getBlueStationArray() {
        return blueStationArray;
    }

    public String[] getGreenStationArray() {
        return greenStationArray;
    }

    public String[] getOrangeStationArray() {
        return orangeStationArray;
    }

    public String[] getBrownStationArray() {
        return brownStationArray;
    }

    public List<MrtModel> getMrtInfoList(){
        return mrtInfoList;
    }

    /**
     * 取得每個不同線上的所有捷運站, position = 請選擇
     * */
    private String[] getLineStations(Context context , int lineId) {
        List<String> redLineStations = new ArrayList<>();
        List<String> blueLineStations = new ArrayList<>();
        List<String> greenLineStations = new ArrayList<>();
        List<String> orangeLineStations = new ArrayList<>();
        List<String> brownLineStations = new ArrayList<>();

        redLineStations.add(context.getResources().getString(R.string.filter_choice));
        blueLineStations.add(context.getResources().getString(R.string.filter_choice));
        greenLineStations.add(context.getResources().getString(R.string.filter_choice));
        orangeLineStations.add(context.getResources().getString(R.string.filter_choice));
        brownLineStations.add(context.getResources().getString(R.string.filter_choice));

        InputStream is = null;
        try {
            is = context.getAssets().open("mrt_final.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String data = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                MrtModel mrtModel = new Gson().fromJson(jsonArray.get(i).toString(), MrtModel.class);
                if (mrtModel.getStation_line_id().equals("BR")) {
                    brownLineStations.add(mrtModel.getStation_name_chinese());
                }
                if (mrtModel.getStation_line_id().equals("R")) {
                    redLineStations.add(mrtModel.getStation_name_chinese());
                }
                if (mrtModel.getStation_line_id().equals("G")) {
                    greenLineStations.add(mrtModel.getStation_name_chinese());
                }
                if (mrtModel.getStation_line_id().equals("O")) {
                    orangeLineStations.add(mrtModel.getStation_name_chinese());
                }
                if (mrtModel.getStation_line_id().equals("BL")) {
                    blueLineStations.add(mrtModel.getStation_name_chinese());
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception", e.toString());
        }
        switch (lineId) {
            case Constants.LINE_BLUE:
                return blueLineStations.toArray(new String[blueLineStations.size()]);
            case Constants.LINE_RED:
                return redLineStations.toArray(new String[redLineStations.size()]);
            case Constants.LINE_GREEN:
                return greenLineStations.toArray(new String[greenLineStations.size()]);
            case Constants.LINE_ORANGE:
                return orangeLineStations.toArray(new String[orangeLineStations.size()]);
            case Constants.LINE_BROWN:
                return brownLineStations.toArray(new String[brownLineStations.size()]);
            default:
                return null;
        }
    }

    /**
     * 取得json file內所有捷運站的List
     */
    private List<MrtModel> getMrtInfoList(Context context) {
        List<MrtModel> mrtList = new ArrayList<>();

        InputStream is = null;
        try {
            is = context.getAssets().open("mrt_final.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mrtData = new String(buffer, "UTF-8");
            JSONArray mrtJsonArray = new JSONArray(mrtData);
            for (int i = 0; i < mrtJsonArray.length(); i++) {
                MrtModel mrtModel = new Gson().fromJson(mrtJsonArray.get(i).toString(), MrtModel.class);
                mrtList.add(mrtModel);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mrtList;
    }
}
