package ru.practicum.android.diploma.details.data.model

import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class VacancyConverter @Inject constructor() {

    fun toVacancy(vacancyEntity: VacancyEntity): Vacancy {
        return with(vacancyEntity) {
            Vacancy(
                id = id,
                iconUri = iconUri,
                title = title,
                company = company,
                salary = salary,
                date = date
            )
        }
    }
    fun toEntity(vacancy: Vacancy): VacancyEntity {
        return with(vacancy) {
            VacancyEntity(
                id = id,
                iconUri = iconUri,
                title = title,
                company = company,
                salary = salary,
                date = date
            )
        }
    }


    fun mapToVacancies(entities: List<VacancyEntity>): List<Vacancy>{
        return entities.map { toVacancy(it) }
    }
    fun mapToEntities(vacancies: List<Vacancy>): List<VacancyEntity>{
        return vacancies.map { toEntity(it) }
    }


}