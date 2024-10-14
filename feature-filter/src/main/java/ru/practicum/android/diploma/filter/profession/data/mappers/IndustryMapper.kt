package ru.practicum.android.diploma.filter.profession.data.mappers

import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.Industry
import ru.practicum.android.diploma.filter.profession.domain.model.IndustryModel

internal class IndustryMapper {
    fun map(
        dto: Industry
    ): IndustryModel {
        return IndustryModel(dto.id, dto.name)
    }

    fun map(dtoList: List<Industry>): List<IndustryModel> {
        return dtoList.map { map(it) }
    }

    fun map(industries: HHIndustriesResponse): List<IndustryModel> = industries.map {
        IndustryModel(
            it.id,
            it.name
        )
    }
}
