package ru.practicum.android.diploma.util

import android.content.Context
import android.util.TypedValue

fun pxToDp(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}
