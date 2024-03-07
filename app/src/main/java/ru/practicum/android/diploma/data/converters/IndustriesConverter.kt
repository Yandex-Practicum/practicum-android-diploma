package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.ParentIndustries
import ru.practicum.android.diploma.domain.industries.ParentIndustriesAllDeal

object IndustriesConverter {
    fun ParentIndustries.mapToIndustriesAllDeal(): ParentIndustriesAllDeal {
        return ParentIndustriesAllDeal(
            id = id,
            name = name
        )
    }

    fun List<ParentIndustries>.mapToIndustriesAllDeal(): List<ParentIndustriesAllDeal> =
        map { it.mapToIndustriesAllDeal() }
}
