package ru.practicum.android.diploma.navigate.api

import android.os.Bundle
import ru.practicum.android.diploma.navigate.state.NavigateEventState

interface NavigateArgsToVacancy<T : NavigateEventState> {
    fun createArgs(state: T): Bundle
}
