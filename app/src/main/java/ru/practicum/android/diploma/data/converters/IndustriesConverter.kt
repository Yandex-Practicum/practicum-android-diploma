package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.data.response.IndustriesResponse

object IndustriesConverter {

    fun IndustriesResponse.mapToIndustries(): Industries {
        return Industries(
            id = id,
            name = name
        )
    }
}
