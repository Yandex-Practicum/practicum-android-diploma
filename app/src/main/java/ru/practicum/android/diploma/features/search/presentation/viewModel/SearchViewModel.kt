package ru.practicum.android.diploma.features.search.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.App
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.features.search.domain.model.Filter
import ru.practicum.android.diploma.features.search.domain.model.QueryError
import ru.practicum.android.diploma.features.search.domain.model.ResponseModel
import ru.practicum.android.diploma.features.search.domain.repository.SearchVacancyRepository
import ru.practicum.android.diploma.features.search.presentation.SearchScreenState
import ru.practicum.android.diploma.features.search.presentation.SearchingCleanerState
import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel

class SearchViewModel(
    private val vacancyRepository: SearchVacancyRepository,
    private val context: Context
) : ViewModel() {
    private var previousSearchingRequest = ""
    private var searchJob: Job? = null

    private val searchingCleanerState = MutableLiveData<SearchingCleanerState>()
    fun searchingCleanerStateObserve(): LiveData<SearchingCleanerState> = searchingCleanerState

    private val searchScreenState = MutableLiveData<SearchScreenState>()
    fun searchScreenStateObserve(): LiveData<SearchScreenState> = searchScreenState

    private val vacancyFeed = MutableLiveData<ArrayList<VacancyScreenModel>>()
    fun vacancyFeedObserve(): LiveData<ArrayList<VacancyScreenModel>> = vacancyFeed

    private val chipMessage = MutableLiveData<String>()
    fun chipMessageObserve(): LiveData<String> = chipMessage

    fun onSearchingRequestChange(text: String) {
        val cleanerState =
            if (text.isEmpty()) SearchingCleanerState.INACTIVE else SearchingCleanerState.ACTIVE
        searchingCleanerState.postValue(cleanerState)

        if (text == previousSearchingRequest || text.isEmpty()) return
        previousSearchingRequest = text

        runSearching(App.CLICK_DEBOUNCE_DELAY_MILLIS,text, Filter(""))
    }

    fun onSearchingFieldClean() {
        searchScreenState.postValue(SearchScreenState.NEWBORN)
        searchingCleanerState.postValue(SearchingCleanerState.INACTIVE)
    }

    private fun runSearching(delay: Long, parameter: String, filter: Filter) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(delay)
            searchScreenState.postValue(SearchScreenState.SEARCHING)
            handleSearchingResponse(
                vacancyRepository.loadVacancies(parameter, filter)
            )
            searchJob = null
        }
    }
    private fun handleSearchingResponse(response: ResponseModel) {
        if (response.resultVacancyList.isNotEmpty()) {
            vacancyFeed.postValue(response.resultVacancyList)
        }
        var screenState = SearchScreenState.RESPONSE_RESULTS
        var chipMessage = ""
        when (response.error) {
            QueryError.NO_ERRORS -> {
                chipMessage =
                    context.getString(R.string.found) + " " + modifyToStringVacancyQuantity(
                        response.resultVacancyList.size
                    )
            }
            QueryError.NO_RESULTS -> {
                screenState = SearchScreenState.EMPTY_RESULT
                chipMessage = context.getString(R.string.no_such_vacancies)
            }
            QueryError.SOMETHING_WENT_WRONG -> {
                screenState = SearchScreenState.SOMETHING_WENT_WRONG
                chipMessage = context.getString(R.string.something_went_wrong)
            }
        }
        searchScreenState.postValue(screenState)
        this.chipMessage.postValue(chipMessage)

    }

    private fun modifyToStringVacancyQuantity(quantity: Int): String {
        val ending = when (quantity.toString().takeLast(1).toInt()) {
            1 -> context.getString(R.string.vacancy_one)
            2, 3, 4 -> context.getString(R.string.vacancy_2_3_4)
            else -> context.getString(R.string.vacancy_many)
        }
        return "$quantity $ending"
    }
}