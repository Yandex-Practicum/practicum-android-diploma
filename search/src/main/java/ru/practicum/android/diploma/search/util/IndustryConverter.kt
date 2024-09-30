package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.search.domain.models.Industry
import ru.practicum.android.diploma.data.networkclient.api.dto.Industry as IndustryDto

class IndustryConverter {
    fun map(industry: IndustryDto): Industry {
        return with(industry) {
            Industry(id, name)
        }
    }
}
