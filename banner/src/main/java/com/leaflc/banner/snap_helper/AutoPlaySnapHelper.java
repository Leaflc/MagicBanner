package com.leaflc.banner.snap_helper;

import android.os.Handler;
import android.os.Looper;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;


/**
 * Used by {@link AutoPlayRecyclerView} to implement auto play effect
 */

public class AutoPlaySnapHelper extends PageSnapHelper {

    public final static int LEFT = 1;
    public final static int RIGHT = 2;

    private Handler handler;
    private int timeInterval;
    private Runnable autoPlayRunnable;
    private boolean runnableAdded;
    private int direction;

    AutoPlaySnapHelper(int timeInterval, int direction) {
        checkDirection(direction);
        handler = new Handler(Looper.getMainLooper());
        this.timeInterval = timeInterval;
        this.direction = direction;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        if (mRecyclerView == recyclerView) {
            return; // nothing to do
        }
        if (mRecyclerView != null) {
            destroyCallbacks();
        }
        mRecyclerView = recyclerView;
        if (mRecyclerView != null) {
            final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (!(layoutManager instanceof ViewPagerLayoutManager)) return;

            setupCallbacks();
            mGravityScroller = new Scroller(mRecyclerView.getContext(),
                    new DecelerateInterpolator());

            snapToCenterView((ViewPagerLayoutManager) layoutManager,
                    ((ViewPagerLayoutManager) layoutManager).onPageChangeListener);

            ((ViewPagerLayoutManager) layoutManager).setInfinite(true);


            autoPlayRunnable = new AutoPlayRunnable(this);
            handler.postDelayed(autoPlayRunnable, timeInterval);
            runnableAdded = true;
        }

    }


    static class AutoPlayRunnable implements Runnable {

        private WeakReference<AutoPlaySnapHelper> reference;

        public AutoPlayRunnable(AutoPlaySnapHelper pageSnapHelper) {
            this.reference = new WeakReference<AutoPlaySnapHelper>(pageSnapHelper);
        }

        @Override
        public void run() {
            final AutoPlaySnapHelper autoPlaySnapHelper = reference.get();
            if (autoPlaySnapHelper == null) return;
            final RecyclerView mRecyclerView = autoPlaySnapHelper.mRecyclerView;
            final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            assert layoutManager != null;
            final int currentPosition =
                    ((ViewPagerLayoutManager) layoutManager).getCurrentPositionOffset() *
                            (((ViewPagerLayoutManager) layoutManager).getReverseLayout() ? -1 : 1);
            ScrollHelper.smoothScrollToPosition(mRecyclerView,
                    (ViewPagerLayoutManager) layoutManager, autoPlaySnapHelper.direction == RIGHT ? currentPosition + 1 : currentPosition - 1);
            autoPlaySnapHelper.handler.postDelayed(autoPlaySnapHelper.autoPlayRunnable, autoPlaySnapHelper.timeInterval);
        }
    }

    @Override
    void destroyCallbacks() {
        super.destroyCallbacks();
        if (runnableAdded) {
            handler.removeCallbacks(autoPlayRunnable);
            runnableAdded = false;
        }
    }

    void pause() {
        if (runnableAdded) {
            handler.removeCallbacks(autoPlayRunnable);
            runnableAdded = false;
        }
    }

    void start() {
        if (!runnableAdded) {
            handler.postDelayed(autoPlayRunnable, timeInterval);
            runnableAdded = true;
        }
    }

    void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    void setDirection(int direction) {
        checkDirection(direction);
        this.direction = direction;
    }

    private void checkDirection(int direction) {
        if (direction != LEFT && direction != RIGHT)
            throw new IllegalArgumentException("direction should be one of left or right");
    }


}
