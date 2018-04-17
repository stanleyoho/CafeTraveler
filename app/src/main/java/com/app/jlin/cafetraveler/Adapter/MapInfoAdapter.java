package com.app.jlin.cafetraveler.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.Tag.MyMarkerTag;
import com.app.jlin.cafetraveler.databinding.ItemMarkerInfoBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Stanley_NB on 2018/4/17.
 */

public class MapInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public MapInfoAdapter(Context context){
        this.context = context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        ItemMarkerInfoBinding itemMarkerInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_marker_info,null,true);
        /**
         *獲取marker內的Tag資料，並新增至itemMarkerInfoBinding內
         */
        if(marker.getTag()!=null && marker.getTag()instanceof MyMarkerTag){
            MyMarkerTag myMarkerTag = (MyMarkerTag)marker.getTag();
            itemMarkerInfoBinding.textName.setText(myMarkerTag.getName());
            itemMarkerInfoBinding.rankWifi.setRank(myMarkerTag.getWifi());
            itemMarkerInfoBinding.rankSeat.setRank(myMarkerTag.getSeat());
            itemMarkerInfoBinding.rankMoney.setRank(myMarkerTag.getCheap());
            Log.d("myMarkerTag",myMarkerTag.getName()+myMarkerTag.getWifi()+myMarkerTag.getSeat()+myMarkerTag.getCheap());
        }
//        itemMarkerInfoBinding.executePendingBindings();
        return itemMarkerInfoBinding.getRoot();
    }
}
