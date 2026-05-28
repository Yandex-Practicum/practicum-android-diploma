package ru.practicum.android.diploma.core.data.dto

class FilterAreaDto(
    id: Integer,
    name: String,
    parentId: Integer,
    areas: List<FilterAreaDto>
)
