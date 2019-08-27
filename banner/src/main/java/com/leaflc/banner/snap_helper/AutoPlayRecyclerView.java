package com.leaflc.banner.snap_helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


/**
 * An implement of {@link RecyclerView} which support auto play.
 */

public class AutoPlayRecyclerView extends RecyclerView {
    private AutoPlaySnapHelper autoPlaySnapHelper;

    private int timeInterval = 1000;
    private int direction = AutoPlaySnapHelper.RIGHT;


    public AutoPlayRecyclerView(Context context) {
        this(context, null);
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlaySnapHelper getAutoPlaySnapHelper() {
        return autoPlaySnapHelper;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getDirection() {
        return direction;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public AutoPlayRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        autoPlaySnapHelper = new AutoPlaySnapHelper(timeInterval, direction);
    }


    /**
     * 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean result = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            // 停止翻页
            case MotionEvent.ACTION_DOWN:
                if (autoPlaySnapHelper != null) {
                    autoPlaySnapHelper.pause();
                }
                break;
            // 开始翻页
            case MotionEvent.ACTION_UP:
                if (autoPlaySnapHelper != null) {
                    autoPlaySnapHelper.start();
                }
            default:
                break;
        }
        return result;
    }



    public void start() {
        autoPlaySnapHelper.start();
    }

    public void pause() {
        autoPlaySnapHelper.pause();
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        autoPlaySnapHelper.attachToRecyclerView(this);

    }
}
