package ru.practicum.android.diploma.filter.ui.view_models

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.data.SharedPrefsStorage
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class BaseFilterViewModel @Inject constructor(
    val sharedPrefsStorage: SharedPrefsStorage,
    logger: Logger
) : BaseViewModel(logger) {
}