package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.ui.details.DetailsViewModel

object SalaryFormatter {
    fun format(salary: String) =
        salary
            .reversed()
            .chunked(DetailsViewModel.SIZE_OF_MONEY_PART)
            .reversed()
            .joinToString(" ") {
                it.reversed()
            }
}
