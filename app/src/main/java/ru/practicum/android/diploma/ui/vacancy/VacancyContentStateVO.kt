package ru.practicum.android.diploma.ui.vacancy

sealed class VacancyContentStateVO {
    data object Base : VacancyContentStateVO()
    data object Loading : VacancyContentStateVO()
    data object Error : VacancyContentStateVO()
    data class Success(val vacancy: VacancyDetailsVO) : VacancyContentStateVO()
}
