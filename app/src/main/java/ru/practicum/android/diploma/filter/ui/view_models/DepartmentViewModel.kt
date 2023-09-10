package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val useCase: GetIndustriesUseCase,
    private val interactor: FilterInteractor,
    logger: Logger
) : AreasViewModel(logger) {

    override fun getData() {
        super.getData()
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure, ::handleSuccess)
        }

    }

    fun saveIndustry(industry: Industry) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveIndustry(FILTER_KEY, industry)
        }
    }

}