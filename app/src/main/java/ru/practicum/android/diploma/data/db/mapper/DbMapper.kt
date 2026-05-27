package ru.practicum.android.diploma.data.db.mapper

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
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

fun VacancyDetail.toDb(): FavoriteVacancyEntity = FavoriteVacancyEntity(
    id = id,
    name = name,
    description = description,
    salary = salary?.toDb(),
    address = address?.toDb(),
    experience = experience?.toDb(),
    schedule = schedule?.toDb(),
    employment = employment?.toDb(),
    contacts = contacts?.toDb(),
    employer = employer.toDb(),
    area = area.toDb(),
    skills = skills,
    url = url,
    industry = industry.toDb()
)

fun FavoriteVacancyEntity.toVacancyDetail(): VacancyDetail = VacancyDetail(
    id = id,
    name = name,
    description = description,
    salary = salary?.fromDb(),
    address = address?.fromDb(),
    experience = experience?.fromDb(),
    schedule = schedule?.fromDb(),
    employment = employment?.fromDb(),
    contacts = contacts?.fromDb(),
    employer = employer.fromDb(),
    area = area.fromDb(),
    skills = skills,
    url = url,
    industry = industry.fromDb()
)

fun FavoriteVacancyEntity.toVacancy(): Vacancy = Vacancy(
    id = id,
    name = name,
    company = employer.name,
    city = address?.city,
    salary = salary?.fromDb(),
    logo = employer.logo
)

private fun Salary.toDb(): SalaryEmbedded = SalaryEmbedded(
    from = from,
    to = to,
    currency = currency
)
private fun SalaryEmbedded.fromDb(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)

private fun Address.toDb(): AddressEmbedded = AddressEmbedded(
    id = id,
    city = city,
    street = street,
    building = building,
    raw = raw
)
private fun AddressEmbedded.fromDb(): Address = Address(
    id = id,
    city = city,
    street = street?:"",
    building = building?:"",
    raw = raw
)

private fun Experience.toDb(): ExperienceEmbedded = ExperienceEmbedded(
    id = id,
    name = name
)

private fun ExperienceEmbedded.fromDb(): Experience = Experience(
    id = id,
    name = name
)

private fun Schedule.toDb(): ScheduleEmbedded = ScheduleEmbedded(
    id = id,
    name = name
)
private fun ScheduleEmbedded.fromDb(): Schedule = Schedule(
    id = id,
    name = name
)

private fun Employment.toDb(): EmploymentEmbedded = EmploymentEmbedded(
    id = id,
    name = name
)
private fun EmploymentEmbedded.fromDb(): Employment = Employment(
    id = id,
    name = name
)

private fun Contacts.toDb(): ContactsEmbedded = ContactsEmbedded(
    id = id,
    name = name,
    email = email,
    phones = phones.map { it.toDb() }
)
private fun ContactsEmbedded.fromDb(): Contacts = Contacts(
    id = id,
    name = name,
    email = email?:"",
    phones = phones?.map { it.fromDb() }?:listOf()
)

private fun Phone.toDb(): PhoneEmbedded = PhoneEmbedded(
    comment = comment,
    formatted = formatted
)
private fun PhoneEmbedded.fromDb(): Phone = Phone(
    comment = comment,
    formatted = formatted?:""
)

private fun Employer.toDb(): EmployerEmbedded = EmployerEmbedded(
    id = id,
    name = name,
    logo = logo
)

private fun EmployerEmbedded.fromDb(): Employer = Employer(
    id = id,
    name = name,
    logo = logo?:""
)
private fun FilterArea.toDb(): FilterAreaEmbedded = FilterAreaEmbedded(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.map { it.toDb() }
)
private fun FilterAreaEmbedded.fromDb(): FilterArea = FilterArea(
    id = id,
    name = name,
    parentId = parentId,
    areas = areas.map { it.fromDb() }
)

private fun FilterIndustry.toDb(): FilterIndustryEmbedded = FilterIndustryEmbedded(
    id = id,
    name = name
)

private fun FilterIndustryEmbedded.fromDb(): FilterIndustry = FilterIndustry(
    id = id,
    name = name
)



