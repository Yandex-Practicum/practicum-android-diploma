package ru.practicum.android.diploma.search.data.network

data class SearchResponseVacancy(
    val acceptHandicapped: Boolean, // соискателей с инвалидностью
    val acceptIncompleteResumes: Boolean, // неполное резюме
    val acceptKids: Boolean, // старше 14 лет
    val allowMessages: Boolean, // возможность переписки с кандидатами
    val alternateUrl: String, // ссылка на представление вакансии на сайте
    val applyAlternateUrl: String, // Ссылка на отклик на вакансию на сайте
    val approved: Boolean, // Прошла ли вакансия модерацию
    val archived: Boolean, // Находится ли данная вакансия в архиве
    val area: Area, // Регион
    val billingType: BillingType, // Биллинговый тип из справочника vacancy_billing_type
    val description: String, // Описание в html, не менее 200 символов
    val driverLicenseTypes: List<String>, // Список требуемых категорий водительских прав
    val experience: Experience?, // object or null (Опыт работы)
    val hasTest: Boolean, // Информация о наличии прикрепленного тестового задании к вакансии
    val id: String, // Идентификатор вакансии
    val initialCreatedAt: String, // Дата и время создания вакансии
    val keySkills: List<Skill>, // Список ключевых навыков, не более 30
    val name: String, // Название
    val premium: Boolean, // Является ли данная вакансия премиум-вакансией
    val professionalRoles: List<String>, // Список профессиональных ролей
    val publishedAt: String, // Дата и время публикации вакансии
    val responseLetterRequired: Boolean, // Обязательно ли заполнять сообщение при отклике на вакансию
    val schedule: Schedule, // object or null (График работы)
    val type: VacancyType, // Идентификатор типа вакансии из справочника vacancy_type
)
