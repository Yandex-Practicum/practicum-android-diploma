package ru.practicum.android.diploma.domain.api

interface ThemeSettingsInteractor {
    fun installTheme():Boolean
    fun switchTheme(themeBooleanValue:Boolean):Boolean
}
