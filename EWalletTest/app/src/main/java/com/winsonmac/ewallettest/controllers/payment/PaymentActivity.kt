package com.winsonmac.ewallettest.controllers.payment

import com.winsonmac.core.base.BaseActivity
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.controllers.confirmation.ConfirmationActivity
import com.winsonmac.ewallettest.databinding.PaymentActivityBinding
import com.winsonmac.ewallettest.env.Constants
import com.winsonmac.ewallettest.models.CardModel


class PaymentActivity : BaseActivity(), PaymentEventHandler {

    private val mPaymentBinding by lazy {
        bindingContentLayout<PaymentActivityBinding>(R.layout.payment_activity)
    }
    private val mPaymentViewModel by lazy {
        PaymentViewModel(this)
    }

    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun setupViews() {
        ui().showToolbarTitle(R.string.payment_screen_title)
        ui().showToolbarSubtitle(R.string.payment_screen_subtitle)

        // Intent process
        val phoneNumber = intent.getStringExtra(Constants.KEY_PHONE_NUMBER)
        val cardInfo = intent.getParcelableExtra<CardModel>(Constants.KEY_CARD_INFO)

        mPaymentViewModel.setupPaymentRules(phoneNumber, cardInfo)

        // trigger binding
        mPaymentBinding.viewModel = mPaymentViewModel
        mPaymentBinding.handler = this
    }


    /* =========================================================================================== */
    /*  Event handler                                                                              */
    /* =========================================================================================== */

    override fun onBackClick() {
        finish()
    }

    override fun onNextClick() {
        val confirmIntent = ui().createIntentWithFlags(ConfirmationActivity::class.java)
        confirmIntent.putExtra(Constants.KEY_PHONE_NUMBER, mPaymentViewModel.mPhoneNumber)
        confirmIntent.putExtra(Constants.KEY_CARD_INFO, mPaymentViewModel.mCardInfo)
        confirmIntent.putExtra(Constants.KEY_PAYMENT_METHOD, mPaymentViewModel.mPaymentAdapter.getSelectedItem())
        startActivity(confirmIntent)
    }
}
