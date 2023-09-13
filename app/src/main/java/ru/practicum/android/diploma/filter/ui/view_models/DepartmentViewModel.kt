package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val useCase: GetIndustriesUseCase,
    private val interactor: FilterInteractor,
    logger: Logger
) : AreasViewModel(logger) {

    private var industryList = listOf<Industry>()
    
    override fun getData() {
        super.getData()
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleSuccess(list: List<Any>) {
        super.handleSuccess(list)
        industryList = list.map { it as Industry }
    }
    
    override fun onSearchQueryChanged(text: String) {
        super.onSearchQueryChanged(text)
        val temp = industryList
        _uiState.value = UiState.Content(temp.filter {
            it.name.contains(text, true)
        })
    }
    
    fun saveIndustry(industry: Industry) {
        log(thisName, "saveRegion(region: String)")
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveIndustry(BaseFilterViewModel.FILTER_KEY, industry)
        }
    }
}