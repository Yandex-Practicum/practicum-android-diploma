package ru.practicum.android.diploma.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.api.ThemeSettingsInteractor

class RootViewModel(
    private val themeSettingsInteractor: ThemeSettingsInteractor
) : ViewModel() {
    private val _observeTheme = MutableLiveData<Boolean>()
    val observeTheme: LiveData<Boolean> get() = _observeTheme
    /*
    We install the theme when launching the application
    fun installTheme() {
        _observeTheme.postValue(themeSettingsInteractor.installTheme())
    }

    Switching the application theme
    fun switchTheme(theme:Boolean){
        _observeTheme.postValue(themeSettingsInteractor.switchTheme(theme))
    }
    */

}
