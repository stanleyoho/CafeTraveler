package com.app.jlin.cafetraveler.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.jlin.cafetraveler.Constants.Constants;
import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.databinding.ItemSpinnerBinding;

/**
 * Created by Stanley_NB on 2018/4/11.
 */

public class MySpinnerAdapter extends BaseAdapter {
    private Context context;
    private int[] lineArray;

    public MySpinnerAdapter(Context context , int[] lineArray) {
        this.lineArray = lineArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lineArray.length;
    }

    @Override
    public Object getItem(int position) {
        return lineArray[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSpinnerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_spinner,parent,false);
        switch (lineArray[position]){
            case Constants.LINE_RED:
                binding.viewLine.setBackgroundColor(context.getResources().getColor(R.color.line_red));
                break;
            case Constants.LINE_BLUE:
                binding.viewLine.setBackgroundColor(context.getResources().getColor(R.color.line_blue));
                break;
            case Constants.LINE_GREEN:
                binding.viewLine.setBackgroundColor(context.getResources().getColor(R.color.line_green));
                break;
            case Constants.LINE_ORANGE:
                binding.viewLine.setBackgroundColor(context.getResources().getColor(R.color.line_orange));
                break;
            case Constants.LINE_BROWN:
                binding.viewLine.setBackgroundColor(context.getResources().getColor(R.color.line_brown));
                break;
        }
        return binding.getRoot();
    }
}
