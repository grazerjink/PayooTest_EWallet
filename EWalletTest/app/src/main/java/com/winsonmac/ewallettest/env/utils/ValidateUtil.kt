package com.winsonmac.ewallettest.env.utils

object ValidateUtil {

    private const val VALIDATE_PHONE_NUMBER = "[0-9]{10,11}"

    fun isPhoneNumberFormat(phone: String?): Boolean {
        phone?.apply {
            return matches(VALIDATE_PHONE_NUMBER.toRegex())
        }
        return false
    }
}