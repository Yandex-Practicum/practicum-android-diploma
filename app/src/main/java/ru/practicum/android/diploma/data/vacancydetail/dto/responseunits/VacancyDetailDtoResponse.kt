package ru.practicum.android.diploma.data.vacancydetail.dto.responseunits

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.Response
import ru.practicum.android.diploma.data.dto.responseUnits.Employer
import ru.practicum.android.diploma.data.dto.responseUnits.Salary
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyArea
import ru.practicum.android.diploma.data.dto.responseUnits.VacancyType

class VacancyDetailDtoResponse(
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
): Response()
