package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.filter.domain.models.FilterVacancy

sealed interface  BaseFilterScreenState {

    fun render(binding: FragmentFilterBaseBinding)

    object Empty : BaseFilterScreenState {
        override fun render(binding: FragmentFilterBaseBinding) {

        }
    }

    data class Content(val filterVacancy: FilterVacancy) : BaseFilterScreenState {
        override fun render(binding: FragmentFilterBaseBinding) {
            TODO("Not yet implemented")
        }
    }
}