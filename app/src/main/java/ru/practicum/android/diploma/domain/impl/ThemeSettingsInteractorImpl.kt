package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.ThemeSettingsInteractor
import ru.practicum.android.diploma.domain.api.ThemeSettingsRepository

class ThemeSettingsInteractorImpl(private val themeSettingsRepository: ThemeSettingsRepository) :
    ThemeSettingsInteractor {
    override fun installTheme(): Boolean {
        return themeSettingsRepository.installTheme()
    }

    override fun switchTheme(themeBooleanValue: Boolean): Boolean {
        return themeSettingsRepository.switchTheme(themeBooleanValue)
    }
}
