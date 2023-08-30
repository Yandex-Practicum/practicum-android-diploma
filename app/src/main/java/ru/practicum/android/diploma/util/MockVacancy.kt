package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.search.domain.models.Vacancy
import kotlin.random.Random

fun getMockVacancy(): Vacancy {
    return Vacancy(
        id = Random.nextLong(),
        iconUri = "https://cs8.pikabu.ru/avatars/2404/x2404686-1153049926.png",
        title =  titles.random(),
        company = companies.random(),
        salary = Random.nextInt(200000, 400000).toString(),
        area = areas.random(),
        date = System.currentTimeMillis()
        )
}
private val companies = listOf("Ynadex", "Google", "Tinkoff", "Sber", "Gazprom")
private val titles = listOf("android-developer", "python-dev","devOps", "manager","pilot")
private val areas = listOf("Moscow", "Saint-Petersburg", "Kazan", "Novosibirsk", "Vladivostok")

