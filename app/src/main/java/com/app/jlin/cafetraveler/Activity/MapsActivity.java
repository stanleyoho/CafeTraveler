package com.app.jlin.cafetraveler.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.jlin.cafetraveler.Adapter.CafeAdapter;
import com.app.jlin.cafetraveler.Adapter.MapInfoAdapter;
import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.Interface.CafeListCallBack;
import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.Utils.LogUtils;
import com.app.jlin.cafetraveler.Utils.MapUtils;
import com.app.jlin.cafetraveler.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private final int REQUEST_CODE = 0;
    private ActivityMapsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        initRecyclerView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMap != null){
            mMap.clear();
            mMap = null;
        }
    }



    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * @param cafeList dataList of coffee shop
     * @param map googleMap
     * @param isFilterData isFilterData by CheckListActivity
     * */
    private void updateRecyclerView(List<RMCafe> cafeList, GoogleMap map, Boolean isFilterData) {
        CafeAdapter cafeAdapter = (CafeAdapter) binding.recycler.getAdapter();
        if (cafeAdapter == null) {
            cafeAdapter = new CafeAdapter(this, cafeList, cafeListCallBack,map);
            binding.recycler.setAdapter(cafeAdapter);
        } else {
            cafeAdapter.updateData(this, cafeList, map, isFilterData);
        }
    }



    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            //init zoomPreference
            mMap.setMaxZoomPreference(16f);
            mMap.setMinZoomPreference(3f);

            //check location permission
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MapsActivity.this, "Please open location function", Toast.LENGTH_SHORT).show();
            } else {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                    @Override
                    public void onMyLocationClick(@NonNull Location location) {
                        mMap.animateCamera(MapUtils.getCameraLatLngZoom(location.getLatitude(),location.getLongitude(),18));
                    }
                });
            }

            /**
             * 利用LocationManager取得當前所在位置
             * 並於google map開啟時顯示當前位置
             * 若沒有最後位置則開啟時定位於台北車站
             */
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener(){

                @Override
                public void onLocationChanged(Location location) {
                    LogUtils.e("onLocationChanged","onLocationChanged");
                    Toast.makeText(MapsActivity.this,"onLocationChanged",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    LogUtils.e("onStatusChanged","onStatusChanged");
                    Toast.makeText(MapsActivity.this,"onStatusChanged",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderEnabled(String provider) {
                    LogUtils.e("onProviderEnabled","onProviderEnabled");
                    Toast.makeText(MapsActivity.this,"onProviderEnabled",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    LogUtils.e("onProviderDisabled","onProviderDisabled");
                    Toast.makeText(MapsActivity.this,"請打開GPS，以獲得更好的服務",Toast.LENGTH_SHORT).show();
                }
            });

            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                mMap.animateCamera(MapUtils.getCameraLatLngZoom(location.getLatitude(),location.getLongitude(),12));
            }else{
                mMap.animateCamera(MapUtils.getCameraLatLngZoom(Constants.LOCATION_TAIPIE_STATION_LAT,Constants.LOCATION_TAIPIE_STATION_LNG,12));
            }
            //recyclerView setAdapter
            updateRecyclerView(RealmManager.getInstance().getRealm().where(RMCafe.class).findAll(), mMap, false);

            mMap.setInfoWindowAdapter(new MapInfoAdapter(MapsActivity.this));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
//                    //點擊圖案變形
//                    //TODO : 點其他復原
//                    if(marker != null){
//                        BitmapDrawable bitmapDrawable = (BitmapDrawable) MapsActivity.this.getResources().getDrawable(R.drawable.ic_mymarker);
//                        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(),200,200,false);
//                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
//                    }
                    return false;
                }
            });

        }
    };

    private CafeListCallBack cafeListCallBack = new CafeListCallBack() {

        @Override
        public void moveToPosition(RMCafe rmCafe) {
            mMap.animateCamera(MapUtils.getCameraLatLngZoom(rmCafe.getLatitude() + 0.001, rmCafe.getLongitude(),16));
            LogUtils.e("RMCafe","*************************************");
            LogUtils.e("RMCafe","Name :" + rmCafe.getName());
            LogUtils.e("RMCafe","Station :" + rmCafe.getNearestAnnotation());
            LogUtils.e("RMCafe","WiFi :" + rmCafe.getWifi());
            LogUtils.e("RMCafe","Seat :" + rmCafe.getSeat());
            LogUtils.e("RMCafe","Socket :" + rmCafe.getSocket());
            LogUtils.e("RMCafe","Cheap :" + rmCafe.getCheap());
            LogUtils.e("RMCafe","Limited_time :" + rmCafe.getLimited_time());
            LogUtils.e("RMCafe","*************************************");
        }

        @Override
        public void moveToTop() {
            CafeAdapter cafeAdapter = (CafeAdapter)binding.recycler.getAdapter();
            if(cafeAdapter != null){
                binding.recycler.smoothScrollToPosition(0);
            }
        }

        @Override
        public void moveToBottom() {
            CafeAdapter cafeAdapter = (CafeAdapter)binding.recycler.getAdapter();
            if(cafeAdapter != null){
                binding.recycler.smoothScrollToPosition(cafeAdapter.getItemCount()-1);
            }
        }
    };

    public void toCheckList(View view) {
        Intent intent = new Intent(this, CheckListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    ArrayList<String> checkedListId = data.getExtras().getStringArrayList("checkedCafeList");
                    List<RMCafe> checkedList = new ArrayList<>();
                    if (checkedListId != null) {
                        for (String cafeId : checkedListId) {
                            RMCafe rmCafe = RealmManager.getInstance().getRealm().where(RMCafe.class).equalTo("id", cafeId).findFirst();
                            checkedList.add(rmCafe);
                        }
                        updateRecyclerView(checkedList, mMap, true);
                    } else {
                    }
                    break;
                case RESULT_CANCELED:
                    RealmResults<RMCafe> getCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();
                    updateRecyclerView(getCafeList, mMap, false);
                    break;
            }
        }
    }

}

