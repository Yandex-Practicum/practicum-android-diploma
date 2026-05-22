package ru.practicum.android.diploma.util.extentions

fun Int.formatWithSpaces(): String {
    return "%,d".format(this).replace(",", " ")
}
