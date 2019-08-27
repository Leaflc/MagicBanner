package com.leaflc.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.leaflc.banner.adapter.PageAdapter
import com.leaflc.banner.listener.OnItemClickListener
import com.leaflc.banner.snap_helper.AutoPlayRecyclerView
import com.leaflc.banner.snap_helper.AutoPlaySnapHelper
import com.leaflc.banner.snap_helper.ScaleLayoutManager
import com.leaflc.banner.snap_helper.ViewPagerLayoutManager
import com.leaflc.magicbanner.holder.CBViewHolderCreator


/**
 * 轮播图
 *
 * @author leaflc
 * @date 2019-08-25
 */
class Banner<T> : RelativeLayout {

    var mDatas: List<T>? = null
    /**
     * 存储指示器图像资源
     */
    var pageIndicatorId: IntArray? = null
    var mPointViews = ArrayList<ImageView>()
    var pageAdapter: PageAdapter<T>? = null
    var onPageChangeListener: ViewPagerLayoutManager.OnPageChangeListener? = null



    private var firstItem = 0

    private var turnDirection = AutoPlaySnapHelper.RIGHT


    private var timeInterval = 100


    lateinit var recyclerView: AutoPlayRecyclerView
    lateinit var loPageTurningPoint: ViewGroup

    enum class PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.Banner)
        timeInterval =
            typeArray.getInteger(R.styleable.Banner_timeInterval, 100)
        firstItem = typeArray.getInt(R.styleable.Banner_firstItem, 0)
        turnDirection = typeArray.getInt(R.styleable.Banner_direction, AutoPlaySnapHelper.RIGHT)
        typeArray.recycle()
        init(context)
    }


    private fun init(context: Context) {
        val hView = LayoutInflater.from(context).inflate(
            R.layout.include_viewpager, this, true
        )
        recyclerView = hView.findViewById(R.id.cbLoopViewPager)
        loPageTurningPoint = hView.findViewById(R.id.loPageTurningPoint)
        recyclerView.timeInterval = timeInterval
        recyclerView.direction = turnDirection
    }


    fun setLayoutManager(layoutManager: ViewPagerLayoutManager): Banner<T> {
        recyclerView.layoutManager = layoutManager
        return this
    }

    fun setPages(holderCreator: CBViewHolderCreator<T>, datas: MutableList<T>): Banner<*> {
        this.mDatas = datas
        pageAdapter = PageAdapter<T>(holderCreator, mDatas!!)
        recyclerView.adapter = pageAdapter

        if (pageIndicatorId != null) {
            setPageIndicator(pageIndicatorId!!)
        }
        return this
    }


    /**
     * 通知数据变化
     */
    fun notifyDataSetChanged() {
        recyclerView.adapter?.notifyDataSetChanged()
        if (pageIndicatorId != null)
            setPageIndicator(pageIndicatorId!!)
    }

    /**
     * 设置底部指示器是否可见
     */
    fun setPointViewVisible(visible: Boolean) {
        loPageTurningPoint.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    fun setPageIndicator(pageIndicatorId: IntArray): Banner<T> {
        loPageTurningPoint.removeAllViews()
        mPointViews.clear()
        this.pageIndicatorId = pageIndicatorId
        if (mDatas == null) return this
        for (count in mDatas!!.indices) {
            // 翻页指示的点
            val pointView = ImageView(context)
            pointView.setPadding(5, 0, 5, 0)
            if (firstItem == count) {
                pointView.setImageResource(pageIndicatorId[1])
            } else {
                pointView.setImageResource(pageIndicatorId[0])
            }
            mPointViews.add(pointView)
            loPageTurningPoint.addView(pointView)
        }
        if (recyclerView.layoutManager == null) throw NullPointerException("layoutManager not set")

        (recyclerView.layoutManager as ViewPagerLayoutManager).setOnPageChangeListener(object :
            ViewPagerLayoutManager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                for (index in mPointViews.indices) {
                    mPointViews[position].setImageResource(pageIndicatorId[1])
                    if (position != index) {
                        mPointViews[index].setImageResource(pageIndicatorId[0])
                    }
                }
                onPageChangeListener?.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                onPageChangeListener?.onPageScrollStateChanged(state)
            }

        })
        return this
    }


    /**
     * 设置item点击监听
     */
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener): Banner<T> {
        pageAdapter?.onItemClickListener = onItemClickListener
        return this
    }

    /**
     * 获取当前页对应的position
     */
    fun getCurrentItem(): Int {
        if (recyclerView.layoutManager is ViewPagerLayoutManager) {
            return (recyclerView.layoutManager as ViewPagerLayoutManager).currentPosition
        }
        return 0
    }

    /**
     * 设置当前页对应的position
     */
    fun setCurrentItem(position: Int): Banner<T> {
        recyclerView.scrollToPosition(position)
        return this
    }


    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    fun setPageIndicatorAlign(align: PageIndicatorAlign): Banner<*> {
        val layoutParams = loPageTurningPoint.layoutParams as LayoutParams
        layoutParams.addRule(
            ALIGN_PARENT_LEFT,
            if (align == PageIndicatorAlign.ALIGN_PARENT_LEFT) TRUE else 0
        )
        layoutParams.addRule(
            ALIGN_PARENT_RIGHT,
            if (align == PageIndicatorAlign.ALIGN_PARENT_RIGHT) TRUE else 0
        )
        layoutParams.addRule(
            CENTER_HORIZONTAL,
            if (align == PageIndicatorAlign.CENTER_HORIZONTAL) TRUE else 0
        )
        loPageTurningPoint.layoutParams = layoutParams
        return this
    }

}