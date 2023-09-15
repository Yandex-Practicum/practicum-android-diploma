package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val useCase: GetIndustriesUseCase,
    logger: Logger
) : AreasViewModel(logger) {

    private var industryList = listOf<Industry>()
    
    override fun getData(data: SelectedFilter) {
        selectedFilter = data
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleSuccess(list: List<Any>) {
        super.handleSuccess(list)
        industryList = list.map { it as Industry }
    }
    
    override fun onSearchQueryChanged(text: String) {
        val temp = industryList
        _uiState.value = UiState.Content(temp.filter {
            it.name.contains(text, true)
        })
    }
    
    fun saveIndustry(industry: Industry) {
        log(thisName, "saveIndustry($industry: Industry)")
        selectedFilter = selectedFilter.copy(industry = industry)
    }
}