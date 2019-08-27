package com.leaflc.bannerdemo.rotate;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.RotateLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class RotateLayoutActivity extends BaseActivity<RotateLayoutManager, RotatePopUpWindow> {

    @NotNull
    @Override
    protected RotateLayoutManager createLayoutManager() {
        return new RotateLayoutManager(this, Util.Dp2px(this, 10));
    }

    @NotNull
    @Override
    protected RotatePopUpWindow createSettingPopUpWindow() {
        return new RotatePopUpWindow(this, getViewPagerLayoutManager(), ((Banner) findViewById(R.id.banner)));
    }
}
