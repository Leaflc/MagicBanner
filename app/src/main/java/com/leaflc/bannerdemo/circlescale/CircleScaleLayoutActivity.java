package com.leaflc.bannerdemo.circlescale;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.CircleScaleLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class CircleScaleLayoutActivity extends BaseActivity<CircleScaleLayoutManager, CircleScalePopUpWindow> {

    @NotNull
    @Override
    protected CircleScaleLayoutManager createLayoutManager() {
        return new CircleScaleLayoutManager(this);
    }

    @NotNull
    @Override
    protected CircleScalePopUpWindow createSettingPopUpWindow() {
        return new CircleScalePopUpWindow(this, getViewPagerLayoutManager(), (Banner) findViewById(R.id.banner));
    }
}
