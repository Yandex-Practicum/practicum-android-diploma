package ru.practicum.android.diploma.similars

import android.view.View
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter

sealed interface SimilarVacanciesState {
    fun render(binding: FragmentSimilarsVacancyBinding)

    object Empty : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.placeHolder.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        }
    }

    data class Content(val list: List<Vacancy>) : SimilarVacanciesState {
        override fun  render(binding: FragmentSimilarsVacancyBinding) {
            binding.placeHolder.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            val adapter = (binding.recycler.adapter as SearchAdapter)
            adapter.list = list
            adapter.notifyDataSetChanged()
        }
    }
}