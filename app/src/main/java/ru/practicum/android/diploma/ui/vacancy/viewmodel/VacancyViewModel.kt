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
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.ui.vacancy.VacancyState
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val interactor: VacancyInteractor,
    private val favoriteVacanciesInteractor: FavoriteVacanciesInteractor
) : ViewModel() {

    private var currentVacancy: Vacancy? = null
    private var currentVacancyId: String? = null

    private val vacancyScreenStateLiveData = MutableLiveData<VacancyState>()
    private val favoriteVacancyButtonStateLiveData = MutableLiveData<FavoriteVacancyButtonState>()
    private val shareLinkStateLiveData = MutableLiveData<ShareLinkState>()
    private var listVacancy: VacancyFullItemDto? = null
    val getVacancyScreenStateLiveData: LiveData<VacancyState> = vacancyScreenStateLiveData
    val getFavoriteVacancyButtonStateLiveData: LiveData<FavoriteVacancyButtonState> = favoriteVacancyButtonStateLiveData
    val getShareLinkStateLiveData: LiveData<ShareLinkState> = shareLinkStateLiveData

    fun shareVacancy() {
        shareLinkStateLiveData.postValue(ShareLinkState(currentVacancy?.alternateUrl))
    }

    fun insertVacancyInFavorites() {
        viewModelScope.launch {
            favoriteVacanciesInteractor.insertFavoriteVacancy(currentVacancy!!)
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

        viewModelScope.launch {
            favoriteVacanciesInteractor.getFavoriteVacancyById(id).collect { vacancyFromDb ->
                if (vacancyFromDb == null) {
                    viewModelScope.launch {
                        interactor.getVacancyId(id).collect { resource ->
                            processResult(resource)
                        }
                    }
                    FavoriteVacancyButtonState.VacancyIsNotFavorite
                } else {
                    currentVacancy = vacancyFromDb
                    getFavoriteVacancyDetailsFromDb(id)
                    FavoriteVacancyButtonState.VacancyIsFavorite
                }
            }
        }
    }

    private fun processResult(resource: Resource<VacancyFullItemDto>) {
        when (resource) {
            is Resource.Success -> {
                listVacancy = resource.data
                currentVacancy = convertToAppEntity(resource.data)
                renderState(VacancyState.Content(listVacancy!!))
            }

            is Resource.Error -> {
                when (resource.message) {
                    VacancyError.NETWORK_ERROR -> {
                        renderState(VacancyState.NetworkError)
                    }

                    VacancyError.BAD_REQUEST -> {
                        renderState(VacancyState.BadRequest)
                    }

                    VacancyError.NOT_FOUND -> {
                        renderState(VacancyState.Empty)
                    }

                    VacancyError.UNKNOWN_ERROR, VacancyError.SERVER_ERROR -> {
                        renderState(VacancyState.ServerError)
                    }
                }
            }
        }
    }

    private fun getFavoriteVacancyDetailsFromDb(
        vacancyId: String
    ) {
        viewModelScope.launch {
            favoriteVacanciesInteractor
                .getFavoriteVacancyById(vacancyId)
                .collect { foundedVacancy ->
                    currentVacancy = foundedVacancy
                    favoriteVacancyButtonStateLiveData.postValue(FavoriteVacancyButtonState.VacancyIsFavorite)
                    renderState(VacancyState.ContentWithAppEntity(foundedVacancy!!))
                }
        }
    }

    private fun renderState(state: VacancyState) {
        vacancyScreenStateLiveData.postValue(state)
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

    private fun getCorrectFormOfSalary(item: VacancyFullItemDto)
        : String {
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
