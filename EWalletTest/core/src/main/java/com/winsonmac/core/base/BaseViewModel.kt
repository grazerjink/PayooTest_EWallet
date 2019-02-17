package com.winsonmac.core.base

import android.content.Context
import android.databinding.BaseObservable
import com.google.gson.Gson
import com.winsonmac.core.helper.MyShareReferences
import com.winsonmac.core.helper.PagingHelper
import com.winsonmac.core.helper.APIManager
import java.io.Serializable
import java.lang.reflect.ParameterizedType


/* =========================================================================================== */
/*  Base view model for easy implementation                                                    */
/* =========================================================================================== */

open class BaseViewModel<T>(val context: Context) : BaseObservable(), Serializable {

    // Api manage
    private val mApi by lazy { APIManager.getInstance().create(newInstanceByClassName()) }

    // Convert json purpose
    private val mGson by lazy { Gson() }

    // Application share storage
    private val mSharePref by lazy { MyShareReferences.getInstance() }

    // Paging feature
    private val mPaging by lazy { PagingHelper() }

    /* =========================================================================================== */
    /*  Getters                                                                                    */
    /* =========================================================================================== */

    fun api() = mApi

    fun gson() = mGson

    fun share() = mSharePref

    fun paging() = mPaging


    /**
     * Retrieve the instance of class by the class name
     */
    @Throws(ClassNotFoundException::class)
    private fun newInstanceByClassName(): Class<T> {
        val mySuperclass = javaClass.genericSuperclass
        val tType = (mySuperclass as ParameterizedType).actualTypeArguments[0]
        val className = tType.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        return Class.forName(className) as Class<T>
    }

}