package ru.practicum.android.diploma.region.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.region.domain.api.RegionInteractor
import ru.practicum.android.diploma.region.domain.models.RegionItem

class RegionViewModelImpl(
    private val interactor: RegionInteractor
) : RegionViewModel() {
    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()

    private val _state = MutableStateFlow<RegionScreenState>(RegionScreenState.Loading)
    override val state: StateFlow<RegionScreenState> = _state.asStateFlow()

    private var allRegions: List<RegionItem> = emptyList()

    init {
        viewModelScope.launch {
            interactor.getRegions().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = RegionScreenState.Loading
                    is Resource.Success -> {
                        allRegions = resource.data
                        _state.value = buildContent(_query.value)
                    }

                    is Resource.Error -> _state.value = RegionScreenState.Error(
                        if (resource.code == ResultCode.NO_INTERNET) {
                            RegionError.NO_INTERNET
                        } else {
                            RegionError.ERROR
                        }
                    )
                }
            }
        }
    }

    override fun onQueryChanged(query: String) {
        _query.value = query
        val current = _state.value
        if (current !is RegionScreenState.Loading && current !is RegionScreenState.Error) {
            _state.value = buildContent(query)
        }
    }

    override fun onSearchIconClicked() {
        if (_query.value.isNotEmpty()) {
            onQueryChanged("")
        }
    }

    private fun buildContent(query: String): RegionScreenState {
        val filtered = if (query.isEmpty()) {
            allRegions
        } else {
            allRegions.filter { it.region.name.contains(query, ignoreCase = true) }
        }
        return if (filtered.isEmpty()) RegionScreenState.Empty else RegionScreenState.Content(filtered)
    }
}
