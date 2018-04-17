package com.app.jlin.cafetraveler.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.jlin.cafetraveler.Interface.CafeListCallBack;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.Tag.MyMarkerTag;
import com.app.jlin.cafetraveler.ViewModel.CafeViewModel;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoRecyclerBinding;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoRecyclerEmptyBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stanley.lin on 2018/4/2.
 */

public class CafeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<RMCafe> cafeList;
    private CafeListCallBack cafeListCallBack;

    public CafeAdapter(Context context, List<RMCafe> cafeList, CafeListCallBack cafeListCallBack){
        this.context = context;
        this.cafeList = cafeList;
        this.cafeListCallBack = cafeListCallBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(cafeList == null || cafeList.size() ==0){
            return new EmptyVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_recycler_empty,parent,false));
        }else {
            return new CafeVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_recycler,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CafeVH){
            CafeVH cafeVH = (CafeVH)holder;
            final RMCafe rmCafe = cafeList.get(position);
            CafeViewModel cafeViewModel = new CafeViewModel();
            cafeViewModel.setName(context.getString(R.string.cafe_name,rmCafe.getName()));
//            cafeViewModel.setLat(context.getString(R.string.cafe_lat,rmCafe.getLatitude()));
//            cafeViewModel.setLon(context.getString(R.string.cafe_lon,rmCafe.getLongitude()));
            cafeViewModel.setMrt(context.getString(R.string.cafe_mrt,rmCafe.getMyMrt()));
            cafeViewModel.setLine(context.getString(R.string.cafe_line,rmCafe.getMrtLine()));
            cafeViewModel.setOpenTime(context.getString(R.string.cafe_open_time,rmCafe.getOpen_time()));
            cafeVH.binding.setViewModel(cafeViewModel);
            if(cafeListCallBack != null){
                cafeVH.binding.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cafeListCallBack.moveToPosition(rmCafe);
                    }
                });
            }
        }else if(holder instanceof EmptyVH){

        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cafeList.size() <= 1 ? 1 : cafeList.size();
    }

    private class CafeVH extends RecyclerView.ViewHolder{

        private ItemCafeInfoRecyclerBinding binding;

        public CafeVH(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private class EmptyVH extends RecyclerView.ViewHolder{

        private ItemCafeInfoRecyclerEmptyBinding binding;
        public EmptyVH(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private List<MarkerOptions> markerOps = new ArrayList<>();

    public void updateData(Context context,List<RMCafe> cafeList, GoogleMap map,Boolean isChecked){
        this.cafeList = cafeList;
        if(!markerOps.isEmpty()){
            map.clear();
            markerOps.clear();
        }
//        if(isChecked) addMarkerOption(context,cafeList,map,markerOps);
//        for(MarkerOptions markerOptions : markerOps){
//            map.addMarker(markerOptions);
//        }
        if(isChecked){
            for(RMCafe rmCafe : cafeList){
                MarkerOptions markerOption = addMarkerOption(context,rmCafe,map);
                Marker marker = map.addMarker(markerOption);

                MyMarkerTag markerTag = new MyMarkerTag();
                markerTag.setName(rmCafe.getName());
                markerTag.setWifi(rmCafe.getWifi());
                markerTag.setCheap(rmCafe.getCheap());
                markerTag.setSeat(rmCafe.getSeat());
                marker.setTag(markerTag);
            }
        }
        notifyDataSetChanged();
    }

//    private void addMarkerOption(Context context, List<RMCafe> cafeList, GoogleMap map, List<MarkerOptions> markerOps) {
//        for(RMCafe rmCafe : cafeList){
//            LatLng place = new LatLng(rmCafe.getLatitude(), rmCafe.getLongitude());
//            String title = rmCafe.getName();
//            BitmapDrawable icon = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ic_mymarker);
//            Bitmap bitmap = icon.getBitmap();
//            Bitmap smallIcon = Bitmap.createScaledBitmap(bitmap,80,160,false);
//
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(place)
//                    .title(title)
//                    .icon(BitmapDescriptorFactory.fromBitmap(smallIcon));
//            markerOps.add(markerOptions);
//        }
//        Log.d("inAddMarkerOptions",Integer.toString(markerOps.size()));
//    }

    private MarkerOptions addMarkerOption(Context context, RMCafe rmCafe, GoogleMap map) {

            LatLng place = new LatLng(rmCafe.getLatitude(), rmCafe.getLongitude());
            String title = rmCafe.getName();
            BitmapDrawable icon = (BitmapDrawable)context.getResources().getDrawable(R.drawable.ic_mymarker);
            Bitmap bitmap = icon.getBitmap();
            Bitmap smallIcon = Bitmap.createScaledBitmap(bitmap,80,160,false);

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(place)
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallIcon));
            markerOps.add(markerOptions);
            return markerOptions;
    }
}
