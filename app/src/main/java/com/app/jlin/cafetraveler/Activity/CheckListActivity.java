package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.databinding.ActivityCheckListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by stanley.lin on 2018/4/9.
 */

public class CheckListActivity extends BaseActivity {

    private ActivityCheckListBinding binding;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
        initEvent();
        initFirstSpinner();
    }
    RealmResults<RMCafe> allCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();
    List<RMCafe> filterCafeList;
    ArrayList<String> checkedList = new ArrayList<>();

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
                    Boolean[] resultArray = new Boolean[3];
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
        adapter = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.line_strings, android.R.layout.simple_spinner_dropdown_item);
        binding.spLine.setAdapter(adapter);
        binding.spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        RMCafe[] tempArray = new RMCafe[allCafeList.size()];
                        allCafeList.toArray(tempArray);
                        filterCafeList = Arrays.asList(tempArray);
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.empty_array, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 1:
                        filterCafeList = lineFilter("red");
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.red_lines, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 2:
                        filterCafeList = lineFilter("brown");
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.brown_lines, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 3:
                        filterCafeList = lineFilter("green");
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.green_lines, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 4:
                        filterCafeList = lineFilter("orange");
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.orange_lines, android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case 5:
                        filterCafeList = lineFilter("blue");
                        adapter2 = ArrayAdapter.createFromResource(CheckListActivity.this, R.array.blue_lines, android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
                binding.spStation.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private ArrayList<RMCafe> lineFilter(String line){
        ArrayList<RMCafe> tempList = new ArrayList<>();
        for(RMCafe rmCafe : allCafeList){
            switch(line){
                case "red":
                    if(rmCafe.isRedLine()) tempList.add(rmCafe);
                    break;
                case"brown":
                    if(rmCafe.isBrownLine()) tempList.add(rmCafe);
                    break;
                case"green":
                    if(rmCafe.isGreenLine()) tempList.add(rmCafe);
                    break;
                case"orange":
                    if(rmCafe.isOrangeLine()) tempList.add(rmCafe);
                    break;
                case"blue":
                    if(rmCafe.isBlueLine()) tempList.add(rmCafe);
                    break;
            }
        }
        return tempList;
    }
}
