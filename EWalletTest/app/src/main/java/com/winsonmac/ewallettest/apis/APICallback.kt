package com.winsonmac.ewallettest.apis

import android.content.Context
import android.net.ConnectivityManager
import com.winsonmac.ewallettest.EWalletApplication
import com.winsonmac.ewallettest.R
import com.winsonmac.ewallettest.env.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* =========================================================================================== */
/*  The maintainable callback customized on Retrofit callback                                  */
/* =========================================================================================== */

class APICallback<T>(val callback: (success: Boolean, data: T?, message: Int?) -> Unit) : Callback<T> {

    /**
     * Check network status
     */
    @Transient
    var networkState: ConnectivityManager =
        EWalletApplication.root.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * When response arrived, opera the error before submitting to destination
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        checkHttpStatusCode(response) { success, message ->
            if (success) {
                callback(success, response.body(), null)
            } else {
                callback(success, null, message)
            }
        }
    }


    /**
     * When error arrived, system just redirect the error message to destination
     */
    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        callback(false, null, getFailureError())
    }


    /**
     * Filter the kind of errors and raises it
     */
    private fun <T> checkHttpStatusCode(
        response: Response<T>,
        callback: ((success: Boolean, message: Int?) -> Unit)
    ) {
        if (response.body() == null) {
            callback.invoke(false, R.string.server_no_data_error)
        } else {
            when (response.code()) {
                Constants.SERVER_SUCCESS_CODE -> callback.invoke(true, null)
                Constants.SERVER_BAD_REQUEST_CODE -> callback.invoke(false, R.string.server_bad_request_error)
                Constants.SERVER_PAGE_NOT_FOUND_CODE -> callback.invoke(false, R.string.server_page_not_found_error)
                Constants.SERVER_ERROR_CODE -> callback.invoke(false, R.string.server_failed_error)
                else -> callback.invoke(false, getFailureError())
            }
        }
    }

    /**
     * Check the special error such as network connection or sth else...
     */
    private fun getFailureError(): Int {
        val activeNetworkInfo = networkState.activeNetworkInfo
        return if (activeNetworkInfo != null && activeNetworkInfo.isAvailable && activeNetworkInfo.isConnected) {
            R.string.server_no_internet_error
        } else {
            R.string.server_unknown_error
        }
    }
}