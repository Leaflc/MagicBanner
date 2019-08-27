package com.leaflc.bannerdemo.gallery;


import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.GalleryLayoutManager;
import com.leaflc.bannerdemo.BaseActivity;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.Util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class GalleryLayoutActivity extends BaseActivity<GalleryLayoutManager, GalleryPopUpWindow> {

    @NotNull
    @Override
    protected GalleryLayoutManager createLayoutManager() {
        return new GalleryLayoutManager(this, Util.Dp2px(this, 10));
    }

    @NotNull
    @Override
    protected GalleryPopUpWindow createSettingPopUpWindow() {
        return new GalleryPopUpWindow(this, getViewPagerLayoutManager(), ((Banner) findViewById(R.id.banner)));
    }
}
