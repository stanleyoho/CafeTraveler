package com.app.jlin.cafetraveler.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.Tag.MyMarkerTag;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy on 2018/4/20.
 */

public class MyMarkerUtils {

    private static List<Marker> markerList = new ArrayList<>();

//    public static void setAll(List<Marker> markers){
//        markerList = markers;
//    }

    public static Bitmap smallIcon(Context context) {
        BitmapDrawable icon = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_mymarker);
        Bitmap bitmap = icon.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, 80, 160, false);
    }

    public static Bitmap largeIcon(Context context) {
        BitmapDrawable icon = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_mymarker_focus);
        Bitmap bitmap = icon.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, 120, 240, false);
    }

    public static Marker addMarker(GoogleMap map, RMCafe rmCafe, Bitmap icon) {
        LatLng place = new LatLng(rmCafe.getLatitude(), rmCafe.getLongitude());
        String title = rmCafe.getName();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(place)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(icon));
        Marker marker = map.addMarker(markerOptions);
        int iconSize = 0;
        switch (icon.getHeight()) {
            case 160:
                iconSize = 1;
                break;
            case 240:
                iconSize = 2;
                break;
        }
        setTag(marker, rmCafe, iconSize);
        markerList.add(marker);
        return marker;
    }

    private static Marker preMarker = null;
    private static Marker nowMarker = null;

    public static void cleanPreMarker(){
        preMarker = null;
    }

    public static void checkMarker(Context context,GoogleMap map, List<RMCafe> cafeList,RMCafe rmCafe) {
        String title = rmCafe.getName();
        if(preMarker == null){
            Log.d("STEP","inIfStmt");
            int position = -1;
            Marker marker = null;
            for(int i = 0;i<markerList.size();i++){
                Log.d("STEP","inForLoop");
                marker = markerList.get(i);
                if(marker.getTitle().equals(title)){
                    position = i;
                    Log.d("preMarkerIsNull",marker.getTitle());
                    break;
                }
            }
            if(position != -1){
                marker.remove();
//                markerList.get(position).remove();
            }
        }else{
            preMarker.remove();
        }
        Bitmap nowIcon = largeIcon(context);
        nowMarker = addMarker(map,rmCafe,nowIcon);
        Log.d("getMarker","preMarker = "+((preMarker == null)?"null":preMarker.getTitle())+"\tnowMarker = "+nowMarker.getTitle());
        preMarker = nowMarker;
        Log.d("getMarker","preMarker = "+preMarker.getTitle()+"\tnowMarker = "+nowMarker.getTitle());
    }

    public static void setTag(Marker marker, RMCafe rmCafe, int iconSize) {
        MyMarkerTag tag = new MyMarkerTag();
        tag.setName(rmCafe.getName());
        tag.setWifi(rmCafe.getWifi());
        tag.setCheap(rmCafe.getCheap());
        tag.setSeat(rmCafe.getSeat());
        switch (iconSize) {
            case 1:
                tag.setIcon("small");
                break;
            case 2:
                tag.setIcon("large");
                break;
        }
        marker.setTag(tag);
    }

}
