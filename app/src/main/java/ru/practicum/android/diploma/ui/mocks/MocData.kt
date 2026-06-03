package ru.practicum.android.diploma.ui.mocks

import ru.practicum.android.diploma.domain.models.FilterArea
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
    val countries: List<FilterArea> = listOf(
        FilterArea(
            id = 113,
            name = "Россия",
            parentId = null,
            areas = listOf(
                FilterArea(id = 1, name = "Москва", parentId = 113, areas = emptyList()),
                FilterArea(id = 2, name = "Санкт-Петербург", parentId = 113, areas = emptyList()),
                FilterArea(id = 3, name = "Краснодарский край", parentId = 113, areas = emptyList())
            )
        ),
        FilterArea(
            id = 114,
            name = "Беларусь",
            parentId = null,
            areas = listOf(
                FilterArea(id = 4, name = "Минск", parentId = 114, areas = emptyList()),
                FilterArea(id = 5, name = "Брест", parentId = 114, areas = emptyList())
            )
        ),
        FilterArea(
            id = 115,
            name = "Казахстан",
            parentId = null,
            areas = listOf(
                FilterArea(id = 6, name = "Астана", parentId = 115, areas = emptyList()),
                FilterArea(id = 7, name = "Алматы", parentId = 115, areas = emptyList())
            )
        ),
        FilterArea(
            id = 116,
            name = "Армения",
            parentId = null,
            areas = listOf(
                FilterArea(id = 8, name = "Ереван", parentId = 116, areas = emptyList()),
                FilterArea(id = 9, name = "Гюмри", parentId = 116, areas = emptyList())
            )
        ),
        FilterArea(
            id = 117,
            name = "Грузия",
            parentId = null,
            areas = listOf(
                FilterArea(id = 10, name = "Тбилиси", parentId = 117, areas = emptyList()),
                FilterArea(id = 11, name = "Батуми", parentId = 117, areas = emptyList())
            )
        ),
        FilterArea(
            id = 118,
            name = "Узбекистан",
            parentId = null,
            areas = listOf(
                FilterArea(id = 12, name = "Ташкент", parentId = 118, areas = emptyList()),
                FilterArea(id = 13, name = "Самарканд", parentId = 118, areas = emptyList())
            )
        ),
        FilterArea(
            id = 119,
            name = "Кыргызстан",
            parentId = null,
            areas = listOf(
                FilterArea(id = 14, name = "Бишкек", parentId = 119, areas = emptyList()),
                FilterArea(id = 15, name = "Ош", parentId = 119, areas = emptyList())
            )
        )
    )
}
