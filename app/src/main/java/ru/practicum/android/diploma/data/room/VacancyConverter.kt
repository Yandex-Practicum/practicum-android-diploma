package ru.practicum.android.diploma.data.room

import ru.practicum.android.diploma.data.dto.favourites.room.VacancyDetails
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyEntity
import java.util.stream.Collectors

object VacancyConverter {
    fun map(entity: VacancyEntity): VacancyDetails = VacancyDetails(
        id = entity.id,
        url = entity.url,
        name = entity.name,
        area = entity.area,
        salaryCurrency = entity.salaryCurrency,
        salaryFrom = entity.salaryFrom,
        salaryTo = entity.salaryTo,
        salaryGross = entity.salaryGross,
        experience = entity.experience,
        schedule = entity.schedule,
        contactName = entity.contactName,
        contactEmail = entity.contactEmail,
        phones = entity.phones?.split(";"),
        contactComment = entity.contactComment,
        logoUrl = entity.logoUrl,
        logoUrl90 = entity.logoUrl90,
        logoUrl240 = entity.logoUrl240,
        address = entity.address,
        employerUrl = entity.employerUrl,
        employerName = entity.employerName,
        employment = entity.employment,
        keySkills = entity.keySkills?.split(";"),
        description = entity.description,
    ).apply {
        isFavorite.isFavorite = true
    }

    fun map(vacancy: VacancyDetails): VacancyEntity = VacancyEntity(
        id = vacancy.id,
        url = vacancy.url!!,
        name = vacancy.name!!,
        area = vacancy.area!!,
        salaryCurrency = vacancy.salaryCurrency,
        salaryFrom = vacancy.salaryFrom,
        salaryTo = vacancy.salaryTo,
        salaryGross = vacancy.salaryGross,
        experience = vacancy.experience,
        schedule = vacancy.schedule,
        contactName = vacancy.contactName,
        contactEmail = vacancy.contactEmail,
        phones = vacancy.phones?.stream()?.collect(Collectors.joining(";")),
        contactComment = vacancy.contactComment,
        logoUrl = vacancy.logoUrl,
        logoUrl90 = vacancy.logoUrl90,
        logoUrl240 = vacancy.logoUrl240,
        address = vacancy.address,
        employerUrl = vacancy.employerUrl,
        employerName = vacancy.employerName,
        employment = vacancy.employment,
        keySkills = vacancy.keySkills?.stream()?.collect(Collectors.joining(";")),
        salary = vacancy.salaryCurrency.toString(),
        description = vacancy.description!!,
    )
}
