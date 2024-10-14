package ru.practicum.android.diploma.filter.profession.data.mappers

import ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.HHIndustriesResponse
import ru.practicum.android.diploma.filter.profession.domain.model.Industry

internal class IndustryMapper {
    fun map(dto: ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.Industry) : Industry{
        return Industry(dto.id,dto.name)
    }

    fun map(dtoList : List<ru.practicum.android.diploma.data.networkclient.api.dto.response.industries.item.Industry>) : List<Industry>{
        return dtoList.map { map(it) }
    }

    fun map(industries: HHIndustriesResponse): List<Industry> = industries.map {
        Industry(
            it.id,
            it.name
        )
    }
}
