package ru.practicum.android.diploma.search.util

import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.search.domain.models.Industry
import ru.practicum.android.diploma.search.domain.models.IndustryList
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.Industry as IndustryDto

class IndustryConverter {
    fun map(industry: IndustryDto): Industry {
        return with(industry) {
            Industry(id = id, name = name)
        }
    }

    fun map(industryResponse: HHIndustriesResponse): List<IndustryList> {
        return industryResponse.map { industryItem ->
            IndustryList(id = industryItem.id, industries = map(industryItem.industries), name = industryItem.name)
        }
    }

    fun map(industries: List<IndustryDto>): List<Industry> {
        return industries.map { map(it) }
    }
}
