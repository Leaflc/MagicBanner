package com.leaflc.bannerdemo.rotate;

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
import com.leaflc.banner.snap_helper.CenterSnapHelper;
import com.leaflc.banner.snap_helper.RotateLayoutManager;
import com.leaflc.banner.snap_helper.ViewPagerLayoutManager;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.SettingPopUpWindow;
import com.leaflc.bannerdemo.Util;

/**
 * Created by Dajavu on 27/10/2017.
 */

@SuppressLint("InflateParams")
@SuppressWarnings("FieldCanBeLocal")
public class RotatePopUpWindow extends SettingPopUpWindow
        implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private RotateLayoutManager rotateLayoutManager;
    private RecyclerView recyclerView;
    private TextView itemSpaceValue;
    private TextView speedValue;
    private TextView angleValue;
    private SwitchCompat changeOrientation;
    private SwitchCompat infinite;
    private SwitchCompat reverseRotate;
    private SwitchCompat reverse;
    private SwitchCompat indicatorVisible;
    private Banner banner;


    @SuppressLint("WrongConstant")
    RotatePopUpWindow(Context context, RotateLayoutManager rotateLayoutManager, Banner banner) {
        super(context);
        this.rotateLayoutManager = rotateLayoutManager;
        this.recyclerView = banner.recyclerView;
        this.banner = banner;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rotate_setting, null);
        setContentView(view);


        SeekBar itemSpace = view.findViewById(R.id.sb_item_space);
        SeekBar speed = view.findViewById(R.id.sb_speed);
        SeekBar angle = view.findViewById(R.id.sb_angle);

        itemSpaceValue = view.findViewById(R.id.item_space);
        speedValue = view.findViewById(R.id.speed_value);
        angleValue = view.findViewById(R.id.angle_value);

        reverseRotate = view.findViewById(R.id.s_reverse_rotate);
        changeOrientation = view.findViewById(R.id.s_change_orientation);
        infinite = view.findViewById(R.id.s_infinite);
        reverse = view.findViewById(R.id.s_reverse);
        indicatorVisible = view.findViewById(R.id.s_indicator_visible);

        itemSpace.setOnSeekBarChangeListener(this);
        speed.setOnSeekBarChangeListener(this);
        angle.setOnSeekBarChangeListener(this);

        itemSpace.setProgress(rotateLayoutManager.getItemSpace() / 2);
        speed.setProgress(Math.round(rotateLayoutManager.getMoveSpeed() / 0.05f));
        angle.setProgress(Math.round(rotateLayoutManager.getAngle() / 360 * 100));

        itemSpaceValue.setText(String.valueOf(rotateLayoutManager.getItemSpace()));
        speedValue.setText(Util.formatFloat(rotateLayoutManager.getMoveSpeed()));
        angleValue.setText(Util.formatFloat(rotateLayoutManager.getAngle()));

        reverseRotate.setChecked(rotateLayoutManager.getEnableBringCenterToFront());
        changeOrientation.setChecked(rotateLayoutManager.getOrientation() == ViewPagerLayoutManager.VERTICAL);
        reverse.setChecked(rotateLayoutManager.getReverseLayout());
        infinite.setChecked(rotateLayoutManager.getInfinite());

        reverseRotate.setOnCheckedChangeListener(this);
        changeOrientation.setOnCheckedChangeListener(this);
        reverse.setOnCheckedChangeListener(this);
        infinite.setOnCheckedChangeListener(this);
        indicatorVisible.setOnCheckedChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_item_space:
                int itemSpace = progress * 2;
                rotateLayoutManager.setItemSpace(itemSpace);
                itemSpaceValue.setText(String.valueOf(itemSpace));
                break;
            case R.id.sb_angle:
                final float angle = progress / 100f * 360;
                rotateLayoutManager.setAngle(angle);
                angleValue.setText(Util.formatFloat(angle));
                break;
            case R.id.sb_speed:
                final float speed = progress * 0.05f;
                rotateLayoutManager.setMoveSpeed(speed);
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
                rotateLayoutManager.setInfinite(isChecked);
                break;
            case R.id.s_change_orientation:
                rotateLayoutManager.scrollToPosition(0);
                rotateLayoutManager.setOrientation(isChecked ?
                        ViewPagerLayoutManager.VERTICAL : ViewPagerLayoutManager.HORIZONTAL);
                break;
            case R.id.s_reverse_rotate:
                rotateLayoutManager.setReverseRotate(isChecked);
                break;
            case R.id.s_reverse:
                rotateLayoutManager.scrollToPosition(0);
                rotateLayoutManager.setReverseLayout(isChecked);
                break;
            case R.id.s_indicator_visible:
                banner.setPointViewVisible(isChecked);
                break;
            default:
                break;
        }
    }
}
