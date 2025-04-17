package ru.practicum.android.diploma.util.extensions

import android.content.Context
import ru.practicum.android.diploma.util.LabeledEnum

fun LabeledEnum.toLabel(context: Context): String = context.getString(labelResId)
