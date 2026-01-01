package ru.practicum.android.diploma.vacancy.details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
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
import ru.practicum.android.diploma.vacancy.details.data.VacancyDetailToFavoriteMapper
import ru.practicum.android.diploma.vacancy.details.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.details.domain.model.Result
import ru.practicum.android.diploma.vacancy.details.domain.model.VacancyDetailsSource

class VacancyDetailsViewModel(
    private val interactor: VacancyDetailsInteractor,
    private val favoritesInteractor: FavoritesVacanciesInteractor
) : ViewModel() {

    private val vacancyDetailToFavoriteMapper = VacancyDetailToFavoriteMapper()
    private val _vacancy = MutableStateFlow<VacancyDetail?>(null)
    val vacancy: StateFlow<VacancyDetail?> = _vacancy.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<Throwable?>(null)
    val error: StateFlow<Throwable?> = _error.asStateFlow()

    private fun checkFavoriteStatus() {
        val vacancyId = _vacancy.value?.id ?: return
        viewModelScope.launch {
            _isFavorite.value = favoritesInteractor.isFavorite(vacancyId)
        }
    }

    fun toggleFavorite() {
        val currentVacancy = _vacancy.value ?: return
        viewModelScope.launch {
            val currentIsFavorite = _isFavorite.value

            if (currentIsFavorite) {
                favoritesInteractor.removeFromFavorites(currentVacancy.id)
                _isFavorite.value = false
            } else {
                val favoriteEntity = with(vacancyDetailToFavoriteMapper) {
                    currentVacancy.toFavoriteVacancyEntity()
                }
                favoritesInteractor.addToFavorites(favoriteEntity)
                _isFavorite.value = true
            }
        }
    }

    fun getShareUrl(): String? {
        return _vacancy.value?.url
    }

    fun loadVacancy(id: String, source: VacancyDetailsSource) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            interactor.getVacancyById(id, source)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _vacancy.value = result.data
                            _isLoading.value = false
                            checkFavoriteStatus()
                        }

                        is Result.Error -> {
                            _isLoading.value = false

                            // Если переход из избранного и вакансия не найдена (404), удаляем из БД
                            if (source == VacancyDetailsSource.FAVORITES &&
                                isNotFoundError(result.throwable)
                            ) {
                                favoritesInteractor.removeFromFavorites(id)
                            }

                            _error.value = result.throwable
                        }
                    }
                }
        }
    }
}

private const val HTTP_NOT_FOUND = 404
private fun isNotFoundError(throwable: Throwable?): Boolean {
    val httpException = when (throwable) {
        is HttpException -> throwable
        else -> throwable?.cause as? HttpException
    }

    return throwable?.message == "404 Not Found" ||
        httpException?.code() == HTTP_NOT_FOUND
}

object VacancyFakeFactory {

    private const val VACANCY_ID = "123"
    private const val EMPLOYER_ID = "777"
    private const val CONTACT_ID = "1"
    private const val SALARY_FROM = 150_000
    private const val SALARY_TO = 250_000
    private const val AREA_ID = 1
    private const val INDUSTRY_ID = 10

    fun create(): VacancyDetail =
        VacancyDetail(
            id = VACANCY_ID,
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

    private fun fakeSalary() = Salary(SALARY_FROM, SALARY_TO, "RUB")

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
        id = CONTACT_ID,
        name = "HR",
        email = "hr@test.ru",
        phone = listOf("+7 999 123-45-67", "+7 495-123-45-67")
    )

    private fun fakeEmployer() = Employer(
        id = EMPLOYER_ID,
        name = "Super Company",
        logo = "https://example.com/logo.png"
    )

    private fun fakeArea() = FilterArea(
        id = AREA_ID,
        name = "Москва",
        parentId = null,
        areas = emptyList()
    )

    private fun fakeSkills() = listOf("Kotlin", "Compose", "Coroutines")

    private fun fakeIndustry() = FilterIndustry(INDUSTRY_ID, "IT")
}
