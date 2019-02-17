package com.winsonmac.core.helper

import android.content.Context
import android.content.SharedPreferences
import java.lang.RuntimeException


/* =========================================================================================== */
/*  Custom reusable share references                                                           */
/* =========================================================================================== */

class MyShareReferences private constructor(appContext: Context) {

    private val PREFS_NAME = "PRIVATE_PREFS"

    private var settings: SharedPreferences

    companion object {

        private var sharePref: MyShareReferences? = null

        /**
         * Create a new singleton instance
         */
        fun newInstance(appContext: Context): MyShareReferences {
            if (sharePref == null) {
                synchronized(MyShareReferences::class.java) {
                    if (null == sharePref) {
                        sharePref = MyShareReferences(appContext)
                    }
                }
            }
            return sharePref!!
        }


        /**
         * Retrieve a created instance, if null rails error
         */
        fun getInstance(): MyShareReferences {
            if (sharePref == null) {
                throw RuntimeException("You must call newInstance() method before using this.")
            }
            return sharePref!!
        }
    }

    init {
        settings = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun saveString(key: String, text: String) {
        settings.edit().apply {
            putString(key, text)
        }.apply()
    }

    private fun getStringValue(key: String): String? {
        return settings.getString(key, "")
    }

    private fun saveBoolean(key: String, value: Boolean) {
        settings.edit().apply {
            putBoolean(key, value)
        }.apply()
    }

    private fun getBooleanValue(key: String): Boolean {
        return settings.getBoolean(key, false)
    }

    private fun saveInt(key: String, value: Int) {
        settings.edit().apply {
            putInt(key, value)
        }.apply()
    }

    private fun getIntValue(key: String): Int {
        return settings.getInt(key, 0)
    }

    fun removeValue(key: String) {
        settings.edit().apply {
            remove(key)
        }.apply()
    }
}