package ru.practicum.android.diploma.domain.models.vacancydetails

data class EmploymentForm(
    val name: String,
    val requiresSuffix: Boolean
)

enum class EmploymentFormType(val id: String, val requiresSuffix: Boolean) {
    FULL("FULL", true),
    PART("PART", true);

    companion object {
        fun fromId(id: String?): EmploymentFormType? = entries.find { it.id == id }
    }
}
