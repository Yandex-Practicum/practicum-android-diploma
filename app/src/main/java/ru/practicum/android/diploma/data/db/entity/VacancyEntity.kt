package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.converters.StringListConverter

@Entity(tableName = "fav_vacancies_table")
@TypeConverters(StringListConverter::class)
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "vacancy_id")
    val vacancyId: String, // ID вакансии
    @ColumnInfo(name = "vacancy_name")
    val vacancyName: String, // Название вакансии
    @Embedded(prefix = "employer_")
    val vacancyEmployer: EmployerEntity?, // Данные работодателя
    @Embedded(prefix = "area_")
    val vacancyArea: AreaEntity?, // Данные региона (города)
    @Embedded(prefix = "salary_")
    val vacancySalary: SalaryEntity?, // Данные о зарплате
    @ColumnInfo(name = "vacancy_description")
    val vacancyDescription: String?, // Описание вакансии (HTML)
    @ColumnInfo(name = "vacancy_experience_id")
    val vacancyExperienceId: String?, // ID опыта работы
    @ColumnInfo(name = "vacancy_experience_name")
    val vacancyExperienceName: String?, // Требуемый опыт работы
    @ColumnInfo(name = "vacancy_schedule_id")
    val vacancyScheduleId: String?, // ID графика работы
    @ColumnInfo(name = "vacancy_schedule_name")
    val vacancyScheduleName: String?, // График работы
    @ColumnInfo(name = "vacancy_employment_id")
    val vacancyEmploymentId: String?, // ID типа занятости
    @ColumnInfo(name = "vacancy_employment_name")
    val vacancyEmploymentName: String?, // Тип занятости
    @ColumnInfo(name = "vacancy_address_id")
    val vacancyAddressId: String?, // ID адреса
    @ColumnInfo(name = "vacancy_address_city")
    val vacancyAddressCity: String?, // Город
    @ColumnInfo(name = "vacancy_address_street")
    val vacancyAddressStreet: String?, // Улица
    @ColumnInfo(name = "vacancy_address_building")
    val vacancyAddressBuilding: String?, // Дом
    @ColumnInfo(name = "vacancy_address_raw")
    val vacancyAddressRaw: String?, // Полный адрес
    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "vacancy_skills")
    val vacancySkills: List<String>?, // Список ключевых навыков
    @Embedded(prefix = "contacts_")
    val vacancyContacts: ContactsEntity?, // Контактная информация
    @ColumnInfo(name = "vacancy_share_url")
    val vacancyShareUrl: String?, // Ссылка на вакансию для шеринга
    @ColumnInfo(name = "vacancy_industry_id")
    val vacancyIndustryId: Int, // ID отрасли
    @ColumnInfo(name = "vacancy_industry_name")
    val vacancyIndustryName: String // Название отрасли
)

/**
 * ИНФОРМАЦИЯ ДЛЯ РАЗРАБОТКИ ЭКРАНА ОТРАСЛЕЙ (Victoria):
 *
 * 1. Правило именования: Используем стандарт «ID + Name».
 *    - industryId: String (Идентификатор отрасли)
 *    - industryName: String (Название отрасли)
 *
 * 2. Модель данных (Domain):
 *    - Создать файл: domain/models/Industry.kt
 *    - Структура: data class Industry(val industryId: String, val industryName: String)
 *
 * 3. Названия классов для реализации:
 *    - IndustryViewModel (Презентация)
 *    - IndustryState (Состояния экрана: Loading, Content, Error, Empty)
 *    - IndustryAdapter / IndustryViewHolder (UI список)
 *
 * 4. Передача данных (Result API):
 *    - При возврате выбранной отрасли на экран фильтров использовать ключ "industryId".
 *
 * 5. Типизация:
 *    - Все ID строго String (даже если из API приходит число, приводим к String в маппере).
 */

/**
 * ИНФОРМАЦИЯ ДЛЯ РАЗРАБОТКИ ЭКРАНА ПОИСКА (Victor & Khazaret):
 *
 * 1. Для верстки списков и элементов (Victor):
 *    - Использовать поля из `domain/models/Vacancy.kt` для привязки к View.
 *    - Ключевые поля элемента списка: vacancyName, companyName, areaName, logoUrl, а также salaryFrom / salaryTo / currency.
 *
 * 2. Для логики поиска (Khazaret):
 *    - Создать класс `SearchViewModel.kt`.
 *    - Внедрить интерфейс `VacanciesRepository` через Koin.
 *    - Метод `searchVacancies` возвращает `Flow<ApiResult<VacanciesSearchResult>>`.
 *    - Готовый маппер DTO -> Domain уже реализован в `data/converters/VacancyConverter.kt`. Повторно писать SearchMapper НЕ НУЖНО.
 */

/**
 * ИНФОРМАЦИЯ ДЛЯ РАЗРАБОТКИ ЭКРАНА ВЫБОРА РЕГИОНА (Ksenia):
 *
 * 1. Правило именования: Используем стандарт «ID + Name».
 *    - areaId: String (Идентификатор региона/города)
 *    - areaName: String (Название региона/города)
 *
 * 2. Модель данных (Domain):
 *    - Создать файл: domain/models/Area.kt
 *    - Структура: data class Area(val areaId: String, val areaName: String)
 *
 * 3. Названия классов для реализации:
 *    - AreaViewModel (Презентация)
 *    - AreaState (Состояния экрана)
 *    - AreaAdapter / AreaViewHolder (UI список)
 *
 * 4. Передача данных (Result API):
 *    - При возврате выбранного региона на экран фильтров использовать ключ "areaId".
 */

/**
 * СТАТУС РАЗРАБОТКИ БАЗЫ ДАННЫХ ДЛЯ ИЗБРАННОГО (Victor & Khazaret):
 *
 * 1. Проектирование VacancyEntity.kt (Victor):
 *    - Сущность создана, все 20 полей покрывают требования списков и деталей.
 *    - Настроен Primary Key: vacancy_id (тип String) для исключения дубликатов.
 *
 * 2. Создание AppDatabase.kt и FavoriteDao (Khazaret):
 *    - База данных создана (`data/db/AppDatabase.kt`).
 *    - Роль FavoriteDao выполняет `VacancyDao.kt`. Он полностью готов и интегрирован в базу данных.
 *
 * 3. Реализация методов insert, delete, getAll (Khazaret):
 *    В `VacancyDao.kt` реализованы методы:
 *      • insertVacancy(vacancy: VacancyEntity)
 *      • deleteVacancy(vacancyId: String)
 *      • getFavoriteVacancies(): List<VacancyEntity> (возвращает список всех избранных вакансий).
 *      • deleteAndInsertVacancy(...) внутри транзакции.
 *    - Все методы используют корутины (suspend) и готовы к вызову из репозитория.
 */
