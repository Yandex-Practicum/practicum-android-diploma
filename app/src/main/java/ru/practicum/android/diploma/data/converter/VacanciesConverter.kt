package ru.practicum.android.diploma.data.converter

import ru.practicum.android.diploma.data.db.model.VacancyEntity
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

object VacanciesConverter {

    fun Vacancy.convert():VacancyEntity{
        return VacancyEntity(
            id = this.id,
            url = this.employer?.logoUrls ?: "",
            name = this.name,
            area = area.name

        )
    }

}
