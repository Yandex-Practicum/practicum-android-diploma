package ru.practicum.android.diploma.commonutils

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View

object Utils {
    fun doToPx(dp: Float, context: Context): Int {
        return TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
    }

    fun visibilityView(views: Array<View>? = emptyArray(), v: View) {
        if (views != null) {
            for (view in views) view.visibility = View.GONE
        }
        v.visibility = View.VISIBLE
    }

    fun outputStackTrace(tag: String, e: Throwable) {
        e.stackTrace.forEach { element ->
            Log.e(
                tag,
                "Class: ${element.className}, Method: ${element.methodName}, Line: ${element.lineNumber}"
            )
        }
    }
}
