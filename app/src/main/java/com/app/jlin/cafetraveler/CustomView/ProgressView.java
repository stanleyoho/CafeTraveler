package com.app.jlin.cafetraveler.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Stanley_NB on 2018/4/21.
 */

public class ProgressView extends ProgressBar {
    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }
}
