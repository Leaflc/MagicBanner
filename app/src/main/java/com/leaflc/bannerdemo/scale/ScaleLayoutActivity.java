package com.leaflc.bannerdemo.scale;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.ScaleLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class ScaleLayoutActivity extends BaseActivity<ScaleLayoutManager, ScalePopUpWindow> {

    @NotNull
    @Override
    protected ScaleLayoutManager createLayoutManager() {
        return new ScaleLayoutManager(this, Util.Dp2px(this, 10));
    }

    @NotNull
    @Override
    protected ScalePopUpWindow createSettingPopUpWindow() {
        return new ScalePopUpWindow(this, getViewPagerLayoutManager(), ((Banner) findViewById(R.id.banner)));
    }
}
