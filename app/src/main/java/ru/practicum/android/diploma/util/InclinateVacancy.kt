package ru.practicum.android.diploma.util

fun inclinateVacancy(vacanciesAmount: Int): String {
    val preLastDigit = vacanciesAmount % HUNDRED
    if (preLastDigit in FIVE..TWENTY) return "$vacanciesAmount вакансий"
    return when (preLastDigit % TEN) {
        ONE -> "$vacanciesAmount вакансия"
        in TWO..FORE -> "$vacanciesAmount вакансии"
        else -> "$vacanciesAmount вакансий"
    }
}

