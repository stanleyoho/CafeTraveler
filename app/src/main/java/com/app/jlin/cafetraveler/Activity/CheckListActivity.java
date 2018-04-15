package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.Model.MrtModel;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.SharePreferences.FilterCheckPreferences;
import com.app.jlin.cafetraveler.Utils.LogUtils;
import com.app.jlin.cafetraveler.databinding.ActivityCheckListBinding;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by stanley.lin on 2018/4/9.
 */

public class CheckListActivity extends BaseActivity {
    private FilterCheckPreferences preferences;
    private ActivityCheckListBinding binding;
    private RealmResults<RMCafe> allCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();
    private List<RMCafe> filterCafeList;
    private ArrayList<String> checkedList = new ArrayList<>();
    private String[] redStationArray, blueStationArray, greenStationArray, orangeStationArray, brownStationArray;
    private Object[] checkedChoice = new Object[6]; // 0:wifi 1:seat 2:socket 3:timeLimit 4:line 5:station
    private int wifi,seat;
    private String socket,timeLimit;
    int selectedMrtType = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
        preferences = new FilterCheckPreferences(this);
        initRadioGroup();
        initStationArray();
        initEvent();
        initFirstSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.rgWifi.check(preferences.getWifi());
        binding.rgSeat.check(preferences.getSeat());
        binding.rgSocket.check(preferences.getSocket());
        binding.rgTimeLimit.check(preferences.getTimeLimit());
        binding.spLine.setSelection(preferences.getLine());
//        binding.spStation.setSelection(preferences.getStation());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preferences.setWifi(R.id.radio_wifi_lv1);
        preferences.setSeat(R.id.radio_seat_lv1);
        preferences.setSocket(R.id.radio_socket_no);
        preferences.setTimeLimit(R.id.radio_timeLimit_no);
        preferences.setLine(0);
    }

    private void initRadioGroup() {
        binding.rgWifi.check(R.id.radio_wifi_lv1);
        wifi = 1;
        binding.rgWifi.setOnCheckedChangeListener(new onWifiChangeListener());
        binding.rgSeat.check(R.id.radio_seat_lv1);
        seat = 1;
        binding.rgSeat.setOnCheckedChangeListener(new onSeatChangeListener());
        binding.rgSocket.check(R.id.radio_socket_no);
        socket = "no";
        binding.rgSocket.setOnCheckedChangeListener(new onSocketChangeListener());
        binding.rgTimeLimit.check(R.id.radio_timeLimit_no);
        timeLimit = "no";
        binding.rgTimeLimit.setOnCheckedChangeListener(new onTimeLimitChangeListener());
    }

    private void initEvent() {
        BtnEvent btnEvent = new BtnEvent();
        binding.btnCheckAll.setOnClickListener(btnEvent);
        binding.btnOk.setOnClickListener(btnEvent);
        binding.btnCancel.setOnClickListener(btnEvent);
    }

    private void initStationArray() {
        redStationArray = getLineStations(Constants.LINE_RED);
        blueStationArray = getLineStations(Constants.LINE_BLUE);
        greenStationArray = getLineStations(Constants.LINE_GREEN);
        orangeStationArray = getLineStations(Constants.LINE_ORANGE);
        brownStationArray = getLineStations(Constants.LINE_BROWN);
    }

    private class BtnEvent implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId) {
                case R.id.btn_checkAll:
                    binding.rgWifi.check(R.id.radio_wifi_lv5);
                    binding.rgSeat.check(R.id.radio_seat_lv5);
                    binding.rgSocket.check(R.id.radio_socket_yes);
                    binding.rgTimeLimit.check(R.id.radio_timeLimit_no);
                    break;
                case R.id.btn_ok:
                    checkedChoice[0] = wifi;
                    checkedChoice[1] = seat;
                    checkedChoice[2] = socket;
                    checkedChoice[3] = timeLimit;
                    checkedChoice[4] = selectedMrtType;
                    filterCafeList = RMCafe.getFilterResult(checkedChoice);
                    for (RMCafe rmCafe : filterCafeList) {
                        checkedList.add(rmCafe.getId());
                    }
                    Log.d("checkedListSize", Integer.toString(checkedList.size()));
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra("checkedCafeList", checkedList);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case R.id.btn_cancel:
                    setResult(RESULT_CANCELED);
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        preferences.setWifi(binding.rgWifi.getCheckedRadioButtonId());
        preferences.setSeat(binding.rgSeat.getCheckedRadioButtonId());
        preferences.setSocket(binding.rgSocket.getCheckedRadioButtonId());
        preferences.setTimeLimit(binding.rgTimeLimit.getCheckedRadioButtonId());
        preferences.setLine(selectedMrtType);
    }

    private void initFirstSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.line_strings, android.R.layout.simple_spinner_dropdown_item);
        binding.spLine.setAdapter(adapter);
        binding.spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> stationSpinnerAdapter = null;
