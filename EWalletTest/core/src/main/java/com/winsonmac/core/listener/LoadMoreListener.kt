package com.winsonmac.core.listener

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/* =========================================================================================== */
/*  Load more event handler                                                                    */
/* =========================================================================================== */

class LoadMoreListener : RecyclerView.OnScrollListener {

    // Delegate for custom logic
    interface OnLoadMoreListener {
        fun onLoadMore()
        fun onStateChanged()
    }

    private var previousTotalItemCount = 0
    private var loading = true
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var loadMoreListener: OnLoadMoreListener? = null

    constructor(recyclerView: RecyclerView) : super() {
        this.mLayoutManager = recyclerView.layoutManager
        recyclerView.addOnScrollListener(this)
    }

    constructor(recyclerView: RecyclerView, loadMoreListener: OnLoadMoreListener) : super() {
        this.mLayoutManager = recyclerView.layoutManager
        this.loadMoreListener = loadMoreListener
        recyclerView.addOnScrollListener(this)
    }


    /* =========================================================================================== */
    /*  When recycler view was scrolled                                                            */
    /* =========================================================================================== */

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        var lastVisibleItemPosition = 0

        val totalItemCount = mLayoutManager!!.itemCount

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions =
                (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)

        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()

        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        val visibleThreshold = 5

        if (!loading && dy > 0 && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            loadMoreListener!!.onLoadMore()
            loading = true
        }
    }


    /**
     * Enable the cusom logic handler
     */
    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.loadMoreListener = loadMoreListener
    }


    /**
     * The the last visible item count
     */
    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }


    /**
     * Reset the current load more state
     */
    fun resetState() {
        this.previousTotalItemCount = 0
        this.loading = true
    }


    /**
     * For handling the scrolling event
     */
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        this.loadMoreListener!!.onStateChanged()
    }
}