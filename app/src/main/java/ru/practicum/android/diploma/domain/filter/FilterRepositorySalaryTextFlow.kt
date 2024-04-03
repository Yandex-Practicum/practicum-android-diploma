package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

interface FilterRepositorySalaryTextFlow {
    fun setSalaryTextFlow(salary: SalaryTextShared?)

    fun getSalaryTextFlow(): StateFlow<SalaryTextShared?>
}
