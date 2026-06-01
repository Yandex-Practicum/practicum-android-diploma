package ru.practicum.android.diploma.presentation.filtration.action

import ru.practicum.android.diploma.domain.models.FilterIndustry

sealed interface FiltrationAction {
    data class SalaryChanged(val salary: Int) : FiltrationAction
    data object SalaryCleared : FiltrationAction
    data class OnlyWithSalaryChanged(val checked: Boolean) : FiltrationAction
    data object ApplyClicked : FiltrationAction
    data object ResetClicked : FiltrationAction
    data object IndustryClicked : FiltrationAction
    data object BackClicked : FiltrationAction
    data object CloseScreen : FiltrationAction
    data class IndustryChanged(val industry: FilterIndustry) : FiltrationAction
}
