package com.app.jlin.cafetraveler.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.jlin.cafetraveler.Adapter.MySpinnerAdapter;
import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.databinding.ActivityCheckListBinding;

/**
 * Created by stanley.lin on 2018/4/9.
 */

public class CheckListActivity extends BaseActivity {

    private ActivityCheckListBinding binding;
    private int[] mrtLines;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
        mrtLines = new int[]{1, 2, 3, 4, 5};

        binding.spLine.setAdapter(new MySpinnerAdapter(this, mrtLines));
        binding.spLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter arrayAdapter = null;
                switch (mrtLines[position]) {
                    case Constants.LINE_RED:
                        arrayAdapter = new ArrayAdapter(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[]{"1", "2", "3"});
                        break;
                    case Constants.LINE_BLUE:
                        arrayAdapter = new ArrayAdapter(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[]{"2", "2", "3"});
                        break;
                    case Constants.LINE_GREEN:
                        arrayAdapter = new ArrayAdapter(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[]{"3", "2", "3"});
                        break;
                    case Constants.LINE_ORANGE:
                        arrayAdapter = new ArrayAdapter(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[]{"4", "2", "3"});
                        break;
                    case Constants.LINE_BROWN:
                        arrayAdapter = new ArrayAdapter(CheckListActivity.this, android.R.layout.simple_spinner_item, new String[]{"5", "2", "3"});
                        break;
                }
                binding.spStation.setAdapter(arrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}