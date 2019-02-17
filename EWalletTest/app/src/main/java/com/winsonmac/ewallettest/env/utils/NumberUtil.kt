package com.winsonmac.ewallettest.env.utils

import java.util.*

object NumberUtil {

    fun toCurrency(value: Any, locale: Locale = Locale("vi")): String {
        return if (value is Float) {
            String.format(locale, "%,.0f đ", value)
        } else if (value is Double) {
            String.format(locale, "%,.0f đ", value)
        } else if (value is Int) {
            String.format(locale, "%,d đ", value)
        } else if (value is Long) {
            String.format(locale, "%,d đ", value)
        } else {
            "0 đ"
        }
    }
}