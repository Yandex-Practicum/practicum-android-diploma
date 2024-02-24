package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.db.entyti.VacancyDetailDtoEntity
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto

object VacancyDetailsDbConverter {

    fun VacancyDetailDtoEntity.mapToVacancyDetailDto(): VacancyDetailDto {
        return VacancyDetailDto(
            id = id,
            name = name,
            area = area,
            contacts = contacts,
            employer = employer,
            salary = salary,
            schedule = schedule,
            type = type,
            employment = employment,
            experience = experience,
            snippet = snippet,
            keySkills = keySkills,
            url = url,
            description = description
        )
    }

    fun VacancyDetailDto.mapToVacancyDetailDtoEntity(): VacancyDetailDtoEntity {
        return VacancyDetailDtoEntity(
            id = id,
            name = name,
            area = area,
            contacts = contacts,
            employer = employer,
            salary = salary,
            schedule = schedule,
            type = type,
            employment = employment,
            experience = experience,
            snippet = snippet,
            keySkills = keySkills,
            url = url,
            description = description
        )
    }

    fun List<VacancyDetailDtoEntity>.mapToVacancyDetailDto(): List<VacancyDetailDto> =
        map { it.mapToVacancyDetailDto() }


}
