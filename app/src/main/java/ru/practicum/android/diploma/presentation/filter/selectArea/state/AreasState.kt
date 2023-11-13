package ru.practicum.android.diploma.presentation.filter.selectArea.state

import ru.practicum.android.diploma.domain.models.filter.Area


sealed interface AreasState {
    class Error(val errorText: String): AreasState
    class DisplayAreas(val areas: ArrayList<Area>): AreasState
}