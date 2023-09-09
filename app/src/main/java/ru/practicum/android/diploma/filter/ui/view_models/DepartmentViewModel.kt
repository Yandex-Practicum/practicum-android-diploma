package ru.practicum.android.diploma.filter.ui.view_models

import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import javax.inject.Inject

class DepartmentViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) :  AreasViewModel(logger, filterInteractor){
}