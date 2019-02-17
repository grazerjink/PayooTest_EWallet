package com.winsonmac.ewallettest.controllers.confirmation

import android.content.Context
import android.databinding.Observable
import android.databinding.ObservableField
import com.google.gson.JsonElement
import com.winsonmac.core.base.BaseViewModel
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.apis.APICallback
import com.winsonmac.ewallettest.apis.ListAPIPayment
import com.winsonmac.ewallettest.models.CardModel
import com.winsonmac.ewallettest.models.PaymentMethodModel


class ConfirmationViewModel(context: Context) : BaseViewModel<ListAPIPayment>(context) {

    var mPhoneNumber: String? = null
    var mCardInfo: CardModel? = null
    var mPaymentMethod: PaymentMethodModel? = null
    var mTotalAmount: String? = "0 đ"

    // OTP code
    var mCode: ObservableField<String> = ObservableField()
    var mError: ObservableField<String> = ObservableField()


    init {
        mCode.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                mError.set(null)
            }
        })
    }

    /**
     * Call api create payment
     */
    fun createPayment(completion: (success: Boolean, message: Int?, messageTitle: Int?, messageButtonTitle: Int?) -> Unit) {
        // Reset error
        mError.set(null)
        
        // All requires is valid, then call api
        api().createPayment(mCode.get()!!).enqueue(APICallback<JsonElement> { success, data, message ->
            if (success) {

                // Reset otp code
                mCode.set(null)

                completion.invoke(
                    true,
                    R.string.confirmation_screen_create_payment_successful,
                    R.string.message_popup_inform_title,
                    R.string.message_popup_ok_title
                )
            } else {
                completion.invoke(
                    false,
                    R.string.confirmation_screen_create_payment_failed,
                    R.string.message_popup_fail_title,
                    R.string.message_popup_ok_title
                )
            }
        })
    }


    /**
     * Not allowed empty otp code
     */
    fun isOTPValid(): Boolean {

        val isValid = !mCode.get().isNullOrEmpty()

        if (!isValid)
            mError.set(context.getString(R.string.confirmation_screen_otp_required))

        return isValid
    }


    /**
     * Load data
     */
    fun setupData(phoneNumber: String?, cardInfo: CardModel?, paymentMethod: PaymentMethodModel?) {
        mPhoneNumber = phoneNumber
        mCardInfo = cardInfo
        mPaymentMethod = paymentMethod
        mTotalAmount = mCardInfo?.getPrice()

    }
}