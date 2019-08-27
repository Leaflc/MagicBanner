package com.leaflc.bannerdemo.carousel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.CarouselLayoutManager;
import com.leaflc.banner.snap_helper.CenterSnapHelper;
import com.leaflc.banner.snap_helper.ViewPagerLayoutManager;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.SettingPopUpWindow;
import com.leaflc.bannerdemo.Util;


/**
 * Created by Dajavu on 27/10/2017.
 */

@SuppressLint("InflateParams")
@SuppressWarnings("FieldCanBeLocal")
public class CarouselPopUpWindow extends SettingPopUpWindow
        implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private CarouselLayoutManager carouselLayoutManager;
    private RecyclerView recyclerView;
    private TextView itemSpaceValue;
    private TextView speedValue;
    private Banner banner;
    private TextView minScaleValue;
    private SwitchCompat changeOrientation;
    private SwitchCompat infinite;
    private SwitchCompat reverse;
    private SwitchCompat indicatorVisible;


    @SuppressLint("WrongConstant")
    CarouselPopUpWindow(Context context, CarouselLayoutManager carouselLayoutManager, Banner banner) {
        super(context);
        this.carouselLayoutManager = carouselLayoutManager;
        this.recyclerView = banner.recyclerView;
        this.banner = banner;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_carousel_setting, null);
        setContentView(view);

        SeekBar itemSpace = view.findViewById(R.id.sb_item_space);
        SeekBar speed = view.findViewById(R.id.sb_speed);
        SeekBar minScale = view.findViewById(R.id.sb_min_scale);

        itemSpaceValue = view.findViewById(R.id.item_space);
        speedValue = view.findViewById(R.id.speed_value);
        minScaleValue = view.findViewById(R.id.min_scale_value);

        changeOrientation = view.findViewById(R.id.s_change_orientation);
        infinite = view.findViewById(R.id.s_infinite);
        reverse = view.findViewById(R.id.s_reverse);
        indicatorVisible = view.findViewById(R.id.s_indicator_visible);

        itemSpace.setOnSeekBarChangeListener(this);
        speed.setOnSeekBarChangeListener(this);
        minScale.setOnSeekBarChangeListener(this);

        itemSpace.setProgress(carouselLayoutManager.getItemSpace() / 5);
        speed.setProgress(Math.round(carouselLayoutManager.getMoveSpeed() / 0.05f));
        minScale.setProgress(Math.round(carouselLayoutManager.getMinScale() * 100));

        itemSpaceValue.setText(String.valueOf(carouselLayoutManager.getItemSpace()));
        speedValue.setText(Util.formatFloat(carouselLayoutManager.getMoveSpeed()));
        minScaleValue.setText(Util.formatFloat(carouselLayoutManager.getMinScale()));

        changeOrientation.setChecked(carouselLayoutManager.getOrientation() == ViewPagerLayoutManager.VERTICAL);
        reverse.setChecked(carouselLayoutManager.getReverseLayout());
        infinite.setChecked(carouselLayoutManager.getInfinite());

        changeOrientation.setOnCheckedChangeListener(this);
        reverse.setOnCheckedChangeListener(this);
        infinite.setOnCheckedChangeListener(this);
        indicatorVisible.setOnCheckedChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_item_space:
                int itemSpace = progress * 5;
                carouselLayoutManager.setItemSpace(itemSpace);
                itemSpaceValue.setText(String.valueOf(itemSpace));
                break;
            case R.id.sb_min_scale:
                final float scale = progress / 100f;
                carouselLayoutManager.setMinScale(scale);
                minScaleValue.setText(Util.formatFloat(scale));
                break;
            case R.id.sb_speed:
                final float speed = progress * 0.05f;
                carouselLayoutManager.setMoveSpeed(speed);
                speedValue.setText(Util.formatFloat(speed));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.s_infinite:
                recyclerView.scrollToPosition(0);
                carouselLayoutManager.setInfinite(isChecked);
                break;
            case R.id.s_change_orientation:
                carouselLayoutManager.scrollToPosition(0);
                carouselLayoutManager.setOrientation(isChecked ?
                        ViewPagerLayoutManager.VERTICAL : ViewPagerLayoutManager.HORIZONTAL);
                break;
            case R.id.s_reverse:
                carouselLayoutManager.scrollToPosition(0);
                carouselLayoutManager.setReverseLayout(isChecked);
                break;
            case R.id.s_indicator_visible:
                banner.setPointViewVisible(isChecked);
                break;
            default:
                break;
        }
    }
}
