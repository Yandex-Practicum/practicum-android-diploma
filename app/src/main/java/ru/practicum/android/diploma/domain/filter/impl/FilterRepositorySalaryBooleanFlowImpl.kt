package ru.practicum.android.diploma.domain.filter.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryBooleanFlow
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared

class FilterRepositorySalaryBooleanFlowImpl(
    private val sharedPreferences: FiltersLocalStorage
) : FilterRepositorySalaryBooleanFlow {

    private val salaryBooleanFlow: MutableStateFlow<SalaryBooleanShared?> =
        MutableStateFlow(sharedPreferences.loadSalaryBooleanState())

    override fun setSalaryBooleanFlow(salaryBoolean: SalaryBooleanShared?) {
        salaryBooleanFlow.value = salaryBoolean
        sharedPreferences.saveSalaryBooleanState(salaryBoolean)
    }

    override fun getSalaryBooleanFlow(): StateFlow<SalaryBooleanShared?> = salaryBooleanFlow
}
