package ru.practicum.android.diploma.util

import android.widget.TextView
import ru.practicum.android.diploma.R

fun TextView.setVacanciesCountText(count: Long) {
    this.text = String.format(
        context.getString(R.string.found),
        context.resources.getQuantityString(
            R.plurals.vacancies_count,
            count.toInt(),
            count
        )
    )
}

