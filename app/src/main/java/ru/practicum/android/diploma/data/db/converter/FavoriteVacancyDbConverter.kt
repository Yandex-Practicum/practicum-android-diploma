package ru.practicum.android.diploma.data.db.converter

import ru.practicum.android.diploma.data.db.entity.AddressEmbedded
import ru.practicum.android.diploma.data.db.entity.ContactsEmbedded
import ru.practicum.android.diploma.data.db.entity.EmployerEmbedded
import ru.practicum.android.diploma.data.db.entity.EmploymentEmbedded
import ru.practicum.android.diploma.data.db.entity.ExperienceEmbedded
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.data.db.entity.FilterAreaEmbedded
import ru.practicum.android.diploma.data.db.entity.FilterIndustryEmbedded
import ru.practicum.android.diploma.data.db.entity.PhoneEmbedded
import ru.practicum.android.diploma.data.db.entity.SalaryEmbedded
import ru.practicum.android.diploma.data.db.entity.ScheduleEmbedded
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail

class FavoriteVacancyDbConverter {

    fun map(vacancy: VacancyDetail): FavoriteVacancyEntity {
        return FavoriteVacancyEntity(
            vacancy.id,
            vacancy.name,
            vacancy.description,
            vacancy.salary?.toEmbedded(),
            vacancy.address?.toEmbedded(),
            vacancy.experience?.toEmbedded(),
            vacancy.schedule?.toEmbedded(),
            vacancy.employment?.toEmbedded(),
            vacancy.contacts?.toEmbedded(),
            vacancy.employer.toEmbedded(),
            mapAreaToEmbedded(vacancy.area),
            vacancy.skills,
            vacancy.url,
            vacancy.industry.toEmbedded()
        )
    }

    fun map(vacancy: FavoriteVacancyEntity): VacancyDetail {
        return VacancyDetail(
            vacancy.id,
            vacancy.name,
            vacancy.description,
            vacancy.salary?.toDomain(),
            vacancy.address?.toDomain(),
            vacancy.experience?.toDomain(),
            vacancy.schedule?.toDomain(),
            vacancy.employment?.toDomain(),
            vacancy.contacts?.toDomain(),
            vacancy.employer.toDomain(),
            mapAreaToDomain(vacancy.area),
            vacancy.skills,
            vacancy.url,
            vacancy.industry.toDomain()
        )
    }

    private fun Salary.toEmbedded() = SalaryEmbedded(from, to, currency)
    private fun SalaryEmbedded.toDomain() = Salary(from, to, currency)
    private fun Address.toEmbedded() = AddressEmbedded(id, city, street, building, raw)
    private fun AddressEmbedded.toDomain() = Address(id, city, street, building, raw)
    private fun Experience.toEmbedded() = ExperienceEmbedded(id, name)
    private fun ExperienceEmbedded.toDomain() = Experience(id, name)

    private fun Schedule.toEmbedded() = ScheduleEmbedded(id, name)
    private fun ScheduleEmbedded.toDomain() = Schedule(id, name)

    private fun Employment.toEmbedded() = EmploymentEmbedded(id, name)
    private fun EmploymentEmbedded.toDomain() = Employment(id, name)

    private fun Phone.toEmbedded() = PhoneEmbedded(comment, formatted)
    private fun PhoneEmbedded.toDomain() = Phone(comment, formatted)
    private fun Contacts.toEmbedded() = ContactsEmbedded(
        id = id,
        name = name,
        email = email,
        phones = phones.map { it.toEmbedded() },
    )
    private fun ContactsEmbedded.toDomain() = Contacts(
        id = id,
        name = name,
        email = email,
        phones = phones.map { it.toDomain() },
    )
    private fun Employer.toEmbedded() = EmployerEmbedded(id, name, logo)
    private fun EmployerEmbedded.toDomain() = Employer(id, name, logo)
    private fun FilterIndustry.toEmbedded() = FilterIndustryEmbedded(id, name)
    private fun FilterIndustryEmbedded.toDomain() = FilterIndustry(id, name)
    private fun mapAreaToEmbedded(area: FilterArea): FilterAreaEmbedded = FilterAreaEmbedded(
        id = area.id,
        name = area.name,
        parentId = area.parentId,
        areas = area.areas.map(::mapAreaToEmbedded),
    )

    private fun mapAreaToDomain(area: FilterAreaEmbedded): FilterArea = FilterArea(
        id = area.id,
        name = area.name,
        parentId = area.parentId,
        areas = area.areas.map(::mapAreaToDomain),
    )
}
