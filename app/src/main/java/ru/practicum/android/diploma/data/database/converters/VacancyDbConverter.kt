package ru.practicum.android.diploma.data.database.converters

import ru.practicum.android.diploma.data.database.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {
    fun map(vacancy: Vacancy): VacancyEntity {
        return with(vacancy) {
            VacancyEntity(
                //photo = "",
                title = title,
                description = description
            )
        }
    }

    fun map(vacancyEntity: VacancyEntity): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id.toInt(),
                title = title,
                description = description
            )
        }
    }

    fun map(list: List<VacancyEntity>): List<Vacancy> {
        return list.map {
            with(it) {
                Vacancy(
                    id = id.toInt(),
                    title = title,
                    description = description
                )
            }
        }

    }
}
