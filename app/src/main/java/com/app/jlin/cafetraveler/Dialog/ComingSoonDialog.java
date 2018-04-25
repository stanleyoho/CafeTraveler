package com.app.jlin.cafetraveler.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.databinding.DialogComingsoonBinding;

/**
 * Created by jimmy on 2018/4/25.
 */

public class ComingSoonDialog implements View.OnClickListener {
    private DialogComingsoonBinding binding;
    private Context mContext;
    private Dialog mDialog;
    private String titleStr;

    public ComingSoonDialog(Context context){
        this.mContext = context;
    }

    public ComingSoonDialog show(){
        mDialog = new Dialog(mContext, R.style.CoomingSoonDialog);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_comingsoon, null, false);
        mDialog.setContentView(binding.getRoot());
        mDialog.setCanceledOnTouchOutside(false);

        binding.comingSoonTitle.setText(titleStr);
        binding.comingSoonOK.setOnClickListener(this);
        mDialog.show();

        return this;
    }

    public ComingSoonDialog setTitle (String title) throws Exception {
        if(title != null)this.titleStr = title;
        else{ throw new Exception("please set title string");}
        return this;
    }

    @Override
    public void onClick(View view) {
        mDialog.dismiss();
    }
}
