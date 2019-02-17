package com.winsonmac.core.popup

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.winsonmac.core.R
import com.winsonmac.core.listener.PopupHandler
import com.winsonmac.core.util.FontUtil


/* =========================================================================================== */
/*  Alert message popup                                                                        */
/* =========================================================================================== */

class MessagePopup(context: Context) : Dialog(context, R.style.PopupWindow) {

    private var btnOK: TextView
    private var btnYes: TextView
    private var btnNo: TextView
    private var tvPopupContent: TextView
    private var tvPopupTitle: TextView


    /**
     * Init and setup stuff
     */
    init {
        setContentView(R.layout.layout_message_popup)
        btnOK = findViewById(R.id.btn_submit)
        btnYes = findViewById(R.id.btn_yes)
        btnNo = findViewById(R.id.btn_no)
        tvPopupContent = findViewById(R.id.tv_popup_message)
        tvPopupTitle = findViewById(R.id.tv_popup_title)
    }


    /**
     * Map the components on view and setup them
     */
    private fun initView(doubleMode: Boolean) {

        tvPopupContent.typeface = FontUtil.loadFont(context, FontUtil.FONT_ROBOTO_REGULAR)
        tvPopupTitle.typeface = FontUtil.loadFont(context, FontUtil.FONT_ROBOTO_BOLD)
        btnOK.typeface = FontUtil.loadFont(context, FontUtil.FONT_ROBOTO_MEDIUM)
        btnNo.typeface = FontUtil.loadFont(context, FontUtil.FONT_ROBOTO_MEDIUM)
        btnYes.typeface = FontUtil.loadFont(context, FontUtil.FONT_ROBOTO_MEDIUM)

        val llTwoOption = findViewById<LinearLayout>(R.id.ll_two_option)
        if (doubleMode) {
            llTwoOption.visibility = View.VISIBLE
            btnOK.visibility = View.GONE
        } else {
            llTwoOption.visibility = View.GONE
            btnOK.visibility = View.VISIBLE
        }
    }


    /**
     * Init dialog and load data on
     */
    private fun initDialog(
        title: CharSequence?,
        message: CharSequence?,
        buttonTitle: CharSequence,
        callback: PopupHandler?
    ) {
        initView(false)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        tvPopupTitle.text = title
        tvPopupContent.text = message
        btnOK.text = buttonTitle
        btnOK.setOnClickListener {
            dismiss()
            callback?.invoke(this)
        }
        val properScreenWidth = properScreenWidth()
        val properScreenHeight = actualHeight(properScreenWidth, tvPopupTitle, tvPopupContent, btnOK)
        window!!.setLayout(properScreenWidth, properScreenHeight)
    }


    /**
     * Init confirm dialog and load data on
     */
    private fun initDialog(
        title: CharSequence?,
        message: CharSequence?,
        positiveButtonTitle: CharSequence,
        negativeButtonTitle: CharSequence,
        positiveCallback: PopupHandler?,
        negativeCallback: PopupHandler?
    ) {
        initView(true)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        tvPopupTitle.text = title
        tvPopupContent.text = message
        btnYes.text = positiveButtonTitle
        btnNo.text = negativeButtonTitle
        btnYes.setOnClickListener {
            dismiss()
            positiveCallback?.invoke(this)
        }
        btnNo.setOnClickListener {
            dismiss()
            negativeCallback?.invoke(this)

        }
        val properScreenWidth = properScreenWidth()
        val properScreenHeight = actualHeight(properScreenWidth, tvPopupTitle, tvPopupContent, btnOK)
        window!!.setLayout(properScreenWidth, properScreenHeight)
    }


    /**
     * The builder class for create a message popup
     */
    class Builder(private val context: Context) {
        private var singleButtonTitle: CharSequence = "OK"
        private var negativeButtonTitle: CharSequence = "Cancel"
        private var positiveButtonTitle: CharSequence = "Ok"
        private var alertTitle: CharSequence? = null
        private var alertMessage: CharSequence? = null
        private var singleButtonListener: PopupHandler? = null
        private var negativeButtonListener: PopupHandler? = null
        private var positiveButtonListener: PopupHandler? = null
        private var doubleMode = false

        fun setTitle(title: CharSequence): Builder {
            alertTitle = title
            return this
        }

        fun setTitle(title: Int): Builder {
            alertTitle = context.getString(title)
            return this
        }

        fun setMessage(message: CharSequence): Builder {
            alertMessage = message
            return this
        }

        fun setMessage(message: Int): Builder {
            alertMessage = context.getString(message)
            return this
        }

        fun setSingleButtonTitle(title: String): Builder {
            singleButtonTitle = title
            return this
        }

        fun setSingleButtonTitle(title: Int): Builder {
            singleButtonTitle = context.getString(title)
            return this
        }

        fun setSingleButtonListener(listener: PopupHandler?): Builder {
            singleButtonListener = listener
            return this
        }

        fun setNegativeButtonTitle(title: String): Builder {
            negativeButtonTitle = title
            return this
        }

        fun setNegativeButtonTitle(title: Int): Builder {
            negativeButtonTitle = context.getString(title)
            return this
        }

        fun setNegativeButtonListener(listener: PopupHandler?): Builder {
            negativeButtonListener = listener
            return this
        }

        fun setPositiveButtonTitle(title: String): Builder {
            positiveButtonTitle = title
            return this
        }

        fun setPositiveButtonTitle(title: Int): Builder {
            positiveButtonTitle = context.getString(title)
            return this
        }

        fun setPositiveButtonListener(listener: PopupHandler?): Builder {
            positiveButtonListener = listener
            return this
        }

        fun enableConfirmMode(enable: Boolean): Builder {
            doubleMode = enable
            return this
        }

        fun create(): MessagePopup {
            val messageDialog = MessagePopup(context)
            if (doubleMode) {
                messageDialog.initDialog(
                    alertTitle,
                    alertMessage,
                    positiveButtonTitle,
                    negativeButtonTitle,
                    positiveButtonListener,
                    negativeButtonListener
                )
            } else {
                messageDialog.initDialog(alertTitle, alertMessage, singleButtonTitle, singleButtonListener)
            }
            return messageDialog
        }
    }

    /* =========================================================================================== */
    /*  Calculate the size of the popup, make it wrap content height                               */
    /* =========================================================================================== */

    /**
     * Get total height of the child view in container
     */
    private fun contentHeight(view: View, screenWidth: Int): Int {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(widthMeasureSpec, heightMeasureSpec)
        return view.measuredHeight
    }


    /**
     * Get the screen width
     */
    private fun screenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        return display.width
    }


    /**
     * Calculate the max height of all child views
     */
    private fun actualHeight(screenWidth: Int, vararg views: View): Int {
        var totalHeight = 0
        for (view in views) {
            totalHeight += contentHeight(view, screenWidth)
        }
        return totalHeight
    }


    /**
     * To scale the width of the popup
     */
    private fun properScreenWidth(): Int {
        return (screenWidth(context) * .7).toInt()
    }
}
