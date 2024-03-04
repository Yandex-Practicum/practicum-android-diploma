package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.data.network.dto.IndustryDto
import ru.practicum.android.diploma.filter.industry.domain.model.Industry

fun List<IndustryDto>.mapToDomain(): List<Industry> {
    return this.map { Industry(it.id, it.name) }
}
