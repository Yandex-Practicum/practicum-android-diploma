package ru.practicum.android.diploma.search.data.network.converter

import ru.practicum.android.diploma.search.data.network.dto.VacancyDto
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class VacancyModelConverter @Inject constructor() {
    
    fun mapList(list: List<VacancyDto>): List<Vacancy> {
        return list.map { it.map() }
    }
    
    private fun VacancyDto.map(): Vacancy {
        return with(this) {
            Vacancy(
                id = id ?: "",
                iconUri = employer?.logo_urls?.`240` ?: "",
                title = name ?: "",
                company = employer?.name ?: "",
                salary = salary?.currency ?: "ЗП не указана",
                area = area?.name ?: "",
                date = published_at ?: "",
            )
        }
    }
}