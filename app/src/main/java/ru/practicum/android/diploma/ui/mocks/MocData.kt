package ru.practicum.android.diploma.ui.mocks

import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

object MocData {
    const val SALARY_FROM = 10_000
    const val SALARY_TO = 20_000
    const val VACANCY_AMOUNT = 395
    val vacancy: Vacancy = Vacancy(
        id = "5",
        name = "Android-разработчик",
        company = "Еда",
        city = "Мoсква",
        salary = Salary(SALARY_FROM, SALARY_TO, "Р"),
        logo = null
    )
    val vacancies = listOf(
        Vacancy(
            id = "1",
            name = "Senior Android Developer",
            company = "TechCorp",
            city = "Москва",
            salary = Salary(from = 300_000, to = 450_000, currency = "RUB"),
            logo = null
        ),
        Vacancy(
            id = "2",
            name = "Junior Kotlin Developer",
            company = "StartupO",
            city = "Санкт-Петербург",
            salary = Salary(from = 70_000, to = 100_000, currency = "RUB"),
            logo = null
        ),
        Vacancy(
            id = "3",
            name = "Middle Android Engineer",
            company = "GlobalFinance",
            city = null,
            salary = Salary(from = 3_000, to = 4_500, currency = "USD"),
            logo = null
        ),
        Vacancy(
            id = "4",
            name = "QA Automation (Kotlin)",
            company = "MegaRetail",
            city = "Казань",
            salary = null,
            logo = null
        ),
        Vacancy(
            id = "5",
            name = "Team Lead Android",
            company = null,
            city = "Новосибирск",
            salary = Salary(from = 500_000, to = null, currency = "RUB"),
            logo = null
        )
    )
}
