package com.test.test.util

import android.content.Context
import androidx.annotation.StringRes

class StringResource(@StringRes val resId: Int, vararg val args: Any) {
    fun getText(context: Context): String {
        return context.getString(resId, args)
    }
}
