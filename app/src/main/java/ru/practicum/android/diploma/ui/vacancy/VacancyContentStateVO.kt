package ru.practicum.android.diploma.ui.vacancy

sealed interface VacancyContentStateVO {
    data object Base : VacancyContentStateVO
    data object Loading : VacancyContentStateVO
    data object Error : VacancyContentStateVO
    data object Refresh : VacancyContentStateVO
    data class Success(val vacancy: VacancyDetailsVO) : VacancyContentStateVO
}
