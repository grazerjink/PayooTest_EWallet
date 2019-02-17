package com.winsonmac.ewallettest.controllers.confirmation

import android.os.Handler
import com.winsonmac.core.base.BaseActivity
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.databinding.ConfirmationActivityBinding
import com.winsonmac.ewallettest.env.Constants
import com.winsonmac.ewallettest.env.extensions.delay
import com.winsonmac.ewallettest.models.CardModel
import com.winsonmac.ewallettest.models.PaymentMethodModel


class ConfirmationActivity : BaseActivity(), ConfirmationEventHandler {

    private val mConfirmationBinding by lazy {
        bindingContentLayout<ConfirmationActivityBinding>(R.layout.confirmation_activity)
    }
    private val mConfirmationViewModel by lazy {
        ConfirmationViewModel(this)
    }
    private val mOtpPopup by lazy {
        ConfirmationPopup(this, this, mConfirmationViewModel)
    }


    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun setupViews() {
        ui().showToolbarTitle(R.string.confirmation_screen_title)
        ui().showToolbarSubtitle(R.string.confirmation_screen_subtitle)

        // Intent process
        val phoneNumber = intent.getStringExtra(Constants.KEY_PHONE_NUMBER)
        val cardInfo = intent.getParcelableExtra<CardModel>(Constants.KEY_CARD_INFO)
        val paymentMethod = intent.getParcelableExtra<PaymentMethodModel>(Constants.KEY_PAYMENT_METHOD)

        // Setup data
        mConfirmationViewModel.setupData(phoneNumber, cardInfo, paymentMethod)

        // Trigger binding
        mConfirmationBinding.viewModel = mConfirmationViewModel
        mConfirmationBinding.handler = this
    }


    /* =========================================================================================== */
    /*  Event handler                                                                              */
    /* =========================================================================================== */

    override fun onPayClick() {
        ui().showLoading()
        Handler().delay(1000) {
            ui().dismissLoading()
            mOtpPopup.show()
        }
    }

    override fun onBackClick() {
        finish()
    }
}