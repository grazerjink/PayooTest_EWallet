package com.winsonmac.core.helper

import android.os.Bundle
import com.winsonmac.core.base.BaseFragment


/* =========================================================================================== */
/*  For lazy loading purpose                                                                   */
/* =========================================================================================== */

abstract class LazyFragment : BaseFragment() {

    private val VISIBLE_STATE = "visible_state"
    private val FIRST_LOADING_STATE = "first_loading_state"
    private val VIEW_CREATED_STATE = "view_created_state"

    protected var isUserVisible = false
    protected var isViewCreated = false
    protected var isFirstLoaded = false

    /* =========================================================================================== */
    /*  Lifecycle                                                                                 */
    /* =========================================================================================== */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isViewCreated && isUserVisible && !isFirstLoaded) {
            isFirstLoaded = true
            firstLazyLoadingData()
        }
        isViewCreated = true
    }

    /**
     * Get the saved value for lazy loading
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            isUserVisible = savedInstanceState.getBoolean(VISIBLE_STATE)
            isFirstLoaded = savedInstanceState.getBoolean(FIRST_LOADING_STATE)
            isViewCreated = savedInstanceState.getBoolean(VIEW_CREATED_STATE)
            saveData(savedInstanceState)
        }
    }

    /**
     * Save the current value for lazy loading
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(VISIBLE_STATE, isUserVisible)
        outState.putBoolean(FIRST_LOADING_STATE, isFirstLoaded)
        outState.putBoolean(VIEW_CREATED_STATE, isViewCreated)
    }

    /**
     * Check if the page is visible, then load data
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isUserVisible = true
            if (isViewCreated) {
                if (!isFirstLoaded) {
                    isFirstLoaded = true
                    firstLazyLoadingData()
                } else {
                    reloadData()
                }
            }
        } else {
            isUserVisible = false
        }
    }


    /* =========================================================================================== */
    /*  Abstract methods                                                                           */
    /* =========================================================================================== */

    // Just run only one time for first setup
    protected abstract fun firstLazyLoadingData()

    // When fragment stop, this method will be triggered
    protected abstract fun saveData(bundle: Bundle)

    // When fragment is re-showing, this method will be triggered
    protected abstract fun reloadData()
}