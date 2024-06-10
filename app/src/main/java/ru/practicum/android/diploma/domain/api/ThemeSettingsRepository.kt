package ru.practicum.android.diploma.domain.api

interface ThemeSettingsRepository {
    fun installTheme(): Boolean
    fun switchTheme(themeBooleanValue: Boolean): Boolean
}
