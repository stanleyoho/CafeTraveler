package com.app.jlin.cafetraveler.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.databinding.ActivityCheckListBinding;

/**
 * Created by stanley.lin on 2018/4/9.
 */

public class CheckListActivity extends BaseActivity {

    private ActivityCheckListBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_list);
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
