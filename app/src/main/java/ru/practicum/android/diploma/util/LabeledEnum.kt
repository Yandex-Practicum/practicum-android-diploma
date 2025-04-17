package ru.practicum.android.diploma.util

import androidx.annotation.StringRes

interface LabeledEnum {
    @get:StringRes
    val labelResId: Int
}
