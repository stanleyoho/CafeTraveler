package com.app.jlin.cafetraveler.Activity;

import android.Manifest;
import android.content.Context;
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
import android.widget.Toast;

import com.app.jlin.cafetraveler.Adapter.CafeAdapter;
import com.app.jlin.cafetraveler.Interface.CafeListCallBack;
import com.app.jlin.cafetraveler.Manager.RealmManager;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import io.realm.RealmResults;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap;

    private ActivityMapsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);
    }

    private OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.setMaxZoomPreference(16f);
            mMap.setMinZoomPreference(3f);

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MapsActivity.this ,"Please open location function",Toast.LENGTH_SHORT).show();
            }else {
                mMap.setMyLocationEnabled(true);
            }

            /**
             * 利用LocationManager取得當前所在位置
             * 並於google map開啟時顯示當前位置
             */
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),12));


            RealmResults<RMCafe> getCafeList = RealmManager.getInstance().getRealm().where(RMCafe.class).findAll();

//            將此方法移至CafeListCallBack，使地圖上標記有點選咖啡店才顯示
//            for(RMCafe cafe:getCafeList){
//                LatLng cafeLat = new LatLng(cafe.getLatitude(),cafe.getLongitude());
//                MarkerOptions markerOptions = new MarkerOptions().position(cafeLat).title(cafe.getName());
//                mMap.addMarker(markerOptions);
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cafe.getLatitude(),cafe.getLongitude())));
//            }

            //recyclerView setAdapter
            binding.recycler.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
            binding.recycler.setAdapter(new CafeAdapter(MapsActivity.this,getCafeList,cafeListCallBack));

            mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),18);
                    mMap.animateCamera(cameraUpdate);
                }
            });
        }
    };

    private CafeListCallBack cafeListCallBack = new CafeListCallBack() {
        private Marker marker;
        @Override
        public void moveToPosition(RMCafe rmCafe) {
            if(marker != null)marker.remove();      //如果已存在標記則先清除
            LatLng cafeLat = new LatLng(rmCafe.getLatitude(),rmCafe.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(cafeLat).title(rmCafe.getName());
            marker = mMap.addMarker(markerOptions);
            marker.setTag(0);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(rmCafe.getLatitude(),rmCafe.getLongitude()),18);
            mMap.animateCamera(cameraUpdate);
        }
    };
}
