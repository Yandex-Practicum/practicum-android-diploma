package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.IndustriesResponseDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesConverter {
    fun convertIndustriesResponse(industries: IndustriesResponseDto): List<Industry> {
        // преобразуем дерево отраслей в список
        var toProceed = industries.industries
        val allIndustries = mutableListOf<IndustryDto>()

        while (toProceed.isNotEmpty()) {
            allIndustries += toProceed
            toProceed = toProceed.flatMap { it.industries ?: emptyList() }
        }

        return allIndustries.map { it.toIndustry() }
    }

    private fun IndustryDto.toIndustry(): Industry {
        return Industry(this.id, this.name)
    }
}
