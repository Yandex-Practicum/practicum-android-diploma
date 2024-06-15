package ru.practicum.android.diploma.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.favorite.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDetailsViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
    private val dictionaryInteractor: DictionaryInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private var sharingInteractor: SharingInteractor
) : ViewModel() {

    private var currencySymbol: String? = null
    private var isFavorite: Boolean = false

    private val _stateLiveData = MutableLiveData<VacancyDetailsState>()
    val stateLiveData: LiveData<VacancyDetailsState> get() = _stateLiveData

    fun fetchVacancyDetails(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                when (result) {
                    is VacancyDetailStatus.Loading -> renderState(VacancyDetailsState.Loading)

                    is VacancyDetailStatus.Content -> {
                        isFavorite = isVacancyFavorite(vacancyId)
                        currencySymbol = currencyDictionary[result.data?.salary?.currency]?.abbr ?: ""
                        renderState(VacancyDetailsState.Content(result.data!!, currencySymbol!!, isFavorite))
                    }

                    is VacancyDetailStatus.NoConnection -> {
                        isFavorite = isVacancyFavorite(vacancyId)
                        if (isFavorite) {
                            getVacancyFromDb(vacancyId)
                        } else {
                            renderState(VacancyDetailsState.NotInDb)
                        }
                    }

                    is VacancyDetailStatus.Error -> {
                        renderState(VacancyDetailsState.Error)
                    }
                }
            }
        }
    }

    fun addToFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            val state = isVacancyFavorite(vacancy.id)
            if (state) {
                favoritesInteractor.removeVacancyFromFavorites(vacancy)
                renderState(
                    VacancyDetailsState.Content(
                        vacancy = vacancy,
                        currencySymbol = currencySymbol!!,
                        isFavorite = false
                    )
                )
                isFavorite = false
            } else {
                favoritesInteractor.addVacancyToFavorites(vacancy)
                renderState(
                    VacancyDetailsState.Content(
                        vacancy = vacancy,
                        currencySymbol = currencySymbol!!,
                        isFavorite = true
                    )
                )
                isFavorite = true
            }
        }
    }

    fun getVacancyFromDb(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vacancyFromDb = favoritesInteractor.getVacancyById(vacancyId)
            _stateLiveData.postValue(
                VacancyDetailsState.Content(
                    vacancy = vacancyFromDb,
                    currencySymbol = currencySymbol.toString(),
                    isFavorite = true
                )
            )
        }
    }

    suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoritesInteractor.isVacancyFavorite(vacancyId)
    }

    private fun renderState(state: VacancyDetailsState) {
        _stateLiveData.value = state
    }

    fun shareApp(vacancyUrl: String) {
        viewModelScope.launch {
            sharingInteractor.shareApp(vacancyUrl)
        }
    }

    fun phoneCall(phone: String) {
        viewModelScope.launch {
            sharingInteractor.phoneCall(phone)
        }
    }

    fun eMail(email: String) {
        viewModelScope.launch {
            sharingInteractor.eMail(email)
        }
    }
}
