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
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.util.debounce

class ChoiceIndustryViewModel(
    application: Application,
    private val interactor: IndustriesInteractor,
    private val sharePrefsInteractor: FilterSharedPreferencesInteractor
) : AndroidViewModel(application) {

    val listIndustry = mutableListOf<Industries>()
    private var latestSearchText: String? = null
    private val _industriesState = MutableLiveData<IndustriesState>()
    val industriesState: LiveData<IndustriesState> get() = _industriesState

    var currentFilter: Filter = sharePrefsInteractor.getFilterSharedPrefs() ?: Filter()

    fun showIndustries() {
        viewModelScope.launch {
            interactor
                .getIndustries()
                .collect { industry ->
                    processResult(industry)
                }
        }
    }

    private fun processResult(
        result:
        List<IndustriesFullDto?>?
    ) {

        if (result != null) {
            for (industries in result) {
                listIndustry.add(Industries(industries!!.id, industries.name))
                for (industries in industries.industries) {
                    listIndustry.add(Industries(industries.id, industries.name))
                }
            }
        }
        listIndustry.toList()
        renderState(IndustriesState.FoundIndustries(listIndustry))
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
        renderState(IndustriesState.Loading)
        var filteredIndustries = emptyList<Industries>()
        filteredIndustries = listIndustry.filter { industry: Industries ->
            industry.name.contains(searchText)
        }.toMutableList()


        if (filteredIndustries.isEmpty()) {
            renderState(IndustriesState.NothingFound)
        } else {
            renderState(IndustriesState.FoundIndustries(filteredIndustries))
        }
    }

    private fun renderState(state: IndustriesState) {
        _industriesState.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}

