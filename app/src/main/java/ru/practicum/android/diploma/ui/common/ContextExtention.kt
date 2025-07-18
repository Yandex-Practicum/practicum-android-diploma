package ru.practicum.android.diploma.ui.common

import android.content.res.Resources
import android.util.TypedValue

val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.dp: Int
    get() = toFloat().dp.toInt()
