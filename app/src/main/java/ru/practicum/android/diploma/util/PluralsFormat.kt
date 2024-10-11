package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R

fun vacanciesPluralsFormat(size: Int, context: Context): String =
    context.resources.getQuantityString(R.plurals.plurals_vacancies, size)
