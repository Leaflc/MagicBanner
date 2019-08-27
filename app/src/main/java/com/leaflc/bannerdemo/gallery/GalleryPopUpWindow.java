package com.leaflc.bannerdemo.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

;import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.leaflc.banner.Banner;
import com.leaflc.banner.snap_helper.CenterSnapHelper;
import com.leaflc.banner.snap_helper.GalleryLayoutManager;
import com.leaflc.banner.snap_helper.ViewPagerLayoutManager;
import com.leaflc.bannerdemo.R;
import com.leaflc.bannerdemo.SettingPopUpWindow;
import com.leaflc.bannerdemo.Util;

/**
 * Created by Dajavu on 27/10/2017.
 */

@SuppressLint("InflateParams")
@SuppressWarnings("FieldCanBeLocal")
public class GalleryPopUpWindow extends SettingPopUpWindow
        implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private GalleryLayoutManager galleryLayoutManager;
    private RecyclerView recyclerView;
    private Banner banner;
    private TextView itemSpaceValue;
    private TextView speedValue;
    private TextView minAlphaValue;
    private TextView maxAlphaValue;
    private TextView angleValue;
    private SwitchCompat centerInFront;
    private SwitchCompat changeOrientation;
    private SwitchCompat infinite;
    private SwitchCompat reverse;
    private SwitchCompat flipRotate;
    private SwitchCompat rotateFromEdge;
    private SwitchCompat indicatorVisible;

    @SuppressLint("WrongConstant")
    GalleryPopUpWindow(Context context, GalleryLayoutManager galleryLayoutManager, Banner banner) {
        super(context);
        this.galleryLayoutManager = galleryLayoutManager;
        this.recyclerView = banner.recyclerView;
        this.banner = banner;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_gallery_setting, null);
        setContentView(view);


        SeekBar itemSpace = view.findViewById(R.id.sb_item_space);
        SeekBar speed = view.findViewById(R.id.sb_speed);
        SeekBar minAlpha = view.findViewById(R.id.sb_min_alpha);
        SeekBar maxAlpha = view.findViewById(R.id.sb_max_alpha);
        SeekBar angle = view.findViewById(R.id.sb_interval);

        itemSpaceValue = view.findViewById(R.id.item_space);
        speedValue = view.findViewById(R.id.speed_value);
        minAlphaValue = view.findViewById(R.id.min_alpha_value);
        maxAlphaValue = view.findViewById(R.id.max_alpha_value);
        angleValue = view.findViewById(R.id.angle_value);

        centerInFront = view.findViewById(R.id.s_center_in_front);
        changeOrientation = view.findViewById(R.id.s_change_orientation);
        infinite = view.findViewById(R.id.s_infinite);
        reverse = view.findViewById(R.id.s_reverse);
        flipRotate = view.findViewById(R.id.s_flip);
        rotateFromEdge = view.findViewById(R.id.s_rotate_from_edge);
        indicatorVisible = view.findViewById(R.id.s_indicator_visible);

        itemSpace.setOnSeekBarChangeListener(this);
        speed.setOnSeekBarChangeListener(this);
        minAlpha.setOnSeekBarChangeListener(this);
        maxAlpha.setOnSeekBarChangeListener(this);
        angle.setOnSeekBarChangeListener(this);

        itemSpace.setProgress(galleryLayoutManager.getItemSpace() / 8 + 50);
        speed.setProgress(Math.round(galleryLayoutManager.getMoveSpeed() / 0.05f));
        maxAlpha.setProgress(Math.round(galleryLayoutManager.getMaxAlpha() * 100));
        minAlpha.setProgress(Math.round(galleryLayoutManager.getMinAlpha() * 100));
        angle.setProgress(Math.round(galleryLayoutManager.getAngle() / 0.9f));

        itemSpaceValue.setText(String.valueOf(galleryLayoutManager.getItemSpace()));
        speedValue.setText(Util.formatFloat(galleryLayoutManager.getMoveSpeed()));
        minAlphaValue.setText(Util.formatFloat(galleryLayoutManager.getMinAlpha()));
        maxAlphaValue.setText(Util.formatFloat(galleryLayoutManager.getMaxAlpha()));
        angleValue.setText(Util.formatFloat(galleryLayoutManager.getAngle()));

        centerInFront.setChecked(galleryLayoutManager.getEnableBringCenterToFront());
        changeOrientation.setChecked(galleryLayoutManager.getOrientation() == ViewPagerLayoutManager.VERTICAL);
        reverse.setChecked(galleryLayoutManager.getReverseLayout());
        flipRotate.setChecked(galleryLayoutManager.getFlipRotate());
        rotateFromEdge.setChecked(galleryLayoutManager.getRotateFromEdge());
        infinite.setChecked(galleryLayoutManager.getInfinite());

        centerInFront.setOnCheckedChangeListener(this);
        changeOrientation.setOnCheckedChangeListener(this);
        reverse.setOnCheckedChangeListener(this);
        flipRotate.setOnCheckedChangeListener(this);
        rotateFromEdge.setOnCheckedChangeListener(this);
        infinite.setOnCheckedChangeListener(this);
        indicatorVisible.setOnCheckedChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_item_space:
                int itemSpace = (progress - 50) * 8;
                galleryLayoutManager.setItemSpace(itemSpace);
                itemSpaceValue.setText(String.valueOf(itemSpace));
                break;
            case R.id.sb_speed:
                final float speed = progress * 0.05f;
                galleryLayoutManager.setMoveSpeed(speed);
                speedValue.setText(Util.formatFloat(speed));
                break;
            case R.id.sb_interval:
                final int angle = Math.round(progress * 0.9f);
                galleryLayoutManager.setAngle(angle);
                angleValue.setText(String.valueOf(angle));
                break;
            case R.id.sb_max_alpha:
                final float maxAlpha = progress / 100f;
                galleryLayoutManager.setMaxAlpha(maxAlpha);
                maxAlphaValue.setText(Util.formatFloat(maxAlpha));
                break;
            case R.id.sb_min_alpha:
                final float minAlpha = progress / 100f;
                galleryLayoutManager.setMinAlpha(minAlpha);
                minAlphaValue.setText(Util.formatFloat(minAlpha));
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
                galleryLayoutManager.setInfinite(isChecked);
                break;
            case R.id.s_change_orientation:
                galleryLayoutManager.scrollToPosition(0);
                galleryLayoutManager.setOrientation(isChecked ?
                        ViewPagerLayoutManager.VERTICAL : ViewPagerLayoutManager.HORIZONTAL);
                break;
            case R.id.s_reverse:
                galleryLayoutManager.scrollToPosition(0);
                galleryLayoutManager.setReverseLayout(isChecked);
                break;
            case R.id.s_flip:
                galleryLayoutManager.setFlipRotate(isChecked);
            case R.id.s_center_in_front:
                galleryLayoutManager.setEnableBringCenterToFront(isChecked);
                break;
            case R.id.s_rotate_from_edge:
                galleryLayoutManager.setRotateFromEdge(isChecked);
                break;
            case R.id.s_indicator_visible:
                banner.setPointViewVisible(isChecked);
                break;
            default:
                break;
        }
    }
}
