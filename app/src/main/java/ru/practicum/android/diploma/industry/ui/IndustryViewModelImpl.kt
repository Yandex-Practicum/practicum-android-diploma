package ru.practicum.android.diploma.industry.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.industry.domain.api.IndustriesInteractor

class IndustryViewModelImpl(
    private val interactor: IndustriesInteractor
) : IndustryViewModel() {
    private val _query = MutableStateFlow<String>("")
    override val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow<IndustryScreenState>(IndustryScreenState.Default)
    override var state: StateFlow<IndustryScreenState> = _state.asStateFlow()

    private val _stList = MutableStateFlow<List<Industry>>(emptyList())

    init {
        getIndustries(_query.value)
    }

    private fun getIndustries(query: String){
        viewModelScope.launch {
            interactor.getIndustries(query).collect { resource ->
                when (resource){
                    is Resource.Success -> {
                        _state.value = IndustryScreenState.Content(
                            industries =  resource.data
                        )
                        _stList.value = resource.data
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {
                        _state.value = IndustryScreenState.Loading
                    }
                }
            }
        }
    }

    override fun applyFilter(industry: Industry){
        interactor.applyFilter(industry)
    }

    override fun onSearchIconClicked() {
        if (_query.value.isNotEmpty()) {
            onQueryChanged("")
        }
    }

    override fun onQueryChanged(query: String) {
        _query.value = query

        if (query.isEmpty()) {
            _state.value = IndustryScreenState.Content(_stList.value)
        }
        else {
            val filterList = _stList.value.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }
            _state.value = IndustryScreenState.Content(filterList)
        }

        _state.value.showClearButton = !query.isEmpty()
    }
}
