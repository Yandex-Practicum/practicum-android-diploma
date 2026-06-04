package ru.practicum.android.diploma.presentation.filter

data class WorkplaceUiState(
    val countryId: String? = null,
    val countryName: String? = null,
    val regionId: String? = null,
    val regionName: String? = null
) {
    val showApplyButton: Boolean
        get() = countryName != null || regionName != null
}
