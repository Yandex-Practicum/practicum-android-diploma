package ru.practicum.android.diploma.filter.ui.view_models

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetIndustriesUseCase
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.model.UiState
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

    fun saveIndustry(industry: Industry) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveIndustry(FILTER_KEY, industry)
        }
    }


    override fun onSearchQueryChanged(text: String) {
        super.onSearchQueryChanged(text)
        val temp = industryList
      _uiState.value = UiState.Content(temp.filter {     Log.d("TAG", " ${it.name}:  and ${it.industries}")
          it.name.contains(text, true)

      })

    }
}