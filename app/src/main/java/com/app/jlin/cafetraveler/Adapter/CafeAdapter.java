package com.app.jlin.cafetraveler.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.jlin.cafetraveler.Interface.CafeListCallBack;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.RealmModel.RMCafe;
import com.app.jlin.cafetraveler.ViewModel.CafeViewModel;
import com.app.jlin.cafetraveler.databinding.ItemCafeInfoRecyclerBinding;

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
        return new CafeVH(LayoutInflater.from(context).inflate(R.layout.item_cafe_info_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CafeVH){
            CafeVH cafeVH = (CafeVH)holder;
            final RMCafe rmCafe = cafeList.get(position);
            CafeViewModel cafeViewModel = new CafeViewModel();
            cafeViewModel.setName(context.getString(R.string.cafe_name,rmCafe.getName()));
            cafeViewModel.setLat(context.getString(R.string.cafe_lat,rmCafe.getLatitude()));
            cafeViewModel.setLon(context.getString(R.string.cafe_lon,rmCafe.getLongitude()));
            cafeViewModel.setMrt(context.getString(R.string.cafe_mrt,rmCafe.getMyMrt()));
            cafeVH.binding.setViewModel(cafeViewModel);
            if(cafeListCallBack != null){
                cafeVH.binding.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cafeListCallBack.moveToPosition(rmCafe);
                    }
                });
            }
        }
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
}
