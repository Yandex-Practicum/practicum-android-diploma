package ru.practicum.android.diploma.data.vacancies.details

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.vacancies.details.dto.Address
import ru.practicum.android.diploma.data.vacancies.details.dto.Contacts
import ru.practicum.android.diploma.data.vacancies.details.dto.Employment
import ru.practicum.android.diploma.data.vacancies.details.dto.Experience
import ru.practicum.android.diploma.data.vacancies.details.dto.KeySkillVacancyDetail
import ru.practicum.android.diploma.data.vacancies.details.dto.Schedule
import ru.practicum.android.diploma.data.vacancies.dto.list.Employer
import ru.practicum.android.diploma.data.vacancies.dto.list.Salary
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyArea
import ru.practicum.android.diploma.data.vacancies.dto.list.VacancyType
import ru.practicum.android.diploma.data.vacancies.response.Response

data class VacancyDetailDtoResponse(
    val id: String,
    val name: String,
    val area: VacancyArea, // Регион
    @SerializedName("alternate_url")
    val vacancyLink: String, // Ссылка на представление вакансии на сайте
    val contacts: Contacts?, // Контакты
    val employer: Employer?, // Работодатель
    val salary: Salary?, // Зарплата
    val schedule: Schedule?, // График работы
    val type: VacancyType, // Тип вакансии
    val employment: Employment?, // Тип занятости
    val experience: Experience?, // Опыт работы
    @SerializedName("key_skills")
    val keySkills: List<KeySkillVacancyDetail>, // Список ключевых навыков
    val description: String, // Описание в html, не менее 200 символов
    val address: Address?, // Адрес
) : Response()
