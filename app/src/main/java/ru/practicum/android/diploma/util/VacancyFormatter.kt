package ru.practicum.android.diploma.util

object VacancyFormatter {

    private const val TEN = 10
    private const val ELEVEN = 11
    private const val TWELVE = 12
    private const val FOURTEEN = 14
    private const val HUNDRED = 100
    private const val TWO = 2
    private const val FOUR = 4

    fun changeEnding(count: Int): String {
        return when {
            count % TEN == 1 && count % HUNDRED != ELEVEN -> "$count вакансия"
            count % TEN in TWO..FOUR && count % HUNDRED !in TWELVE..FOURTEEN -> "$count вакансии"
            else -> "$count вакансий"
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
