package ru.practicum.android.diploma.presentation.vacancy.mapper

import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDetailContentUi
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyPhoneUi
import ru.practicum.android.diploma.util.extentions.SalaryFormatLabels
import ru.practicum.android.diploma.util.extentions.formatSalary

fun VacancyDetail.toContentUi(labels: SalaryFormatLabels): VacancyDetailContentUi {
    return VacancyDetailContentUi(
        id = id,
        title = name,
        salaryText = salary.formatSalary(labels),
        companyName = employer.name,
        logoUrl = employer.logo.takeIf { it.isNotBlank() },
        locationText = resolveLocationText(address, area),
        experience = experience?.name?.takeIf { it.isNotBlank() },
        employmentAndSchedule = buildEmploymentAndSchedule(employment, schedule),
        descriptionHtml = description,
        skills = skills.orEmpty().filter { it.isNotBlank() },
        contactName = contacts?.name?.takeIf { it.isNotBlank() },
        email = contacts?.email?.takeIf { it.isNotBlank() },
        phones = contacts?.toPhoneUi().orEmpty(),
        shareUrl = url,
        isFavorite = false,
    )
}

private fun resolveLocationText(address: Address?, area: FilterArea): String? {
    val addressText = address?.toDisplayText()
    return addressText?.takeIf { it.isNotBlank() } ?: area.name.takeIf { it.isNotBlank() }
}

private fun Address.toDisplayText(): String {
    return when {
        raw.isNotBlank() -> raw
        else -> listOf(city, street, building)
            .filter { it.isNotBlank() }
            .joinToString(", ")
    }
}

private fun buildEmploymentAndSchedule(
    employment: Employment?,
    schedule: Schedule?,
): String? {
    return listOfNotNull(
        employment?.name?.takeIf { it.isNotBlank() },
        schedule?.name?.takeIf { it.isNotBlank() },
    ).joinToString(", ").takeIf { it.isNotBlank() }
}

private fun Contacts.toPhoneUi(): List<VacancyPhoneUi> {
    return phones
        .filter { it.formatted.isNotBlank() }
        .map { VacancyPhoneUi(formatted = it.formatted, comment = it.comment?.takeIf { c -> c.isNotBlank() }) }
}
