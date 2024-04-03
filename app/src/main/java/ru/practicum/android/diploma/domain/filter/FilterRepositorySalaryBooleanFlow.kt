package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared

interface FilterRepositorySalaryBooleanFlow {

    fun setSalaryBooleanFlow(salary: SalaryBooleanShared?)

    fun getSalaryBooleanFlow(): StateFlow<SalaryBooleanShared?>
}
