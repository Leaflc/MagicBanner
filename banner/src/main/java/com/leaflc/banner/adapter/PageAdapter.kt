package com.leaflc.banner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leaflc.banner.listener.OnItemClickListener
import com.leaflc.magicbanner.holder.CBViewHolderCreator
import com.leaflc.magicbanner.holder.Holder

/**
 * CBPageAdapter class
 *
 * @author leaflc
 * @date 2019-08-23
 */
class PageAdapter<T> : RecyclerView.Adapter<Holder<T>> {

    protected val mData: List<T>
    val creator: CBViewHolderCreator<T>

    val helper: CBPageAdapterHelper

    var onItemClickListener: OnItemClickListener? = null

    constructor(creator: CBViewHolderCreator<T>, mData: List<T>) : super() {
        this.mData = mData
        this.creator = creator
        helper = CBPageAdapterHelper()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<T> {
        val layoutId = creator.getLayoutId()
        val itemView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return creator.createHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: Holder<T>, position: Int) {
        holder.updateUI(mData[position])
        holder.itemView.setOnClickListener(OnPageClickListener(position))
    }

    inner class OnPageClickListener(private val position: Int) : View.OnClickListener {

        override fun onClick(view: View?) {
            onItemClickListener?.onItemClick(position)
        }
    }


    fun getRealItemCount(): Int {
        return mData.size
    }


}