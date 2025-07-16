package ru.practicum.android.diploma.search.presenter.util

object VacancyCounterFormatter {
    fun changeEnding(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "${count} вакансия"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "${count} вакансии"
            else -> "${count} вакансий"
        }
    }
}
