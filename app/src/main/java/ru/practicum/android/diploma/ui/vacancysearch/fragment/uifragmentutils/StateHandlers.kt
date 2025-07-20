package ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils

import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.util.Debouncer

data class StateHandlers(
    val debouncer: Debouncer?,
    val debounceForPlaceholder: Debouncer?,
    val viewModel: VacanciesSearchViewModel,
    val vacancyList: MutableList<*>
)
