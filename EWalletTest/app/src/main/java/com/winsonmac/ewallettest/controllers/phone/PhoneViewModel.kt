package com.winsonmac.ewallettest.controllers.phone

import android.content.Context
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.winsonmac.core.base.BaseViewModel
import com.winsonmac.ewallettest.env.utils.ValidateUtil
import com.winsonmac.ewallettest.models.CardModel

class PhoneViewModel(context: Context) : BaseViewModel<Void>(context) {

    val isInfoValid by lazy { ObservableBoolean(false) }

    val mPhone by lazy { ObservableField<String>() }

    val mCardAdapter by lazy {
        CardAdapter(context, initCardList()) {
            isInfoValid.set(isPhoneValid() && it != null)
        }
    }

    init {
        mPhone.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                isInfoValid.set(isPhoneValid() && mCardAdapter.isSelectedItem())
            }
        })
    }


    /**
     * Check phone number is valid
     */
    private fun isPhoneValid() = !mPhone.get().isNullOrEmpty() && ValidateUtil.isPhoneNumberFormat(mPhone.get())


    /**
     * Create a selectable card list
     */
    private fun initCardList() = mutableListOf(
        CardModel(10_000),
        CardModel(20_000),
        CardModel(30_000),
        CardModel(50_000),
        CardModel(100_000),
        CardModel(200_000),
        CardModel(300_000),
        CardModel(500_000)
    )
}