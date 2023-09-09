package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val useCase: GetIndustriesUseCase,
    logger: Logger
) :  AreasViewModel(logger){

    override fun getData() {
        super.getData()
        viewModelScope.launch(Dispatchers.IO) {
            useCase().fold(::handleFailure,::handleSuccess)
        }
    }

}