//                preferences.setLine(i);
                switch (i) {
                    case 0:
                        RMCafe[] tempArray = new RMCafe[allCafeList.size()];
                        allCafeList.toArray(tempArray);
                        filterCafeList = Arrays.asList(tempArray);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[0]);
                        selectedMrtType = 0;
                        break;
                    case 1:
                        filterCafeList = lineFilter(Constants.LINE_RED);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, redStationArray);
                        selectedMrtType = Constants.LINE_RED;
                        break;
                    case 2:
                        filterCafeList = lineFilter(Constants.LINE_BROWN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, brownStationArray);
                        selectedMrtType = Constants.LINE_BROWN;
                        break;
                    case 3:
                        filterCafeList = lineFilter(Constants.LINE_GREEN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, greenStationArray);
                        selectedMrtType = Constants.LINE_GREEN;
                        break;
                    case 4:
                        filterCafeList = lineFilter(Constants.LINE_ORANGE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, orangeStationArray);
                        selectedMrtType = Constants.LINE_ORANGE;
                        break;
                    case 5:
                        filterCafeList = lineFilter(Constants.LINE_BLUE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, blueStationArray);
                        selectedMrtType = Constants.LINE_BLUE;
                        break;
                }
                binding.spStation.setAdapter(stationSpinnerAdapter);
                binding.spStation.setOnItemSelectedListener(ItemSelectedListener);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<RMCafe> lineFilter(int line) {
        ArrayList<RMCafe> tempList = new ArrayList<>();
        tempList.addAll(RMCafe.getFilterResultByLine(line, null));
        return tempList;
    }

    private String[] getLineStations(int lineId) {
        List<String> redLineStations = new ArrayList<>();
        List<String> blueLineStations = new ArrayList<>();
        List<String> greenLineStations = new ArrayList<>();
        List<String> orangeLineStations = new ArrayList<>();
        List<String> brownLineStations = new ArrayList<>();

        redLineStations.add(getResources().getString(R.string.filter_choice));
        blueLineStations.add(getResources().getString(R.string.filter_choice));
        greenLineStations.add(getResources().getString(R.string.filter_choice));
        orangeLineStations.add(getResources().getString(R.string.filter_choice));
        brownLineStations.add(getResources().getString(R.string.filter_choice));

        InputStream is = null;
        try {
            is = getAssets().open("mrt_final.json");
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

    private AdapterView.OnItemSelectedListener ItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                checkedChoice[5] = null;
            } else {
                checkedChoice[5] = parent.getSelectedItem().toString();
            }
//           preferences.setStation(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private class onWifiChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radio_wifi_lv1:
                    wifi = 1;
                    break;
                case R.id.radio_wifi_lv2:
                    wifi = 2;
                    break;
                case R.id.radio_wifi_lv3:
                    wifi = 3;
                    break;
                case R.id.radio_wifi_lv4:
                    wifi = 4;
                    break;
                case R.id.radio_wifi_lv5:
                    wifi = 5;
                    break;
            }
        }
    }

    private class onSeatChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radio_seat_lv1:
                    seat = 1;
                    break;
                case R.id.radio_seat_lv2:
                    seat = 2;
                    break;
                case R.id.radio_seat_lv3:
                    seat = 3;
                    break;
                case R.id.radio_seat_lv4:
                    seat = 4;
                    break;
                case R.id.radio_seat_lv5:
                    seat = 5;
                    break;
            }
        }
    }

    private class onSocketChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radio_socket_no:
                    socket = "no";
                    break;
                case R.id.radio_socket_maybe:
                    socket = "maybe";
                    break;
                case R.id.radio_socket_yes:
                    socket = "yes";
                    break;
            }
        }
    }

    private class onTimeLimitChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radio_timeLimit_no:
                    timeLimit = "no";
                    break;
                case R.id.radio_timeLimit_maybe:
                    timeLimit = "maybe";
                    break;
                case R.id.radio_timeLimit_yes:
                    timeLimit = "yes";
                    break;
            }
        }
    }
}
