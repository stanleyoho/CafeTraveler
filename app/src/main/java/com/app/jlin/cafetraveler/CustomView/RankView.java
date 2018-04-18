package com.app.jlin.cafetraveler.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.jlin.cafetraveler.R;
import com.app.jlin.cafetraveler.databinding.LayoutRankBinding;

/**
 * Created by Stanley_NB on 2018/4/17.
 */

public class RankView extends LinearLayout {
//    private int rank;

    public RankView(Context context) {
        this(context, null);
    }

    public RankView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RankView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_rank, this);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.rankView, defStyleAttr, 0);

        int rank = typedArray.getInt(R.styleable.rankView_rank, 5);
        int color = typedArray.getColor(R.styleable.rankView_background, 0);
        String title = typedArray.getString(R.styleable.rankView_title);
        TextView textView = findViewById(R.id.text_title);
        textView.setText(context.getString(R.string.rank_view_title, title));
        setBackgroundColor(color);
        setRank(rank);
    }

    public void setRank(int rank) {
        int nowRank = rank;
        if (rank > 5 || rank < 0) {
            nowRank = 5;
        }

        for (int i = 5; i > nowRank; i--) {
            ImageView ivRank = (ImageView) ((LinearLayout) (findViewById(R.id.layout_linear))).getChildAt(i);
            ivRank.setVisibility(View.INVISIBLE);
        }
    }
}
