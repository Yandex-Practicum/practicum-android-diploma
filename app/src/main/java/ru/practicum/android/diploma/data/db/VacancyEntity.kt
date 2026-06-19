package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "fav_vacancies_table")
@TypeConverters(StringListConverter::class)
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "vacancy_id")
    val vacancyId: String, // ID вакансии
    val vacancyName: String, // Название вакансии
    @Embedded
    val employer: EmployerEntity?, // Данные работодателя
    @Embedded
    val area: AreaEntity?, // Данные региона (города)
    @Embedded
    val salary: SalaryEntity?, // Данные о зарплате
    val description: String?, // Описание вакансии (HTML)
    val experienceName: String?, // Требуемый опыт работы
    val scheduleName: String?, // График работы
    val employmentName: String?, // Тип занятости
    val addressRaw: String?, // Полный адрес
    @TypeConverters(StringListConverter::class)
    val skills: List<String>?, // Список ключевых навыков
    @Embedded
    val contacts: ContactsEntity?, // Контактная информация
    val shareUrl: String? // Ссылка на вакансию для шеринга
)

data class EmployerEntity(
    val employerId: String?, // ID работодателя
    @ColumnInfo(name = "company_name")
    val companyName: String?, // Название компании
    val logoUrl: String? // Ссылка на логотип компании
)

data class AreaEntity(
    val areaId: String?, // ID региона (города)
    val areaName: String? // Название региона (города)
)

data class SalaryEntity(
    @ColumnInfo(name = "salary_from")
    val salaryFrom: Long?, // Зарплата "от"
    val salaryTo: Long?, // Зарплата "до"
    val currency: String? // Валюта зарплаты
)

data class ContactsEntity(
    val contactName: String?, // Имя контактного лица
    val contactEmail: String?, // Почта контакта
    val phoneFormatted: String? // Телефон контакта
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
