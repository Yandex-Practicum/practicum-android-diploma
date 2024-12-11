package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.model.VacancyDto

class HhResponse(val results: List<VacancyDto>, resultCode: Int) : Response(resultCode)
