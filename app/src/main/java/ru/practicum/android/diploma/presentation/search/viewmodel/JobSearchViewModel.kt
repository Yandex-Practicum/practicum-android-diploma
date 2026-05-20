package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class JobSearchViewModel : ViewModel() {
    private val mockVacancies = listOf(
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
            salary = Salary(from = 70_000, to = null, currency = "RUB"),
            logo = null
        ),
        Vacancy(
            id = "3",
            name = "Middle Android Engineer",
            company = "GlobalFinance",
            city = null,
            salary = Salary(from = null, to = 4_500, currency = "USD"),
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
    private val _vacancies = MutableStateFlow<List<Vacancy>>(mockVacancies)
    val vacancies: StateFlow<List<Vacancy>> = _vacancies.asStateFlow()
}
