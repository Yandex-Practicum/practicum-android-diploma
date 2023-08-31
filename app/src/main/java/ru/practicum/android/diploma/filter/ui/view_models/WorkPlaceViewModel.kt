package ru.practicum.android.diploma.filter.ui.view_models

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {
}