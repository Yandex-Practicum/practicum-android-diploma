package ru.practicum.android.diploma.presentation.filtration.state

data class FiltrationUIState(
    val salary: Int?,
    val onlyWithSalary: Boolean? = false,
    val industryId: Int?,
    val industryName: String?
) {
    val showButtons: Boolean
        get() = salary != null || onlyWithSalary == true || industryId != null
}
