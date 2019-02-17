package com.winsonmac.ewallettest.models

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.os.Parcel
import android.os.Parcelable
import com.winsonmac.ewallettest.env.utils.NumberUtil

class CardModel(value: Int = 0) : BaseObservable(), Parcelable {

    var mValue: Int = 0
    var isSelected: ObservableBoolean = ObservableBoolean(false)

    init {
        mValue = value
    }

    /**
     * Get the format currency value
     */
    fun getPrice() = NumberUtil.toCurrency(mValue)


    /* =========================================================================================== */
    /*  Parcelable implementation                                                                  */
    /* =========================================================================================== */

    constructor(parcel: Parcel) : this() {
        mValue = parcel.readInt()
        isSelected = parcel.readParcelable(ObservableBoolean::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mValue)
        parcel.writeParcelable(isSelected, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardModel> {
        override fun createFromParcel(parcel: Parcel): CardModel {
            return CardModel(parcel)
        }

        override fun newArray(size: Int): Array<CardModel?> {
            return arrayOfNulls(size)
        }
    }


}