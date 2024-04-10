package ru.practicum.android.diploma.data.converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.db.model.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.vacacy.Contacts
import ru.practicum.android.diploma.domain.models.vacacy.Employer
import ru.practicum.android.diploma.domain.models.vacacy.LogoUrls
import ru.practicum.android.diploma.domain.models.vacacy.Salary

object FavoriteVacanciesConverter {

    @Suppress("detekt:LongMethod")
    fun VacancyDetails.convert(): VacancyEntity = VacancyEntity(
        id = this.id,
        name = this.name,

        employerId = this.employer?.id,
        employerLogoUrl90 = this.employer?.logoUrls?.art90,
        employerLogoUrl240 = this.employer?.logoUrls?.art240,
        employerLogoUrlOriginal = this.employer?.logoUrls?.original,
        employerName = this.employer?.name,
        employerIsTrusted = this.employer?.trusted,
        employerVacanciesUrl = this.employer?.vacanciesUrl,

        salaryCurrency = this.salary?.currency,
        salaryFrom = this.salary?.from,
        salaryGross = this.salary?.gross,
        salaryTo = this.salary?.to,

        // delete ? ---------------------------
        /*departmentId = "",
        departmentName = "",

        areaId = "",
        areaName = "",
        areaUrl = "",

        typeId = "",
        typeName = "",*/
        // delete ? ---------------------------

        city = this.city,
        experience = this.experience,
        employment = this.employment,
        description = this.description,

        contactEmail = this.contacts?.email,
        contactName = this.contacts?.name,
        contactPhonesJson = Gson().toJson(this.contacts?.phones),

        link = this.link
    )

    @Suppress("detekt:LongMethod")
    fun VacancyEntity.convert(): VacancyDetails = VacancyDetails(
        id = this.id,
        name = this.name,

        salary = if (!this.salaryCurrency.isNullOrBlank()) {
            Salary(
                currency = this.salaryCurrency,
                from = this.salaryFrom,
                gross = this.salaryGross,
                to = this.salaryTo
            )
        } else {
            null
        },

        employer = if (!this.employerId.isNullOrBlank()) {
            Employer(
                id = this.employerId,
                logoUrls = if (!this.employerLogoUrlOriginal.isNullOrBlank()) {
                    LogoUrls(
                        art90 = this.employerLogoUrl90,
                        art240 = this.employerLogoUrl240,
                        original = this.employerLogoUrlOriginal
                    )
                } else {
                    null
                },
                name = this.name,
                trusted = this.employerIsTrusted ?: false,
                vacanciesUrl = this.employerVacanciesUrl
            )
        } else {
            null
        },

        city = this.city,
        // Новый параметр fullAddress и areaName
        fullAddress = this.employment,
        areaName = this.name,
        experience = this.experience,
        employment = this.employment,
        description = this.description,

        contacts = if (
            this.contactEmail.isNullOrBlank() &&
            this.contactName.isNullOrBlank() &&
            this.contactPhonesJson.isNullOrBlank()
        ) {
            null
        } else {
            Contacts(
                email = this.contactEmail,
                name = this.contactName,
                phones = if (this.contactPhonesJson.isNullOrBlank()) {
                    null
                } else {
                    Gson().fromJson<List<String>>(this.contactPhonesJson, object : TypeToken<List<String>>() {}.type)
                }
            )
        },

        link = this.link
    )
}
