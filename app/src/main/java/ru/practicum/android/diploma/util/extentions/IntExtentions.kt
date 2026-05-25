package ru.practicum.android.diploma.util.extentions

import java.util.Locale

fun Int.formatWithSpaces(): String {
    return String.format(Locale.US, "%,d", this).replace(",", " ")
}
