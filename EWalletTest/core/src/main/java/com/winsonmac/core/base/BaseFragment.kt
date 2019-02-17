package com.winsonmac.core.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.support.v4.app.Fragment
import com.winsonmac.core.helper.UIHelper

/* =========================================================================================== */
/*  For reuse and maintenance purpose                                                         */
/* =========================================================================================== */

open class BaseFragment : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: BaseActivity


    /* =========================================================================================== */
    /*  Lifecycle methods                                                                          */
    /* =========================================================================================== */

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
        mActivity = activity as BaseActivity
        mActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    /* Empty methods */

    
    /* =========================================================================================== */
    /*  Getter & setter                                                                            */
    /* =========================================================================================== */

    protected fun ui(): UIHelper {
        return mActivity.ui()
    }
}