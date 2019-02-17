package com.winsonmac.core.util

import android.content.Context
import android.graphics.Typeface


/* =========================================================================================== */
/*  Supply among methods for using fonts in assets or resources                                 */
/* =========================================================================================== */

object FontUtil {

    const val FONT_ROBOTO_BLACK = "Roboto-Black.ttf"
    const val FONT_ROBOTO_BOLD = "Roboto-Bold.ttf"
    const val FONT_ROBOTO_ITALIC = "Roboto-Italic.ttf"
    const val FONT_ROBOTO_LIGHT = "Roboto-Light.ttf"
    const val FONT_ROBOTO_MEDIUM = "Roboto-Medium.ttf"
    const val FONT_ROBOTO_REGULAR = "Roboto-Regular.ttf"
    const val FONT_ROBOTO_THIN = "Roboto-Thin.ttf"


    /**
     * Load fonts by name from assets folder
     *
     * @param context  Context for retrieve fonts resources
     * @param fontName The fonts name for loading
     * @return typeface
     */
    fun loadFont(context: Context, fontName: String): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/$fontName")
    }
}
