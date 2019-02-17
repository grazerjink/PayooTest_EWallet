package com.winsonmac.ewallettest

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.winsonmac.core.helper.MyShareReferences
import com.winsonmac.core.helper.APIManager
import com.winsonmac.ewallettest.apis.APIConfig


/* =========================================================================================== */
/*  Custom application for configuring stuff                                                   */
/* =========================================================================================== */

class EWalletApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var root: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        root = applicationContext

        /**
         * Config some need init first helpers
         */
        APIManager.newInstance(APIConfig.SERVER_URL)

        MyShareReferences.newInstance(root)
    }

}