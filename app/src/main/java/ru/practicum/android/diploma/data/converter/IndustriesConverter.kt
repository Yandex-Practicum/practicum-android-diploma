package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.filter.industries.dto.ParentIndustriesResponse
import ru.practicum.android.diploma.domain.models.industries.ChildIndustry

object IndustriesConverter {

    fun List<ParentIndustriesResponse>.convert(): List<ChildIndustry> {

        val childIndustries = ArrayList<ChildIndustry>()

        this.forEach { parentIndustry ->
            parentIndustry.industries.forEach { childIndustry ->
                childIndustries.add(
                    ChildIndustry(
                        id = childIndustry.id,
                        name = childIndustry.name
                    )
                )
            }
        }

        return childIndustries
    }
}
