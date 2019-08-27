package com.leaflc.bannerdemo.carousel;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.CarouselLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class CarouselLayoutActivity extends BaseActivity<CarouselLayoutManager, CarouselPopUpWindow> {

    @NotNull
    @Override
    protected CarouselLayoutManager createLayoutManager() {
        return new CarouselLayoutManager(this, Util.Dp2px(this, 100));
    }

    @NotNull
    @Override
    protected CarouselPopUpWindow createSettingPopUpWindow() {
        return new CarouselPopUpWindow(this, getViewPagerLayoutManager(), (Banner) findViewById(R.id.banner));
    }
}
