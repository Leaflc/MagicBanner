package com.leaflc.banner.adapter

import android.view.View
import android.view.ViewGroup
import com.leaflc.banner.utils.ScreenUtil

/**
 * adapter中调用onCreateViewHolder, onBindViewHolder
 *
 * @author leaflc
 * @date 2019-08-23
 */
class CBPageAdapterHelper {
    companion object {
        const val sPagePadding = 0
        const val sShowLeftCardWidth = 0
    }

    fun onCreateViewHolder(parent: ViewGroup, itemView: View) {
        val lp = itemView.layoutParams
        lp.width =
            parent.width - ScreenUtil.dip2px(itemView.context, (2 * (sPagePadding + sShowLeftCardWidth)).toFloat())
        itemView.layoutParams = lp
    }

    fun onBindViewHolder(itemView: View, position: Int, itemCount: Int) {
        val padding = ScreenUtil.dip2px(itemView.context, sPagePadding.toFloat())
        itemView.setPadding(padding, 0, padding, 0)
        val leftMarin =
            if (position == 0) padding + ScreenUtil.dip2px(itemView.context, sShowLeftCardWidth.toFloat()) else 0
        val rightMarin =
            if (position == itemCount - 1) padding + ScreenUtil.dip2px(
                itemView.context,
                sShowLeftCardWidth.toFloat()
            ) else 0
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0)
    }

    private fun setViewMargin(view: View, left: Int, top: Int, right: Int, bottom: Int) {
        val lp = view.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
        }
    }
}