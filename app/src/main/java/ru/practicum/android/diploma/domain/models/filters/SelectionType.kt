package ru.practicum.android.diploma.domain.models.filters

enum class SelectionType(val value: String) {
    COUNTRY("country"),
    REGION("region");

    companion object {
        fun from(value: String?): SelectionType? = entries.find { it.value == value }
    }
}
