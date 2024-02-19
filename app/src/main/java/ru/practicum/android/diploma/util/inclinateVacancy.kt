package ru.practicum.android.diploma.util

fun inclinateVacancy(vacanciesAmount: Int): String {
    val preLastDigit = vacanciesAmount % 100
    if (preLastDigit in 5..20) return "$vacanciesAmount вакансий"
    return when (preLastDigit % 10) {
        1 -> "$vacanciesAmount вакансия"
        in (2..4) -> "$vacanciesAmount вакансии"
        else -> "$vacanciesAmount вакансий"
    }
}
