package com.winsonmac.ewallettest.env.utils

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.winsonmac.core.util.FontUtil
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.env.helper.GridSpacingDecorator

object BindingUtil {

    /**
     * Shortcut to setup grid layout manager for recycler view
     */
    @JvmStatic
    @BindingAdapter("gridLayoutManager")
    fun setGridLayoutManager(recyclerView: RecyclerView, col: Int) {
        val res = recyclerView.context.resources
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, col)
        recyclerView.addItemDecoration(GridSpacingDecorator(col, res.getDimensionPixelSize(R.dimen._10sdp), false))
    }

    /**
     * Setup adapter for recycler for showing data
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }

    /**
     * Init & set font for the text view
     */
    @JvmStatic
    @BindingAdapter("font")
    fun setFont(textView: TextView, fontName: String) {
        textView.typeface = FontUtil.loadFont(textView.context, fontName)
    }
}