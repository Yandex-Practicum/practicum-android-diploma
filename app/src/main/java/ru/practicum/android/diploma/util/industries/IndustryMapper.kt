package ru.practicum.android.diploma.util.industries

import ru.practicum.android.diploma.data.response.ParentIndustries
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

object IndustryMapper {
    fun mapToEntityList(industriesDto: List<ParentIndustries>): List<ParentIndustriesAllDeal> {
        val industries = mutableListOf<ParentIndustriesAllDeal>()
        industriesDto.forEach { parentIndustry ->
            parentIndustry.industries.forEach { industry ->
                industries.add(
                    ParentIndustriesAllDeal(
                        id = industry.id,
                        name = industry.name
                    )
                )
            }
        }
        return industries
    }
}
