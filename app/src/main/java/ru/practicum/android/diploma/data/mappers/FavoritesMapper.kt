package ru.practicum.android.diploma.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyAddress
import ru.practicum.android.diploma.domain.models.VacancyContacts
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.models.VacancyEmployer
import ru.practicum.android.diploma.domain.models.VacancyEmployment
import ru.practicum.android.diploma.domain.models.VacancyExperience
import ru.practicum.android.diploma.domain.models.VacancyPhone
import ru.practicum.android.diploma.domain.models.VacancySalary
import ru.practicum.android.diploma.domain.models.VacancySchedule

private const val LIST_SEPARATOR = "|"

private val gson = Gson()
private val phoneListType = object : TypeToken<List<VacancyPhone>>() {}.type

fun VacancyDetail.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        name = name,
        company = employer?.name,
        city = address?.city ?: areaName,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        salaryCurrency = salary?.currency,
        logo = employer?.logo,
        description = description,
        areaName = areaName,
        url = url,
        addressCity = address?.city,
        addressStreet = address?.street,
        addressBuilding = address?.building,
        addressRaw = address?.raw,
        experienceId = experience?.id,
        experienceName = experience?.name,
        scheduleId = schedule?.id,
        scheduleName = schedule?.name,
        employmentId = employment?.id,
        employmentName = employment?.name,
        contactName = contacts?.name,
        contactEmail = contacts?.email,
        employerName = employer?.name,
        employerLogo = employer?.logo,
        skills = skills.joinToString(LIST_SEPARATOR),
        phones = contacts?.phones.orEmpty().toJson(),
    )
}

fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = id,
        name = name,
        company = company,
        city = city,
        salary = toSalary(),
        logo = logo,
    )
}

fun VacancyEntity.toVacancyDetail(): VacancyDetail {
    return VacancyDetail(
        id = id,
        name = name,
        description = description,
        salary = toSalary(),
        address = toAddress(),
        experience = toExperience(),
        schedule = toSchedule(),
        employment = toEmployment(),
        contacts = toContacts(),
        employer = toEmployer(),
        areaName = areaName,
        skills = skills.toListFromString(),
        url = url,
    )
}

private fun VacancyEntity.toAddress(): VacancyAddress {
    return VacancyAddress(
        city = addressCity,
        street = addressStreet,
        building = addressBuilding,
        raw = addressRaw,
    )
}

private fun VacancyEntity.toExperience(): VacancyExperience {
    return VacancyExperience(
        id = experienceId,
        name = experienceName,
    )
}

private fun VacancyEntity.toSchedule(): VacancySchedule {
    return VacancySchedule(
        id = scheduleId,
        name = scheduleName,
    )
}

private fun VacancyEntity.toEmployment(): VacancyEmployment {
    return VacancyEmployment(
        id = employmentId,
        name = employmentName,
    )
}

private fun VacancyEntity.toContacts(): VacancyContacts {
    return VacancyContacts(
        name = contactName,
        email = contactEmail,
        phones = phones.toPhones(),
    )
}

private fun VacancyEntity.toEmployer(): VacancyEmployer? {
    return employerName?.let {
        VacancyEmployer(
            name = it,
            logo = employerLogo,
        )
    }
}

private fun VacancyEntity.toSalary(): VacancySalary? {
    return if (salaryFrom == null && salaryTo == null && salaryCurrency == null) {
        null
    } else {
        VacancySalary(
            from = salaryFrom,
            to = salaryTo,
            currency = salaryCurrency,
        )
    }
}

private fun List<VacancyPhone>.toJson(): String {
    return gson.toJson(this)
}

private fun String.toPhones(): List<VacancyPhone> {
    return if (isBlank()) {
        emptyList()
    } else {
        gson.fromJson(this, phoneListType)
    }
}

private fun String.toListFromString(): List<String> {
    return if (isBlank()) {
        emptyList()
    } else {
        split(LIST_SEPARATOR)
    }
}
