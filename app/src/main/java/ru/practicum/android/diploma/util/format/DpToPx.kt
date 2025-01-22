package ru.practicum.android.diploma.util.format

import android.content.Context

fun Context.dpToPx(dp: Int): Int = dp * resources.displayMetrics.density.toInt()
