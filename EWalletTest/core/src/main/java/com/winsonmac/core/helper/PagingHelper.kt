package com.winsonmac.core.helper

import android.support.v7.widget.RecyclerView
import com.winsonmac.core.listener.LoadMoreListener

/* =========================================================================================== */
/*  Help the paging feature                                                                    */
/* =========================================================================================== */

class PagingHelper {

    // Current page
    private var mPage = 1

    // Flag marks last page
    private var mLastPage: Boolean = false

    // Load more handler
    private var loadMoreListener: LoadMoreListener? = null


    /**
     * Check should increase the current page number
     */
    fun shouldNextPage(lastPage: Int) {
        mLastPage = mPage == lastPage
        mPage++
    }


    /**
     * Check current page is last page
     */
    fun isLastPage(): Boolean {
        return mLastPage
    }
    

    /**
     * Enable the load more feature when trigger it
     */
    fun enableLoadMore(recyclerView: RecyclerView, listener: LoadMoreListener.OnLoadMoreListener) {
        loadMoreListener = LoadMoreListener(recyclerView, listener)
    }


    /**
     * Everything will be renew
     */
    fun reset() {
        mPage = 1
        mLastPage = false
        loadMoreListener!!.resetState()
    }


    /**
     * Customer getter
     */
    fun getCurrentPage(): Int {
        return mPage
    }

}