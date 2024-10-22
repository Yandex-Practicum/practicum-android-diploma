package ru.practicum.android.diploma.filter.filter.domain.model

internal data class PlaceSettings(
    val idCountry: String?,
    val nameCountry: String?,
    val idRegion: String?,
    val nameRegion: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PlaceSettings) return false

        if (!idCountry.equals(other.idCountry)) return false
        if (!nameCountry.equals(other.nameCountry)) return false
        if (!idRegion.equals(other.idRegion)) return false
        if (!nameRegion.equals(other.nameRegion)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = idCountry?.hashCode() ?: 0
        result = 31 * result + (nameCountry?.hashCode() ?: 0)
        result = 31 * result + (idRegion?.hashCode() ?: 0)
        result = 31 * result + (nameRegion?.hashCode() ?: 0)
        return result
    }
}
