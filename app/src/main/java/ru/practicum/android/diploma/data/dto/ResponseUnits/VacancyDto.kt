package ru.practicum.android.diploma.data.dto.ResponseUnits

class VacancyDto (
    val id: String,
    val name: String,
    val accept_incomplete_resumes: Boolean, //Разрешен ли отклик на вакансию неполным резюме
    val alternate_url: String, //Ссылка на представление вакансии на сайте
    val apply_alternate_url: String, //Ссылка на отклик на вакансию на сайте
    val area: VacancyArea, //Регион
    val contacts: Contacts?, //Контакты
    val employer: Employer, //Работодатель
    val has_test: Boolean, //Информация о наличии прикрепленного тестового задании к вакансии
    val professional_roles: Array<ProfessionalRole>, //Список профессиональных ролей
    val published_at: String, //Дата и время публикации вакансии
    val relations: Array<Relations>?, //Возвращает связи соискателя с вакансией
    val response_letter_required: Boolean, //Обязательно ли заполнять сообщение при отклике на вакансию
    val salary: Salary?, //Зарплата
    val schedule: Schedule?, //График работы
    val type: VacancyType, //Тип вакансии
    val url: String, // URL
    val employment: Employment?, // Тип занятости
    val experience: Experience?, // Опыт работы
    val snippet: Snippet, //Дополнительные текстовые отрывки
    val show_logo_in_search: Boolean?, //Отображать ли лого для вакансии в поисковой выдаче
)
