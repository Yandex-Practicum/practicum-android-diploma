package ru.practicum.android.diploma.filter.filter.domain.model

data class IndustrySetting(
    val id: String?,
    val name: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndustrySetting) return false

        if (!id.equals(other.id)) return false
        if (!name.equals(other.name)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}
