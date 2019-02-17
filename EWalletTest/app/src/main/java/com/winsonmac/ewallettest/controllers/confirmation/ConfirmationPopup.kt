package com.winsonmac.ewallettest.controllers.confirmation

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.WindowManager
import com.winsonmac.core.base.BaseActivity
import com.winsonmac.core.popup.MessagePopup
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.controllers.phone.PhoneActivity
import com.winsonmac.ewallettest.databinding.ConfirmationPopupBinding

class ConfirmationPopup(private var activity: BaseActivity, context: Context, viewModel: ConfirmationViewModel) :
    Dialog(context, R.style.PopupWindow),
    ConfirmationPopupEventHandler {

    private var mConfirmViewModel: ConfirmationViewModel = viewModel

    private val mPopupBinding: ConfirmationPopupBinding by lazy {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.confirmation_popup, null, false)
        ConfirmationPopupBinding.bind(view)
    }

    init {
        setContentView(mPopupBinding.root)
        initDialog()
    }

    /* =========================================================================================== */
    /*  Confirmation popup event handler                                                           */
    /* =========================================================================================== */

    override fun onConfirmClick() {
        if (mConfirmViewModel.isOTPValid()) {
            dismiss()
            activity.ui().showLoading()
            mConfirmViewModel.createPayment { success, message, messageTitle, messageButtonTitle ->
                activity.ui().dismissLoading()
                if (success) {
                    activity.ui().showPopup(messageTitle!!, message!!, messageButtonTitle!!) {
                        
                        // This is the last step, we need to restart the flow again
                        activity.startActivity(
                            activity.ui().createIntentWithFlags(
                                PhoneActivity::class.java,
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        )
                    }
                } else {
                    activity.ui().showPopup(messageTitle!!, message!!, messageButtonTitle!!)
                }
            }
        }
    }

    override fun onDismissClick() {
        dismiss()
    }


    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    private fun initDialog() {

        mPopupBinding.handler = this
        mPopupBinding.viewModel = mConfirmViewModel

        setCanceledOnTouchOutside(false)
        setCancelable(false)

        window!!.setLayout(properScreenWidth(), WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun screenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        return display.width
    }

    private fun properScreenWidth(): Int {
        return (screenWidth(context) * .8).toInt()
    }
}