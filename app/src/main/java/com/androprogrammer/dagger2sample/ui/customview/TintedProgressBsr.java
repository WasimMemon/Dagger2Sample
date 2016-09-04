package com.androprogrammer.dagger2sample.ui.customview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;

import com.androprogrammer.dagger2sample.R;

/**
 * Created by wasim on 8/17/2016.
 */

public class TintedProgressBsr extends ContentLoadingProgressBar {


    public TintedProgressBsr(Context context) {
        super(context);
        init(context);
    }

    public TintedProgressBsr(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {

        if (getIndeterminateDrawable() != null) {

            getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }

        if (getProgressDrawable() != null){

            getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
