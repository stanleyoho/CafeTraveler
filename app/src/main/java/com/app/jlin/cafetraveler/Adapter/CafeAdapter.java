package com.app.jlin.cafetraveler.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.jlin.cafetraveler.Interface.CafeListCallBack;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.Utils.MyMarkerUtils;
import com.app.jlin.cafetraveler.ViewModel.CafeViewModel;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoBottomBinding;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoRecyclerBinding;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoRecyclerEmptyBinding;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoSummaryBinding;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

/**
 * Created by stanley.lin on 2018/4/2.
 */

public class CafeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<RMCafe> cafeList;
    private CafeListCallBack cafeListCallBack;
    private GoogleMap map;

    private enum myEnum{
        TYPE_SUMMARY,
        TYPE_BOTTOM,
        TYPE_DATA,
        TYPE_EMPTY
    }
    public CafeAdapter(Context context, List<RMCafe> cafeList, CafeListCallBack cafeListCallBack,GoogleMap map) {
        this.context = context;
        this.cafeList = cafeList;
        this.cafeListCallBack = cafeListCallBack;
        this.map = map;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == myEnum.TYPE_EMPTY.ordinal()) {
            return new EmptyVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_recycler_empty, parent, false));
        }else if( viewType == myEnum.TYPE_SUMMARY.ordinal()){
            return new SummaryVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_summary,parent,false));
        } else if( viewType == myEnum.TYPE_DATA.ordinal()){
            return new CafeVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_recycler, parent, false));
        }else if(viewType == myEnum.TYPE_BOTTOM.ordinal()){
            return new BottomVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_bottom, parent, false));
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SummaryVH){
            SummaryVH summaryVH = (SummaryVH)holder;
            summaryVH.binding.textSummary.setText(context.getString(R.string.recycler_summary,String.valueOf(cafeList.size())));
        }else if (holder instanceof CafeVH) {
            CafeVH cafeVH = (CafeVH) holder;
            final RMCafe rmCafe = cafeList.get(position -1);
            CafeViewModel cafeViewModel = new CafeViewModel();
            cafeViewModel.setName(context.getString(R.string.cafe_name,rmCafe.getName()));
            cafeViewModel.setMrt(context.getString(R.string.cafe_mrt,rmCafe.getNearestStationName()));
            cafeViewModel.setLine(context.getString(R.string.cafe_line,rmCafe.getNearestAnnotation()));
            cafeViewModel.setOpenTime(context.getString(R.string.cafe_open_time,rmCafe.getOpen_time()));
            cafeVH.binding.setViewModel(cafeViewModel);
            if (cafeListCallBack != null) {
                cafeVH.binding.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cafeListCallBack.moveToPosition(rmCafe);
                        MyMarkerUtils.checkMarker(context,map,rmCafe);
                    }
                });
            }
        } else if (holder instanceof EmptyVH) {

        } else if (holder instanceof BottomVH){

        }
        holder.setIsRecyclable(false);
    }



    @Override
    public int getItemCount() {
        return cafeList.size() < 1 ? 1 : cafeList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return myEnum.TYPE_SUMMARY.ordinal();
        }else if(position == getItemCount() -1){
            return myEnum.TYPE_BOTTOM.ordinal();
        }else if( cafeList.size() <= 0 || getItemCount() == 1){
            return myEnum.TYPE_EMPTY.ordinal();
        }else {
            return myEnum.TYPE_DATA.ordinal();
        }
    }

    private class CafeVH extends RecyclerView.ViewHolder {

        private ItemCafeInfoRecyclerBinding binding;

        public CafeVH(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private class EmptyVH extends RecyclerView.ViewHolder {

        private ItemCafeInfoRecyclerEmptyBinding binding;

        public EmptyVH(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private class BottomVH extends RecyclerView.ViewHolder{
        private ItemCafeInfoBottomBinding binding;
        public BottomVH(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.textBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cafeListCallBack.moveToTop();
                }
            });
        }
    }

    private class SummaryVH extends RecyclerView.ViewHolder {

        private ItemCafeInfoSummaryBinding binding;

        public SummaryVH(View itemView){
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.btnBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cafeListCallBack.moveToBottom();
                }
            });
        }
    }

    public void updateData(Context context, List<RMCafe> cafeList, GoogleMap map, Boolean isChecked) {
        this.cafeList = cafeList;
        map.clear();
        MyMarkerUtils.cleanMarkers();
        if (isChecked) {
            for (RMCafe rmCafe : cafeList) {
                Bitmap icon = MyMarkerUtils.smallIcon(context);
                MyMarkerUtils.addMarker(map,rmCafe,icon);
            }
        }
        notifyDataSetChanged();
    }

}
