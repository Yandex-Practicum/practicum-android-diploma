package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.ui.details.DetailsViewModel

object SalaryFormatter {
    fun format(salary: String) =
        salary
            .reversed()
            .chunked(DetailsViewModel.sizeOfMoneyPart)
            .reversed()
            .joinToString(" ") {
                it.reversed()
            }
}
