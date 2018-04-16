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
import com.app.jlin.cafetraveler.Manager.MrtDataManager;
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
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, MrtDataManager.getInstance().getRedStationArray());
                        selectedMrtType = Constants.LINE_RED;
                        break;
                    case 2:
                        filterCafeList = lineFilter(Constants.LINE_BROWN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, MrtDataManager.getInstance().getBrownStationArray());
                        selectedMrtType = Constants.LINE_BROWN;
                        break;
                    case 3:
                        filterCafeList = lineFilter(Constants.LINE_GREEN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, MrtDataManager.getInstance().getGreenStationArray());
                        selectedMrtType = Constants.LINE_GREEN;
                        break;
                    case 4:
                        filterCafeList = lineFilter(Constants.LINE_ORANGE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, MrtDataManager.getInstance().getOrangeStationArray());
                        selectedMrtType = Constants.LINE_ORANGE;
                        break;
                    case 5:
                        filterCafeList = lineFilter(Constants.LINE_BLUE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item, MrtDataManager.getInstance().getBlueStationArray());
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
