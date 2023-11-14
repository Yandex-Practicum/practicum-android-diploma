package ru.practicum.android.diploma.presentation.filter.chooseArea.state

import ru.practicum.android.diploma.domain.models.filter.Industry


sealed interface IndustriesState {
    class Error(val errorText: String): IndustriesState
    class DisplayIndustries(val industries: ArrayList<Industry>): IndustriesState
}