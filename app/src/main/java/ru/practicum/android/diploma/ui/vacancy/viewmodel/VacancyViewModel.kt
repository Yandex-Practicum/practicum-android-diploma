package ru.practicum.android.diploma.ui.vacancy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.VacancyFullItemDto
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.ui.vacancy.VacancyState

class VacancyViewModel(
    private val interactor: VacancyInteractor,
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor
) : ViewModel() {

    private var currentVacancy: VacancyFullItemDto? = null
    private var currentVacancyId: String? = null

    private val vacancyScreenStateLiveData = MutableLiveData<VacancyState>()
    private val favoriteVacancyButtonStateLiveData = MutableLiveData<FavoriteVacancyButtonState>()
    private val shareLinkStateLiveData = MutableLiveData<ShareLinkState>()

    val getVacancyScreenStateLiveData: LiveData<VacancyState> = vacancyScreenStateLiveData
    val getFavoriteVacancyButtonStateLiveData: LiveData<FavoriteVacancyButtonState> = favoriteVacancyButtonStateLiveData
    val getShareLinkStateLiveData: LiveData<ShareLinkState> = shareLinkStateLiveData

    fun shareVacancy() {
        shareLinkStateLiveData.postValue(ShareLinkState(currentVacancy?.vacancyUrl))
    }

    fun insertVacancyInFavorites() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.insertFavoriteVacancy(convertToAppEntity(currentVacancy!!))
            favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsFavorite)
        }
    }

    fun deleteVacancyFromFavorites() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.deleteFavoriteVacancy(currentVacancy!!.id)
            favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsNotFavorite)
        }
    }

    fun getVacancyResources(id: String) {
        renderState(VacancyState.Loading)
        currentVacancyId = id
        viewModelScope.launch {
            interactor.getVacancyId(id).collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancy: VacancyFullItemDto?, errorMessage: String?) {
        if (vacancy != null) {
            currentVacancy = vacancy
            renderState(VacancyState.Content(currentVacancy!!))

            viewModelScope.launch {
                favoriteVacanciesInteractor.getFavoriteVacancyById(vacancy.id).collect { vacancyFromDb ->
                    favoriteVacancyButtonStateLiveData.postValue(
                        if (vacancyFromDb == null) FavoriteVacancyButtonState.VacancyIsNotFavorite
                        else FavoriteVacancyButtonState.VacancyIsFavorite
                    )
                }
            }

        } else {
            getFavoriteVacancyDetailsFromDb(currentVacancyId!!, errorMessage)
        }
    }

    private fun getFavoriteVacancyDetailsFromDb(vacancyId: String, errorMessage: String?) {
        viewModelScope.launch {
            favoriteVacanciesInteractor
                .getFavoriteVacancyById(vacancyId)
                .collect { foundedVacancy ->
                    if (foundedVacancy == null) {
                        renderErrorState(errorMessage)
                    } else {
                        favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsFavorite)
                        renderState(VacancyState.ContentWithAppEntity(foundedVacancy))
                    }
                }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
    }

    private fun renderErrorState(errorMessage: String?) {
        when (errorMessage) {
            "Network Error" -> {
                renderState(VacancyState.NetworkError)
            }

            "Bad Request" -> {
                renderState(VacancyState.BadRequest)
            }

            "Not Found" -> {
                renderState(VacancyState.Empty)
            }

            "Unknown Error" -> {
                renderState(VacancyState.ServerError)
            }

            "Server Error" -> {
                renderState(VacancyState.ServerError)
            }
        }
    }

    private fun convertToAppEntity(vacancyForConvert: VacancyFullItemDto): Vacancy {
        return Vacancy(
            id = vacancyForConvert.id,
            titleOfVacancy = vacancyForConvert.name,
            regionName = getCorrectFormOfEmployerAddress(vacancyForConvert),
            salary = getCorrectFormOfSalary(vacancyForConvert),
            employerName = vacancyForConvert.employer.name,
            employerLogoUrl = vacancyForConvert.employer.logoUrls?.original,
            experience = vacancyForConvert.experience.name,
            employmentType = vacancyForConvert.employment.name,
            scheduleType = vacancyForConvert.schedule.name,
            keySkills = getCorrectFormOfKeySkills(vacancyForConvert),
            description = vacancyForConvert.description,
            alternateUrl = vacancyForConvert.vacancyUrl
        )
    }

    private fun getCorrectFormOfEmployerAddress(item: VacancyFullItemDto): String {
        return if (item.address == null) {
            item.area.name
        } else {
            item.address.raw
        }
    }

    private fun getCorrectFormOfSalary(item: VacancyFullItemDto): String {
        val currencyCodeMapping = mapOf(
            "AZN" to "₼",
            "BYR" to "Br",
            "EUR" to "€",
            "GEL" to "₾",
            "KGS" to "⃀",
            "KZT" to "₸",
            "RUR" to "₽",
            "UAH" to "₴",
            "USD" to "$",
            "UZS" to "Soʻm",
        )
        val codeSalary = currencyCodeMapping[item.salary?.currency] ?: item.salary?.currency

        return if (item.salary == null) {
            "Зарплата не указана"
        } else if (item.salary.to == null || item.salary.from == item.salary.to) {
            "от ${item.salary.from} $codeSalary"
        } else {
            "от ${item.salary.from} $codeSalary до ${item.salary.to} $codeSalary"
        }
    }

    private fun getCorrectFormOfKeySkills(item: VacancyFullItemDto): String {
        return item.keySkills.joinToString(separator = "\n") { itemKey ->
            "• ${itemKey.name.replace(",", ",\n")}"
        }
    }

}
