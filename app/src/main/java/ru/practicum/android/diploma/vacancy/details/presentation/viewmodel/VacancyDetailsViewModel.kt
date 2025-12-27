package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.search.domain.model.Address
import ru.practicum.android.diploma.search.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Employment
import ru.practicum.android.diploma.search.domain.model.Experience
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Schedule
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor
) : ViewModel() {

    private val _vacancy = MutableStateFlow<VacancyDetail?>(null)
    val vacancy: StateFlow<VacancyDetail?> = _vacancy

    // временное решение для верстки
    init {
        _vacancy.value = createFakeVacancy()
    }

    // временная заглушка для верстки
    private fun createFakeVacancy(): VacancyDetail =
        VacancyDetail(
            id = "123",
            name = "Андроид-разработчик",
            description = "Разработка Android-приложений на Kotlin",
            salary = Salary(
                from = 150_000,
                to = 250_000,
                currency = "RUB"
            ),
            address = Address(
                city = "Москва",
                street = "Тверская",
                building = "10",
                fullAddress = "Москва, Тверская 10"
            ),
            experience = Experience("3-6", "3–6 лет"),
//            schedule = null,
            schedule = Schedule("fullDay", "Работа в офисе пон-пят"),
//            employment = null,
            employment = Employment("full", "Полная занятость"), // ?
            contacts = Contacts(
                id = "1",
                name = "HR",
                email = "hr@test.ru",
                phone = listOf("+7 999 123-45-67")
            ),
            employer = Employer(
                id = "777",
                name = "Super Company",
                logo = "https://example.com/logo.png"
            ),
            area = FilterArea(
                id = 1,
                name = "Москва",
                parentId = null,
                areas = emptyList()
            ),
            skills = listOf("Kotlin", "Compose", "Coroutines"),
            url = "https://hh.ru/vacancy/123",
            industry = FilterIndustry(
                id = 10,
                name = "IT"
            )
        )

    fun getShareUrl(): String? {
        return _vacancy.value?.url
    }
}
