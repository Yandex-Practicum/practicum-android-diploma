package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

fun IndustryDto.toDomain(): Industry = Industry(id = id, name = name)
