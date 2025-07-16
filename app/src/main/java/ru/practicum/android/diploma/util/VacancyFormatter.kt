package ru.practicum.android.diploma.util

object VacancyFormatter {

    fun changeEnding(count: Int): String {
        return when {
            count % 10 == 1 && count % 100 != 11 -> "${count} вакансия"
            count % 10 in 2..4 && count % 100 !in 12..14 -> "${count} вакансии"
            else -> "${count} вакансий"
        }
    }

    fun formatSalary(from: Int?, to: Int?, currency: String?): String {
        if (from == null && to == null) return "Зарплата не указана"
        val formattedFrom = from?.let { "%,d".format(it).replace(',', ' ') }
        val formattedTo = to?.let { "%,d".format(it).replace(',', ' ') }
        return when {
            from != null && to != null -> "от $formattedFrom до $formattedTo $currency"
            from != null -> "от $formattedFrom $currency"
            to != null -> "до $formattedTo $currency"
            else -> "Зарплата не указана"
        }
    }
}
