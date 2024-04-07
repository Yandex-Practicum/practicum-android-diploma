package ru.practicum.android.diploma.domain.models.industries

data class ParentIndustry(
    val id: String,
    val name: String,
    val childIndustries: List<ChildIndustry>
)
