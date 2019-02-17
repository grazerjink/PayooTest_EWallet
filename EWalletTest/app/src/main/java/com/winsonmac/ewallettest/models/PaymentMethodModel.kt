package com.winsonmac.ewallettest.models

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.os.Parcel
import android.os.Parcelable

class PaymentMethodModel(title: String = "") : BaseObservable(), Parcelable {

    var mTitle: String = ""
    var isSelected: ObservableBoolean = ObservableBoolean(false)
    var isDisabled: ObservableBoolean = ObservableBoolean(false)

    init {
        mTitle = title
    }


    /* =========================================================================================== */
    /*  Parcelable implementation                                                                  */
    /* =========================================================================================== */

    constructor(parcel: Parcel) : this() {
        mTitle = parcel.readString()
        isSelected = parcel.readParcelable(ObservableBoolean::class.java.classLoader)
        isDisabled = parcel.readParcelable(ObservableBoolean::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mTitle)
        parcel.writeParcelable(isSelected, flags)
        parcel.writeParcelable(isDisabled, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentMethodModel> {
        override fun createFromParcel(parcel: Parcel): PaymentMethodModel {
            return PaymentMethodModel(parcel)
        }

        override fun newArray(size: Int): Array<PaymentMethodModel?> {
            return arrayOfNulls(size)
        }
    }


}