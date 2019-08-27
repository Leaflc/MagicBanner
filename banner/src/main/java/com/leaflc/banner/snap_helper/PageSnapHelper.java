package com.leaflc.banner.snap_helper;


import androidx.recyclerview.widget.RecyclerView;

/**
 * The implementation will snap the center of the target child view to the center of
 * the attached {@link RecyclerView}. And per Child per fling.
 */

public class PageSnapHelper extends CenterSnapHelper {


    /**
     * 减速因子
     */
    private static final float FLING_SCALE_DOWN_FACTOR = 0.5f;
    /**
     * 最大顺时滑动速度
     */
    private static final int FLING_MAX_VELOCITY = 3000;
    /**
     * 最大顺时滑动速度
     */
    private static boolean mEnableLimitVelocity = true;

    private boolean isFling = false;

    public void setFling(boolean fling) {
        isFling = fling;
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        ViewPagerLayoutManager layoutManager = (ViewPagerLayoutManager) mRecyclerView.getLayoutManager();
        if (layoutManager == null) {
            return false;
        }
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null) {
            return false;
        }

        if (mEnableLimitVelocity) {
            velocityX = solveVelocity(velocityX);
            velocityY = solveVelocity(velocityY);
        }

//        if (isFling) {
//            if (!layoutManager.getInfinite() &&
//                    (layoutManager.mOffset == layoutManager.getMaxOffset()
//                            || layoutManager.mOffset == layoutManager.getMinOffset())) {
//                return false;
//            }
//
//            final int minFlingVelocity = mRecyclerView.getMinFlingVelocity();
//            mGravityScroller.fling(0, 0, velocityX, velocityY,
//                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
//
//            if (layoutManager.mOrientation == ViewPagerLayoutManager.VERTICAL
//                    && Math.abs(velocityY) > minFlingVelocity) {
//                final int currentPosition = layoutManager.getCurrentPositionOffset();
//                final int offsetPosition = mGravityScroller.getFinalY() * layoutManager.getDistanceRatio() > layoutManager.mInterval ? 1 : 0;
//                ScrollHelper.smoothScrollToPosition(mRecyclerView, layoutManager, layoutManager.getReverseLayout() ?
//                        -currentPosition - offsetPosition : currentPosition + offsetPosition);
//                return true;
//            } else if (layoutManager.mOrientation == ViewPagerLayoutManager.HORIZONTAL
//                    && Math.abs(velocityX) > minFlingVelocity) {
//                final int currentPosition = layoutManager.getCurrentPositionOffset();
//                final int offsetPosition = mGravityScroller.getFinalX() * layoutManager.getDistanceRatio() > layoutManager.mInterval ? 1 : 0;
//                ScrollHelper.smoothScrollToPosition(mRecyclerView, layoutManager, layoutManager.getReverseLayout() ?
//                        -currentPosition - offsetPosition : currentPosition + offsetPosition);
//                return true;
//            }
//        } else {
//
//        }
        return super.onFling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, FLING_MAX_VELOCITY);
        } else {
            return Math.max(velocity, -FLING_MAX_VELOCITY);
        }
    }
}
