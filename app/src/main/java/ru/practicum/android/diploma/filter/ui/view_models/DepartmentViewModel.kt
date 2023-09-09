package ru.practicum.android.diploma.filter.ui.view_models

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    private val useCase: GetIndustriesUseCase,
    private val logger: Logger
) :  AreasViewModel(logger, filterInteractor){

    init {
        logger.log(thisName, "logg")
        viewModelScope.launch(Dispatchers.IO) {
        useCase().fold(::handleFailure,::success)
        }
    }

    override fun handleFailure(failure: Failure) {
        super.handleFailure(failure)
    }

    private fun success(list: List<Industry>){
        logger.log(thisName, "$list ")
    }

    fun initial() {
        Log.d("TAG", ": log22gin")
    }
}