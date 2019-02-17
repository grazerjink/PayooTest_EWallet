package com.winsonmac.ewallettest.controllers.payment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.winsonmac.core.base.BaseAdapter
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.databinding.PaymentItemBinding
import com.winsonmac.ewallettest.models.PaymentMethodModel


class PaymentAdapter(
    mContext: Context,
    mData: MutableList<PaymentMethodModel>,
    private val callback: (selectedItem: PaymentMethodModel?) -> Unit

) : BaseAdapter<PaymentMethodModel>(mContext, mData) {

    private var selectedIndex: Int = -1


    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun onCreateViewHolder(container: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.payment_item, container, false)
        return PaymentViewHolder(PaymentItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        (holder as PaymentViewHolder).bindModel(pos)
    }


    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    /**
     * Check is selected item
     */
    fun isSelectedItem() = selectedIndex != -1

    /**
     * Get selected card info
     */
    fun getSelectedItem() = getData()[selectedIndex]


    /* =========================================================================================== */
    /*  View holder class                                                                          */
    /* =========================================================================================== */

    inner class PaymentViewHolder(private val mItemBinding: PaymentItemBinding) :
        RecyclerView.ViewHolder(mItemBinding.root),
        View.OnClickListener {

        private var mCurrMethod: PaymentMethodModel? = null
        private var mCurrPos: Int? = null

        fun bindModel(pos: Int) {
            mCurrMethod = getData()[pos]
            mCurrPos = pos
            mItemBinding.method = mCurrMethod
            mItemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mCurrMethod?.apply {
                if (!isDisabled.get() && mCurrPos != selectedIndex) {

                    isSelected.set(true)

                    // Just able to select only one item
                    if (selectedIndex != -1)
                        getData()[selectedIndex].isSelected.set(false)

                    selectedIndex = mCurrPos!!

                    // For check info is valid to enable button continue
                    callback.invoke(mCurrMethod)
                }
            }
        }

    }


}