package com.winsonmac.ewallettest.controllers.phone

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.ContactsContract
import com.winsonmac.core.base.BaseActivity
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.databinding.PhoneActivityBinding
import com.winsonmac.ewallettest.env.Constants.REQUEST_READ_CONTACT_PERMISSION_CODE
import android.content.pm.PackageManager
import com.winsonmac.ewallettest.controllers.payment.PaymentActivity
import com.winsonmac.ewallettest.env.Constants
import com.winsonmac.ewallettest.env.Constants.RESULT_CONTACT_CODE


class PhoneActivity : BaseActivity(), PhoneEventHandler {

    private val mPhoneBinding by lazy {
        bindingContentLayout<PhoneActivityBinding>(R.layout.phone_activity)
    }
    private val mPhoneViewModel by lazy {
        PhoneViewModel(this)
    }

    /* =========================================================================================== */
    /*  Implement super methods                                                                    */
    /* =========================================================================================== */

    override fun setupViews() {
        ui().showToolbarTitle(R.string.phone_screen_title)
        ui().showToolbarSubtitle(R.string.phone_screen_subtitle)

        // Trigger initiation
        mPhoneBinding.viewModel = mPhoneViewModel
        mPhoneBinding.handler = this
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constants.RESULT_CONTACT_CODE -> data?.apply {
                    getContactInfo(this)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_READ_CONTACT_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactFromDevice(Constants.RESULT_CONTACT_CODE)
                }
            }
        }
    }


    /* =========================================================================================== */
    /*  Event handler                                                                              */
    /* =========================================================================================== */

    override fun onContactClick() {
        getContactFromDevice(RESULT_CONTACT_CODE)
    }

    override fun onNextClick() {
        val paymentIntent = ui().createIntentWithFlags(PaymentActivity::class.java)
        paymentIntent.putExtra(Constants.KEY_PHONE_NUMBER, mPhoneViewModel.mPhone.get())
        paymentIntent.putExtra(Constants.KEY_CARD_INFO, mPhoneViewModel.mCardAdapter.getSelectedItem())
        startActivity(paymentIntent)
    }


    /* =========================================================================================== */
    /*  Additional methods                                                                         */
    /* =========================================================================================== */

    /**
     * Collect a contact from user device, but we need to check permission first
     */
    private fun getContactFromDevice(requestCode: Int) {
        if (hasPermission(Manifest.permission.READ_CONTACTS)) {
            val contactPickerIntent = Intent(Intent.ACTION_PICK)
            contactPickerIntent.type = ContactsContract.Contacts.CONTENT_TYPE
            startActivityForResult(contactPickerIntent, requestCode)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_READ_CONTACT_PERMISSION_CODE
                )
            }
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data: Intent that gets from activity result
     */
    private fun getContactInfo(data: Intent) {

        try {
            val uri = data.data!!
            val cursor = contentResolver.query(uri, null, null, null, null)

            cursor?.apply {

                moveToFirst()

                val id = getString(getColumnIndex(ContactsContract.Contacts._ID))
                val hasPhone = getInt(getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                // get the user's phone number
                var phoneNumber: String? = null

                if (hasPhone > 0) {

                    // Query a phone upon a root cursor
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )

                    // Retrieve phone number
                    phoneCursor?.apply {
                        moveToFirst()
                        phoneNumber = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        close()
                    }
                }

                // Close cursor at root
                close()

                mPhoneViewModel.mPhone.set(phoneNumber?.trim())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
