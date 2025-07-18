package ru.practicum.android.diploma.data.db

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.models.VacancyDetails

fun mapToDetails(entity: VacancyEntity): VacancyDetails {
    return VacancyDetails(
        vacancyId = entity.id,
        linkUrl = entity.url,
        vacancyName = entity.name,
        currency = entity.currency,
        salaryFrom = entity.salaryFrom,
        salaryTo = entity.salaryTo,
        employerName = entity.employer,
        employerLogo = entity.employerLogo,
        address = entity.address,
        experience = entity.experience,
        workFormat = entity.workFormat?.let {
            Json.decodeFromString(ListSerializer(String.serializer()), it)
        },
        employmentForm = entity.employmentForm,
        description = entity.description,
        keySkills = Json.decodeFromString(ListSerializer(String.serializer()), entity.keySkills),
        isFavorite = true
    )
}

fun mapToEntity(vacancy: VacancyDetails): VacancyEntity {
    return VacancyEntity(
        id = vacancy.vacancyId,
        url = vacancy.linkUrl,
        name = vacancy.vacancyName,
        currency = vacancy.currency,
        salaryFrom = vacancy.salaryFrom,
        salaryTo = vacancy.salaryTo,
        employer = vacancy.employerName,
        employerLogo = vacancy.employerLogo,
        experience = vacancy.experience,
        workFormat = vacancy.workFormat?.let {
            Json.encodeToString(ListSerializer(String.serializer()), it)
        },
        employmentForm = vacancy.employmentForm,
        description = vacancy.description,
        address = vacancy.address,
        keySkills = Json.encodeToString(ListSerializer(String.serializer()), vacancy.keySkills),
        additionTime = System.currentTimeMillis()
    )
}

fun mapToPreview(entity: VacancyEntity): VacanciesPreview {
    return VacanciesPreview(
        vacancyId = entity.id,
        vacancyName = entity.name,
        employerName = entity.employer,
        employerLogo = entity.employerLogo,
        address = entity.address,
        salaryFrom = entity.salaryFrom,
        salaryTo = entity.salaryTo,
        currency = entity.currency
    )
}
