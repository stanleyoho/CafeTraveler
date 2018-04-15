package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.Model.MrtModel;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
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

    private ActivityCheckListBinding binding;
    private RealmResults<RMCafe> allCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();
    private List<RMCafe> filterCafeList;
    private ArrayList<String> checkedList = new ArrayList<>();
    private String[] redStationArray,blueStationArray,greenStationArray,orangeStationArray,brownStationArray;
    int selectedMrtType = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
        initStationArray();
        initEvent();
        initFirstSpinner();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initEvent() {
        BtnEvent btnEvent = new BtnEvent();
        binding.btnCheckAll.setOnClickListener(btnEvent);
        binding.btnOk.setOnClickListener(btnEvent);
        binding.btnCancel.setOnClickListener(btnEvent);
    }

    private void initStationArray(){
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
                    binding.cbWifi.setChecked(true);
                    binding.cbQuiet.setChecked(true);
                    binding.cbSeat.setChecked(true);
                    break;
                case R.id.btn_ok:
                    if (binding.cbWifi.isChecked() || binding.cbSeat.isChecked() || binding.cbQuiet.isChecked()) {
                        for (RMCafe rmCafe : filterCafeList) {
                            if (binding.cbWifi.isChecked()) {
                                if (binding.cbSeat.isChecked()) {
                                    if (binding.cbQuiet.isChecked()) {
                                        if (rmCafe.getWifi() > 4 && rmCafe.getSeat() > 4 && rmCafe.getQuiet() > 4)
                                            checkedList.add(rmCafe.getId());
                                    } else {
                                        if (rmCafe.getWifi() > 4 && rmCafe.getSeat() > 4)
                                            checkedList.add(rmCafe.getId());
                                    }
                                } else {
                                    if (binding.cbQuiet.isChecked()) {
                                        if (rmCafe.getQuiet() > 4) checkedList.add(rmCafe.getId());
                                    } else {
                                        if (rmCafe.getWifi() > 4) checkedList.add(rmCafe.getId());
                                    }
                                }
                            } else if (binding.cbSeat.isChecked()) {
                                if (binding.cbQuiet.isChecked()) {
                                    if (rmCafe.getSeat() > 4 && rmCafe.getQuiet() > 4)
                                        checkedList.add(rmCafe.getId());
                                } else {
                                    if (rmCafe.getSeat() > 4) checkedList.add(rmCafe.getId());
                                }
                            } else if (binding.cbQuiet.isChecked()) {
                                if (rmCafe.getQuiet() > 4) checkedList.add(rmCafe.getId());
                            }
                        }
                        Log.d("checkedListSize", Integer.toString(checkedList.size()));
                        Intent intent = new Intent();
                        intent.putStringArrayListExtra("checkedCafeList", checkedList);
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
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
        checkedList.clear();
        Log.d("onPause", "clear checked choice");
        Log.d("afterClear", checkedList.toString());
    }

    private void initFirstSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.line_strings, android.R.layout.simple_spinner_dropdown_item);
        binding.spLine.setAdapter(adapter);
        binding.spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> stationSpinnerAdapter = null;
                switch (i) {
                    case 0:
                        RMCafe[] tempArray = new RMCafe[allCafeList.size()];
                        allCafeList.toArray(tempArray);
                        filterCafeList = Arrays.asList(tempArray);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,new String[0]);
                        break;
                    case 1:
                        filterCafeList = lineFilter(Constants.LINE_RED);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,redStationArray);
                        selectedMrtType = Constants.LINE_RED;
                        break;
                    case 2:
                        filterCafeList = lineFilter(Constants.LINE_BROWN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,brownStationArray);
                        selectedMrtType = Constants.LINE_BROWN;
                        break;
                    case 3:
                        filterCafeList = lineFilter(Constants.LINE_GREEN);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,greenStationArray);
                        selectedMrtType = Constants.LINE_GREEN;
                        break;
                    case 4:
                        filterCafeList = lineFilter(Constants.LINE_ORANGE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,orangeStationArray);
                        selectedMrtType = Constants.LINE_ORANGE;
                        break;
                    case 5:
                        filterCafeList = lineFilter(Constants.LINE_BLUE);
                        stationSpinnerAdapter = new ArrayAdapter<String>(CheckListActivity.this, android.R.layout.simple_spinner_item,blueStationArray);
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

    private ArrayList<RMCafe> lineFilter(int line){
        ArrayList<RMCafe> tempList = new ArrayList<>();
        tempList.addAll(RMCafe.getFilterResultByLine(line,null));
        return tempList;
    }

    private String[]  getLineStations(int lineId){
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
        try{
            is = getAssets().open("mrt_final.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String data = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(data);
            for(int i = 0 ; i < jsonArray.length() ; i++){
                MrtModel mrtModel = new Gson().fromJson(jsonArray.get(i).toString(), MrtModel.class);
                if(mrtModel.getStation_line_id().equals("BR")) {
                    brownLineStations.add(mrtModel.getStation_name_chinese());
                }
                if(mrtModel.getStation_line_id().equals("R")) {
                    redLineStations.add(mrtModel.getStation_name_chinese());
                }
                if(mrtModel.getStation_line_id().equals("G")) {
                    greenLineStations.add(mrtModel.getStation_name_chinese());
                }
                if(mrtModel.getStation_line_id().equals("O")) {
                    orangeLineStations.add(mrtModel.getStation_name_chinese());
                }
                if(mrtModel.getStation_line_id().equals("BL")) {
                    blueLineStations.add(mrtModel.getStation_name_chinese());
                }
            }
        }catch (Exception e){
            LogUtils.e("Exception",e.toString());
        }
        switch (lineId){
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
            if(position == 0){
                filterCafeList = RMCafe.getFilterResultByLine(selectedMrtType,null);
            }else{
                String station = parent.getSelectedItem().toString();
                filterCafeList = RMCafe.getFilterResultByLine(selectedMrtType,station);
                LogUtils.e("filterCafeList", String.valueOf(filterCafeList.size()));
                for(RMCafe rmCafe : filterCafeList){
                    LogUtils.e("cafe name : " , rmCafe.getMyMrt());
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
