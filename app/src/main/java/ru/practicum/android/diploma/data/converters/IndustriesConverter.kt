package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.IndustriesResponse
import ru.practicum.android.diploma.domain.industries.IndustriesAllDeal

object IndustriesConverter {
    fun IndustriesResponse.mapToIndustriesAllDeal(): IndustriesAllDeal {
        return IndustriesAllDeal(
            id = id,
            name = name
        )
    }
}
