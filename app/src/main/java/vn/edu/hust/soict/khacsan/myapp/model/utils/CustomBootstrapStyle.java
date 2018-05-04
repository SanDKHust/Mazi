package vn.edu.hust.soict.khacsan.myapp.model.utils;

import android.content.Context;

import com.beardedhen.androidbootstrap.api.attributes.BootstrapBrand;

import vn.edu.hust.soict.khacsan.myapp.R;

public class CustomBootstrapStyle implements BootstrapBrand {
    public CustomBootstrapStyle() {
    }

    @Override
    public int defaultFill(Context context) {
        return context.getResources().getColor(R.color.colorGrey);
    }

    @Override
    public int defaultEdge(Context context) {
        return 0;
    }

    @Override
    public int defaultTextColor(Context context) {
        return context.getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public int activeFill(Context context) {
        return context.getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public int activeEdge(Context context) {
        return 0;
    }

    @Override
    public int activeTextColor(Context context) {
        return context.getResources().getColor(R.color.colorWhite);
    }

    @Override
    public int disabledFill(Context context) {
        return 0;
    }

    @Override
    public int disabledEdge(Context context) {
        return 0;
    }

    @Override
    public int disabledTextColor(Context context) {
        return 0;
    }
}
