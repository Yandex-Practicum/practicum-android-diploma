package ru.practicum.android.diploma.ui.vacancy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.vacancy.VacanciesInteractor
import ru.practicum.android.diploma.domain.vacancy.models.VacanciesWithPage
import ru.practicum.android.diploma.domain.vacancy.models.Vacancy
import ru.practicum.android.diploma.ui.vacancy.model.VacancySearchState
import ru.practicum.android.diploma.util.debounce

class VacancyViewModel(
    application: Application,
    private val vacancyInteractor: VacanciesInteractor,
) : AndroidViewModel(application) {
    private val vacanciesList = ArrayList<Vacancy>()
    private var currentPage = -1
    private var allPages = 0
    private var latestSearchText: String? = null

    private val onVacancySearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { searchString ->
        currentPage = -1
        allPages = 0
        startSearch(searchString)
    }

    private val stateLiveData = MutableLiveData<VacancySearchState>()
    private val mediatorVacanciesStateLiveData = MediatorLiveData<VacancySearchState>().also { liveData ->
        liveData.addSource(stateLiveData) { vacancyState ->
            liveData.value = when (vacancyState) {
                is VacancySearchState.Loading -> VacancySearchState.Loading(vacancyState.message)
                is VacancySearchState.Error -> VacancySearchState.Error(vacancyState.errorMessage)
                is VacancySearchState.Empty -> VacancySearchState.Empty(vacancyState.message)
                is VacancySearchState.Content -> VacancySearchState.Content(vacancyState.vacancies)
            }
        }
    }

    fun observeState(): LiveData<VacancySearchState> = mediatorVacanciesStateLiveData

    fun searchVacancies(searchText: String) {
        if (searchText.isNotEmpty() && latestSearchText != searchText) {
            latestSearchText = searchText
            onVacancySearchDebounce(searchText)
        }
    }

    fun nextSearch() {
        latestSearchText?.let { startSearch(it) }
    }

    private fun startSearch(text: String) {
        renderState(VacancySearchState.Loading("Поиск"))
        currentPage += 1
        if (currentPage <= allPages) {
            viewModelScope.launch {
                vacancyInteractor
                    // Добавить фильтры TODO
                    .searchVacancies(text, "", "", "", null, false, currentPage)
                    .collect { pair -> processResult(pair.first, pair.second) }
            }
        }
    }

    private fun processResult(vacancies: VacanciesWithPage?, errorMessage: String?) {
        if (vacancies != null) {
            if (vacancies.page == 0) {
                vacanciesList.clear()
            }
            vacanciesList.addAll(vacancies.items)
            currentPage = vacancies.page
            allPages = vacancies.pages
            Log.i("VACANCY_TEST", "CurrPage: $currentPage | AllPage: $allPages")
        }
        when {
            errorMessage != null -> {
                renderState(
                    VacancySearchState.Error(
                        errorMessage = errorMessage
                    )
                )
            }

            vacancies?.items?.isEmpty() == true -> {
                renderState(
                    VacancySearchState.Empty(
                        message = getApplication<Application>().getString(
                            R.string.no // Тут сообщение из strings (другая ветка) TODO
                        )
                    )
                )
            }

            else -> renderState(
                VacancySearchState.Content(vacancies = vacanciesList)
            )
        }
    }

    private fun renderState(state: VacancySearchState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 1_000L
    }
}
