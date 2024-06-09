package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.IndustryDto

class IndustryResponse : Response() {
    var items = emptyList<IndustryDto>()
}
