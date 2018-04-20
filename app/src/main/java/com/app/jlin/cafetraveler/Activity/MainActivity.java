package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.app.jlin.cafetraveler.Builder.PermissionBuilder;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.Utils.Utils;
import com.app.jlin.cafetraveler.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        checkPermission();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    private void initEvent() {
        BtnEvent btnEvent = new BtnEvent();
        binding.btnMap.setOnClickListener(btnEvent);
    }

    private void checkPermission() {
        if(!Utils.isNeedRequestPermission()){
            return;
        }
        PermissionBuilder permissionBuilder = new PermissionBuilder.Builder(getApplicationContext())
                .checkLocationState()
                .builder();

        String[] permissions = permissionBuilder.getPermissions();

        if(permissions != null && permissions.length > 0){
            ActivityCompat.requestPermissions(this,permissions,2000);
        }
    }


    private class BtnEvent implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId){
                case R.id.btn_map:
                    startActivity(new Intent(MainActivity.this,MapsActivity.class));
                    break;
            }
        }
    }

}
