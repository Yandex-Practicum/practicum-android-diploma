package ru.practicum.android.diploma.domain

class Industry (
    val industryId: String,
    val industryName: String
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
