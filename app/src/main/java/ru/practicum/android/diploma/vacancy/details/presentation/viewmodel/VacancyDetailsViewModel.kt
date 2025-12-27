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
        _vacancy.value = VacancyFakeFactory.create()
    }

    fun getShareUrl(): String? {
        return _vacancy.value?.url
    }
}

object VacancyFakeFactory {

    fun create(): VacancyDetail =
        VacancyDetail(
            id = "123",
            name = "Андроид-разработчик",
            description = "Разработка Android-приложений на Kotlin",
            salary = fakeSalary(),
            address = fakeAddress(),
            experience = fakeExperience(),
            schedule = fakeSchedule(),
            employment = fakeEmployment(),
            contacts = fakeContacts(),
            employer = fakeEmployer(),
            area = fakeArea(),
            skills = fakeSkills(),
            url = "https://hh.ru/vacancy/123",
            industry = fakeIndustry()
        )

    private fun fakeSalary() = Salary(150_000, 250_000, "RUB")

    private fun fakeAddress() = Address(
        city = "Москва",
        street = "Тверская",
        building = "10",
        fullAddress = "Москва, Тверская 10"
    )

    private fun fakeExperience() = Experience("3-6", "3–6 лет")

    private fun fakeSchedule() = Schedule("fullDay", "Работа в офисе пон-пят")

    private fun fakeEmployment() = Employment("full", "Полная занятость")

    private fun fakeContacts() = Contacts(
        id = "1",
        name = "HR",
        email = "hr@test.ru",
        phone = listOf("+7 999 123-45-67")
    )

    private fun fakeEmployer() = Employer(
        id = "777",
        name = "Super Company",
        logo = "https://example.com/logo.png"
    )

    private fun fakeArea() = FilterArea(
        id = 1,
        name = "Москва",
        parentId = null,
        areas = emptyList()
    )

    private fun fakeSkills() = listOf("Kotlin", "Compose", "Coroutines")

    private fun fakeIndustry() = FilterIndustry(10, "IT")
}
