package ru.practicum.android.diploma.util

import java.util.Locale

fun Int?.formatWithThousandsSeparator(): String {
    return this.let {
        String.format(Locale.US, "%,d", it)
            .replace(',', ' ')
    }
}
