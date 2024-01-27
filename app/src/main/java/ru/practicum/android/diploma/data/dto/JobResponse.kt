package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.domain.models.Vacancy

data class JobResponse(val results: List<Vacancy>) : Response()
