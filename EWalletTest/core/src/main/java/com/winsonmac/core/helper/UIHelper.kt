package com.winsonmac.core.helper

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.winsonmac.core.R
import com.winsonmac.core.popup.LoadingPopup
import com.winsonmac.core.popup.MessagePopup
import com.winsonmac.core.base.BaseActivity
import com.winsonmac.core.util.FontUtil


/* =========================================================================================== */
/*  Helper class for reuse the ui controller operation code                                    */
/* =========================================================================================== */

class UIHelper(private val mContext: Context, private val mActivity: BaseActivity) {

    private val mToolbarLayout: AppBarLayout = mActivity.findViewById(R.id.app_bar_layout)
    private val mToolbarView: Toolbar = mActivity.findViewById(R.id.toolbar)
    private val mToolbarTitle: TextView = mActivity.findViewById(R.id.tv_toolbar_title)
    private val mToolbarSubtitle: TextView = mActivity.findViewById(R.id.tv_toolbar_subtitle)
    private val mShare: MyShareReferences = MyShareReferences.getInstance()
    private val mLoading: LoadingPopup = LoadingPopup(mContext)
    private val mMessagePopup: MessagePopup.Builder = MessagePopup.Builder(mContext)


    /* =========================================================================================== */
    /*  Reusable helper methods                                                                   */
    /* =========================================================================================== */

    /**
     * Just simply to enable toolbar with string title
     */
    fun showToolbarTitle(title: String) {
        mActivity.setSupportActionBar(mToolbarView)
        mActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        mActivity.supportActionBar?.title = null
        mToolbarTitle.typeface = FontUtil.loadFont(mContext, FontUtil.FONT_ROBOTO_BOLD)
        mToolbarTitle.text = title
    }


    /**
     * Enable toolbar subtitle with the resource id
     */
    fun showToolbarTitle(titleResId: Int) {
        showToolbarTitle(mActivity.getString(titleResId))
    }


    /**
     * Just simply to enable toolbar subtitle with string
     */
    fun showToolbarSubtitle(title: String) {
        mActivity.setSupportActionBar(mToolbarView)
        mActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        mActivity.supportActionBar?.title = null
        mToolbarSubtitle.typeface = FontUtil.loadFont(mContext, FontUtil.FONT_ROBOTO_REGULAR)
        mToolbarSubtitle.text = title
    }


    /**
     * Enable toolbar with the title resource id
     */
    fun showToolbarSubtitle(titleResId: Int) {
        showToolbarSubtitle(mActivity.getString(titleResId))
    }


    /**
     * Hide the toolbar for another customization
     */
    fun hideToolbar() {
        mToolbarLayout.visibility = View.GONE
    }


    /**
     * Simply to show the back button
     */
    fun showBackButton() {
        mActivity.supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }


    /**
     * Just create and show up
     */
    fun showLoading() {
        mLoading.show()
    }


    /**
     * Just dismiss without update state keeping state
     */
    fun hideLoading() {
        mLoading.dismiss()
    }


    /**
     * Check whether loading should show up continuously or not
     */
    fun shouldShowLoading() {
        mLoading.shouldShowLoading()
    }


    /**
     * Dismiss the loading with updating the keep loading state
     */
    fun dismissLoading() {
        mLoading.dismissAndRefreshState()
    }


    /**
     * Hide the keyboard of the current focus view
     */
    fun hideKeyboard() {
        val view = mActivity.currentFocus
        if (view != null) {
            val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    /**
     * Help creating a intent with short typing
     *
     * @param activityClass The class would be navigate to, but it's null method just create a empty intent
     * @param intentFlags   The flags to notify the new activity how to create
     * @return Intent just been created.
     */
    fun createIntentWithFlags(activityClass: Class<*>?, intentFlags: Int? = null): Intent {
        val intent: Intent = if (activityClass != null)
            Intent(mContext, activityClass)
        else
            Intent()

        if (intentFlags != null)
            intent.flags = intentFlags

        return intent
    }


    /**
     * Show popup and able to pass with res id
     */
    fun showPopup(title: Int, message: Int, buttonTitle: Int, event: ((dialog: MessagePopup) -> Unit)? = null) {
        showPopup(title, mContext.getString(message), buttonTitle, event)
    }


    /**
     * Show popup with string content
     */
    fun showPopup(title: Int, message: String, buttonTitle: Int, event: ((dialog: MessagePopup) -> Unit)? = null) {
        try {
            mMessagePopup.enableConfirmMode(false)
                .setTitle(title)
                .setMessage(message)
                .setSingleButtonTitle(buttonTitle)
                .setSingleButtonListener(event)
                .create()
                .show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


    /**
     * Show the prompt popup and able to pass res id content message
     */
    fun showConfirmDialog(
        title: Int,
        message: Int,
        positiveTitle: Int,
        negativeTitle: Int,
        positiveEvent: ((dialog: MessagePopup) -> Unit)? = null,
        negativeEvent: ((dialog: MessagePopup) -> Unit)? = null
    ) {
        showConfirmDialog(
            title,
            mContext.getString(message),
            mContext.getString(positiveTitle),
            mContext.getString(negativeTitle),
            positiveEvent, negativeEvent
        )
    }


    /**
     * Show the prompt popup and able to pass string content message
     */
    fun showConfirmDialog(
        title: Int,
        message: String,
        positiveTitle: String,
        negativeTitle: String,
        positiveCallback: ((dialog: MessagePopup) -> Unit)? = null,
        negativeCallback: ((dialog: MessagePopup) -> Unit)? = null
    ) {
        try {
            mMessagePopup.enableConfirmMode(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButtonTitle(positiveTitle)
                .setNegativeButtonTitle(negativeTitle)
                .setPositiveButtonListener(positiveCallback)
                .setNegativeButtonListener(negativeCallback)
                .create()
                .show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }


    /**
     * Toast helper
     */
    fun showToast(message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }


    /**
     * Get share reference with shortcut
     */
    fun share() = mShare
}