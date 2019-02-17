package com.winsonmac.ewallettest.controllers.payment

import android.content.Context
import android.databinding.ObservableBoolean
import com.winsonmac.core.base.BaseViewModel
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.models.CardModel
import com.winsonmac.ewallettest.models.PaymentMethodModel


class PaymentViewModel(context: Context) : BaseViewModel<Void>(context) {

    val isInfoValid by lazy { ObservableBoolean(false) }
    var mPhoneNumber: String? = null
    var mCardInfo: CardModel? = null
    val mPaymentAdapter by lazy {
        PaymentAdapter(context, mutableListOf()) {
            isInfoValid.set(it != null)
        }
    }

    fun setupPaymentRules(phone: String, cardInfo: CardModel) {
        mPhoneNumber = phone
        mCardInfo = cardInfo

        /**
         * If phone has prefix is '090' then the linked account will be disabled
         */
        val disabledLinkedAccount = mPhoneNumber!!.startsWith("090")
        val disabledCardOptions = mCardInfo!!.mValue < 100_000

        /**
         * Create a list payment methods for adapter
         */
        initPaymentOptionWithRules(disabledLinkedAccount, disabledCardOptions)
    }


    /**
     * Create a selectable payment method list
     */
    private fun initPaymentOptionWithRules(
        isDisabledLinkedAccOption: Boolean,
        isDisabledCardOptions: Boolean
    ) {

        val paymentMethods = mutableListOf<PaymentMethodModel>()
        val methodTitles = context.resources.getStringArray(R.array.payment_methods)
        methodTitles.forEach {
            val paymentMethodModel = PaymentMethodModel(it)
            if (it == context.getString(R.string.payment_screen_linked_account_title)) {
                // Linked account option
                paymentMethodModel.isDisabled.set(isDisabledLinkedAccOption)

            } else if (it == context.getString(R.string.payment_screen_international_card_title)
                || it == context.getString(R.string.payment_screen_domestic_card_title)
            ) {
                // Contains card options
                paymentMethodModel.isDisabled.set(isDisabledCardOptions)

            }
            paymentMethods.add(paymentMethodModel)
        }

        mPaymentAdapter.setList(paymentMethods)
    }
}