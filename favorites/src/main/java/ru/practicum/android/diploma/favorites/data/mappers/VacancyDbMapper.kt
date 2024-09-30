package ru.practicum.android.diploma.favorites.data.mappers

import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy

object VacancyDbMapper {
    fun map(vacancyEntity: VacancyEntity): FavoriteVacancy {
        return with(vacancyEntity) {
            FavoriteVacancy(
                idVacancy = idVacancy,
                nameVacancy = nameVacancy,
                salary = salary,
                nameCompany = nameCompany,
                location = location,
                urlLogo = urlLogo,
                dateAddVacancy = dateAddVacancy
            )
        }
    }
}
