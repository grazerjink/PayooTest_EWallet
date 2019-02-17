package com.winsonmac.ewallettest.controllers.phone

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.winsonmac.core.base.BaseAdapter
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.databinding.PhoneCardItemBinding
import com.winsonmac.ewallettest.models.CardModel


class CardAdapter(
    mContext: Context,
    mData: MutableList<CardModel>,
    private val callback: (selectedItem: CardModel?) -> Unit
) :
    BaseAdapter<CardModel>(mContext, mData) {

    private var selectedIndex: Int = -1


    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun onCreateViewHolder(container: ViewGroup, type: Int): RecyclerView.ViewHolder {
        val view = inflater.inflate(R.layout.phone_card_item, container, false)
        return CardViewHolder(PhoneCardItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        (holder as CardViewHolder).bindModel(pos)
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

    inner class CardViewHolder(private val mItemBinding: PhoneCardItemBinding) :
        RecyclerView.ViewHolder(mItemBinding.root),
        View.OnClickListener {

        private var mCurrCard: CardModel? = null
        private var mCurrPos: Int? = null

        fun bindModel(pos: Int) {
            mCurrCard = getData()[pos]
            mCurrPos = pos
            mItemBinding.card = mCurrCard
            mItemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mCurrCard?.apply {
                if (mCurrPos != selectedIndex) {

                    isSelected.set(true)

                    // Just able to select only one item
                    if (selectedIndex != -1)
                        getData()[selectedIndex].isSelected.set(false)

                    selectedIndex = mCurrPos!!

                    // For check info is valid to enable button continue
                    callback.invoke(mCurrCard)
                }
            }
        }

    }


}