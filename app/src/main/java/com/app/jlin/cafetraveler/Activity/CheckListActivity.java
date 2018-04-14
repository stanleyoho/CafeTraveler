package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.databinding.ActivityCheckListBinding;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by stanley.lin on 2018/4/9.
 */

public class CheckListActivity extends BaseActivity {

    private ActivityCheckListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
        initEvent();
    }

    RealmResults<RMCafe> getCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();
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
                        for (RMCafe rmCafe : getCafeList) {
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
                        Log.d("checkedListSize",Integer.toString(checkedList.size()));
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
}
