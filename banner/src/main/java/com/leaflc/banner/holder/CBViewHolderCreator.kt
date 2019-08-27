package com.leaflc.magicbanner.holder

import android.view.View

/**
 * CBViewHolderCreator class
 *
 * @author leaflc
 * @date 2019-08-23
 */
interface CBViewHolderCreator<T> {

    fun createHolder(itemView: View): Holder<T>

    fun getLayoutId(): Int

}