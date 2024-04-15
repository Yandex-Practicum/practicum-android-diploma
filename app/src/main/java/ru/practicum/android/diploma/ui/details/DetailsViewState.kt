package ru.practicum.android.diploma.ui.details

sealed interface DetailsViewState {

    data object Loading : DetailsViewState

    data object Error : DetailsViewState

    data object ToastPermissionDenied : DetailsViewState

    data class Content(
        val name: String,
        val salary: String?,
        val companyLogo: String?,
        val companyName: String?,
        val city: String?,
        val fullAddress: String?,
        val areaName: String,
        val experience: String?,
        val employment: String?,
        val description: String,
        val contactName: String?,
        val contactEmail: String?,
        val contactsPhones: List<String>?,
        val keySkills: String
    ) : DetailsViewState

    data class IsVacancyFavorite(
        val isFavorite: Boolean
    ) : DetailsViewState
}
