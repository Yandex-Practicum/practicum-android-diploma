package ru.practicum.android.diploma.domain.filter.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositorySalaryTextFlow
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FilterRepositorySalaryTextFlowImpl(
    private val sharedPreferences: FiltersLocalStorage
) : FilterRepositorySalaryTextFlow {

    private val salaryTextFlow: MutableStateFlow<SalaryTextShared?> =
        MutableStateFlow(sharedPreferences.loadSalaryTextState())

    override fun setSalaryTextFlow(salary: SalaryTextShared?) {
        salaryTextFlow.value = salary
        sharedPreferences.saveSalaryTextState(salary)
    }

    override fun getSalaryTextFlow(): StateFlow<SalaryTextShared?> = salaryTextFlow
}
