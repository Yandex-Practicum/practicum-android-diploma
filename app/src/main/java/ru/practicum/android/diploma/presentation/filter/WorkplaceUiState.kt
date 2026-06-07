package ru.practicum.android.diploma.presentation.filter

data class WorkplaceUiState(
    val countryId: String? = null,
    val countryName: String? = null,
    val regionId: String? = null,
    val regionName: String? = null,
    val initialCountryId: String? = null,
    val initialCountryName: String? = null,
    val initialRegionId: String? = null,
    val initialRegionName: String? = null
) {
    val showApplyButton: Boolean
        get() = countryId != initialCountryId ||
            countryName != initialCountryName ||
            regionId != initialRegionId ||
            regionName != initialRegionName
}
