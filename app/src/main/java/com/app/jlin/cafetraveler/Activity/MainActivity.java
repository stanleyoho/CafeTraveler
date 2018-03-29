package com.app.jlin.cafetraveler.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.app.jlin.cafetraveler.Builder.PermissionBuilder;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.Service.TestService;
import com.app.jlin.cafetraveler.Utils.Utils;
import com.app.jlin.cafetraveler.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Intent startIntent = new Intent(this,TestService.class);
        startService(startIntent);

        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });


        binding.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(MainActivity.this,TestService.class);
                stopService(stopIntent);


            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
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


}
