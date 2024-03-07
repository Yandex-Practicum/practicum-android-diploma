package ru.practicum.android.diploma.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.filter.area.domain.model.Area
import ru.practicum.android.diploma.filter.domain.models.FilterType
import ru.practicum.android.diploma.filter.domain.usecase.ApplyFilterUseCase
import ru.practicum.android.diploma.filter.domain.usecase.DeleteFiltersUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetFiltersUseCase
import ru.practicum.android.diploma.filter.domain.usecase.SaveFiltersUseCase
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

class FilterViewModel(
    private val getFiltersUseCase: GetFiltersUseCase,
    private val saveFilterUseCase: SaveFiltersUseCase,
    private val deleteFiltersUseCase: DeleteFiltersUseCase,
    private val applyFilterUseCase: ApplyFilterUseCase
) : ViewModel() {
    private val _state = MutableLiveData(FilterState())
    val state: LiveData<FilterState> get() = _state

    fun init() {
        var filter = FilterState()
        getFiltersUseCase.execute().forEach {
            when (it) {
                is FilterType.Country -> {
                    filter = filter.copy(country = Country(it.id, it.name))
                }

                is FilterType.Region -> {
                    filter = filter.copy(area = Area(it.id, it.name, null))
                }

                is FilterType.Industry -> {
                    filter = filter.copy(industry = Industry(it.id, it.name))
                }

                is FilterType.Salary -> {
                    filter = filter.copy(salary = it.amount)
                }

                is FilterType.ShowWithSalaryFlag -> {
                    filter = filter.copy(isNotShowWithoutSalary = it.flag)
                }
            }
        }
        _state.postValue(filter)
    }

    fun onBtnSaveClickEvent(salary: String, isChecked: Boolean) {
        state.value?.let {
            saveFilterUseCase.execute(it.copy(salary = salary.toIntOrNull(), isNotShowWithoutSalary = isChecked))
            applyFilterUseCase.execute()
        }
    }

    fun onBtnResetClickEvent() {
        deleteFiltersUseCase.execute()
        _state.postValue(FilterState())
    }
}
