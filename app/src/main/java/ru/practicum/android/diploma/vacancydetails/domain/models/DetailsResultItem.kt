package ru.practicum.android.diploma.vacancydetails.domain.models

import ru.practicum.android.diploma.common.domain.VacancyBase

class DetailsResultItem(
    id: Int,
    hhId: String,
    name: String,
    isFavorite: Boolean
) : VacancyBase(
    id,
    hhId,
    name,
    isFavorite
)
