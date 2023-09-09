package ru.practicum.android.diploma.filter.ui.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.di.annotations.NewResponse
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.search.domain.models.Vacancies
import ru.practicum.android.diploma.search.ui.models.SearchUiState
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(

    private val useCase: GetIndustriesUseCase,
    private val logger: Logger
) :  AreasViewModel(logger){
    override fun getData() {
        super.getData()
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure,::handleSuccess)
        }
    }


    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)

    }

    private fun handleSuccess(list: List<Industry>) {


    }



}