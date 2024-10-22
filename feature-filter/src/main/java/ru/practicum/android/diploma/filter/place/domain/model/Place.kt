package ru.practicum.android.diploma.filter.place.domain.model

internal data class Place(
    val idCountry: String?,
    val nameCountry: String?,
    val idRegion: String?,
    val nameRegion: String?,
) {
    companion object {
        fun emptyPlace(): Place {
            return Place(
                idCountry = null,
                nameCountry = null,
                idRegion = null,
                nameRegion = null
            )
        }
    }
}

internal fun Place.resetRegion(): Place {
    return this.copy(idRegion = null, nameRegion = null)
}

internal fun Place.setCountry(country: Country): Place {
    return this.copy(idCountry = country.id, nameCountry = country.name)
}
