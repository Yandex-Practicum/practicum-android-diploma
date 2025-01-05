package ru.practicum.android.diploma.ui.filter.industries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.search.state.VacancyError
import ru.practicum.android.diploma.util.debounce

class ChoiceIndustryViewModel(
    application: Application,
    private val interactor: IndustriesInteractor,
    private val interactorSharedPreference: FilterSharedPreferencesInteractor,
) : AndroidViewModel(application) {

    private val listIndustry = mutableListOf<Industry>()
    private var latestSearchText: String? = null
    private val _industriesState = MutableLiveData<IndustriesState>()
    val industriesState: LiveData<IndustriesState> get() = _industriesState

    fun showIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(
        result: List<IndustriesFullDto?>?,
        error: VacancyError?
    ) {
        if (result != null) {
            for (industry in result) {
                listIndustry.add(Industry(industry!!.id, industry.name))
                for (industries in industry.industries) {
                    listIndustry.add(Industry(industries.id, industries.name))
                }
            }
            listIndustry.toList()
            renderState(IndustriesState.FoundIndustries(listIndustry))
        }
        when (error) {
            VacancyError.NETWORK_ERROR -> renderState(IndustriesState.NetworkError)
            VacancyError.BAD_REQUEST -> renderState(IndustriesState.NothingFound)
            VacancyError.NOT_FOUND -> renderState(IndustriesState.NothingFound)
            VacancyError.UNKNOWN_ERROR -> renderState(IndustriesState.ServerError)
            VacancyError.SERVER_ERROR -> renderState(IndustriesState.ServerError)
            null -> Unit
        }
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            industrySearchDebounce(changedText)
        }
    }

    private val industrySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchIndustries(changedText)
        }

    private fun searchIndustries(searchText: String) {
        var filteredIndustries = emptyList<Industry>()
        filteredIndustries = listIndustry.filter { industry: Industry ->
            Regex(searchText, RegexOption.IGNORE_CASE).containsMatchIn(industry.name)
        }.toMutableList()

        if (filteredIndustries.isEmpty()) {
            renderState(IndustriesState.NothingFound)
        } else {
            renderState(IndustriesState.FoundIndustries(filteredIndustries))
        }
    }

    fun getFilter(): Filter? {
        return interactorSharedPreference.getFilterSharedPrefs()
    }

    fun setFilter(filterIndustry: Filter) {
        interactorSharedPreference.setFilterSharedPrefs(filterIndustry)
    }

    private fun renderState(state: IndustriesState) {
        _industriesState.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}

