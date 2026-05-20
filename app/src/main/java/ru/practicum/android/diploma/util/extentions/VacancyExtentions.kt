package ru.practicum.android.diploma.util.extentions

import ru.practicum.android.diploma.domain.models.Vacancy

fun Vacancy.formatDescription(): String {
    return buildString {
        append(name)
        city?.let {
            append(
                ", $city"
            )
        }
    }.trim()
}
