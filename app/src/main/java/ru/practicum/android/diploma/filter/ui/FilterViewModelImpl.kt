package ru.practicum.android.diploma.filter.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.search.ui.SearchViewState

class FilterViewModelImpl(val interactor: FilterInteractor) : FilterViewModel(){
    private val _state = MutableStateFlow<Filters>(Filters())
    override var state: StateFlow<Filters> = _state.asStateFlow()

    override fun onQueryChanged(query: String) {
        TODO("Not yet implemented")
    }

    override fun onFocusChanged(focus: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onResetArea() {
        interactor.resetArea()
    }

    override fun onResetIndustry() {
        interactor.resetIndustry()
    }

    override fun onToggleSalary() {
        TODO("Not yet implemented")
    }

}
