package ru.practicum.android.diploma.filters.industries.domain.models

data class Industry(
    val id: String,
    val name: String,
    val isChecked: Boolean,
    val industries: List<Industry>? = null
) {
    override fun equals(other: Any?): Boolean {
        return other is Industry
            && other.id == this.id
    }
}
