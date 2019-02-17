package com.winsonmac.ewallettest.env.extensions

import android.os.Handler


fun Handler.delay(time: Long, callback: () -> Unit) {
    Handler().postDelayed({
        callback.invoke()
    }, time)
}