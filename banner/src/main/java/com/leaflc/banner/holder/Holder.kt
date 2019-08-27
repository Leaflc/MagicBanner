package com.leaflc.magicbanner.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * @author leaflc
 * @date 2019-08-23
 *
 * @param <T> 任何你指定的对象
 */
abstract class Holder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        initView(itemView)
    }

    abstract fun initView(itemView: View)

    abstract fun updateUI(data: T)
}