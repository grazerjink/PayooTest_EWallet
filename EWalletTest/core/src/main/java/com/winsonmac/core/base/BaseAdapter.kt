package com.winsonmac.core.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.winsonmac.core.listener.OnItemClickListener

/* =========================================================================================== */
/*  Base adapter for maintain and reuse codes                                                  */
/* =========================================================================================== */

abstract class BaseAdapter<T>(private var mContext: Context, private var mData: MutableList<T>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var inflater: LayoutInflater = LayoutInflater.from(mContext)
    protected var mItemClickListener: OnItemClickListener<T>? = null


    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun getItemCount(): Int {
        return mData.size
    }

    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    /**
     * Enable on item click event
     */
    fun setOnItemClickListener(mItemClickListener: OnItemClickListener<T>) {
        this.mItemClickListener = mItemClickListener
    }

    /**
     * Check list data is empty or not
     */
    fun isEmpty(): Boolean {
        return mData.isEmpty()
    }

    /**
     * Add one more item and update ui for the new added data
     */
    fun addItem(item: T) {
        mData.add(item)
        notifyItemInserted(mData.size - 1)
    }

    /**
     * Add a list item and update ui for the new added list data
     */
    fun addAllItems(items: List<T>?) {

        if (items != null || items!!.size > 0) {
            // Notify to change usage
            val beforeSize = mData.size

            // Add more data
            mData.addAll(items)

            // Update and show data
            notifyItemInserted(beforeSize)
        }
    }

    /**
     * Reset the data list, set a new one
     */
    fun setList(list: List<T>?) {
        mData.clear()
        if (list != null) {
            mData.addAll(list)
        }
        notifyDataSetChanged()
    }

    /**
     * Edit one item with index
     */
    fun updateItemAt(item: T, index: Int) {
        mData[index] = item
        notifyItemChanged(index)
    }

    /**
     * Remove all data
     */
    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
    }

    /**
     * Remove one item with index
     */
    fun removeItemAt(index: Int) {
        mData.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, mData.size)
    }

    /**
     * Getter of mData property
     */
    fun getData(): List<T> {
        return mData
    }
}