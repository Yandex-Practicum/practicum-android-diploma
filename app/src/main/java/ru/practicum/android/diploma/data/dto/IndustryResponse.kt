package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.domain.models.Industry

class IndustryResponse(val results: List<Industry>): Response()
