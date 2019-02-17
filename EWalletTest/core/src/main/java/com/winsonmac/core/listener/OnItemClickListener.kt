package com.winsonmac.core.listener

/* =========================================================================================== */
/*  On adapter item clicked event handler                                                      */
/* =========================================================================================== */

public interface OnItemClickListener<T> {

    fun onItemClick(item: T, position: Int)
}