package com.winsonmac.core.popup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.winsonmac.core.R

/* =========================================================================================== */
/*  Loading popup                                                                              */
/* =========================================================================================== */

class LoadingPopup(context: Context) : Dialog(context) {

    private var keepLoading = false

    /**
     * Init and setup stuff
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_loading_popup)
        setCancelable(false)

        val loadingColor = ContextCompat.getColor(context, R.color.loading_color)
        findViewById<ProgressBar>(R.id.loadingView)
            .indeterminateDrawable.setColorFilter(loadingColor, android.graphics.PorterDuff.Mode.MULTIPLY)
        window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    /**
     * Override keep the keepLoading state
     */
    override fun show() {
        keepLoading = true
        super.show()
    }


    /**
     * Check should keep the loading state, so don't hide the popup
     * in case of onPause onStop life cycles
     */
    fun shouldShowLoading() {
        if (keepLoading) {
            show()
        }
    }
    

    /**
     * Dismiss and reset the keepLoading state
     */
    fun dismissAndRefreshState() {
        keepLoading = false
        dismiss()
    }
}