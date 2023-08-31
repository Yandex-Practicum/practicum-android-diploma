package ru.practicum.android.diploma.similars

import android.view.View
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter

sealed interface SimilarVacanciesState {
    fun render(binding: FragmentSimilarsVacancyBinding, adapter: SearchAdapter?)

    object Empty : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding, adapter: SearchAdapter?) {
            binding.placeHolder.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE

        }
    }

    data class Content(val list: List<Vacancy>) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding, adapter: SearchAdapter?) {
            binding.placeHolder.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            adapter?.list = list
            adapter?.notifyDataSetChanged()

        }
    }
}