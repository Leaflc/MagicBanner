package com.leaflc.bannerdemo.circle;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.CircleLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 25/10/2017.
 */
public class CircleLayoutActivity extends BaseActivity<CircleLayoutManager, CirclePopUpWindow> {

    @NotNull
    @Override
    protected CircleLayoutManager createLayoutManager() {
        return new CircleLayoutManager(this);
    }

    @NotNull
    @Override
    protected CirclePopUpWindow createSettingPopUpWindow() {
        return new CirclePopUpWindow(this, getViewPagerLayoutManager(), (Banner) findViewById(R.id.banner));
    }
}
