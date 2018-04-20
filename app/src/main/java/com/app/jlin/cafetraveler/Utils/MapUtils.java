package com.app.jlin.cafetraveler.Utils;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by stanley.lin on 2018/4/20.
 */

public class MapUtils {

    public static CameraUpdate getCameraLatLngZoom(double latitude ,double longitude ,int zoom){
        return CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),zoom);
    }
}
