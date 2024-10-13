package ru.practicum.android.diploma.navigate.api

import android.os.Bundle
import ru.practicum.android.diploma.navigate.state.NavigateEventState

interface VacancyApi<T : NavigateEventState> {
    fun createArgs(state: T): Bundle
}
