package ru.practicum.android.diploma.similars

import android.view.View
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.thisName

sealed interface SimilarVacanciesState {
    fun showContent(
        binding: FragmentSimilarsVacancyBinding,
        viewModel: SimilarVacanciesViewModel
    )

    fun showPlaceholder(
        binding: FragmentSimilarsVacancyBinding,
        viewModel: SimilarVacanciesViewModel
    )

    object Empty : SimilarVacanciesState {
        override fun showContent(
            binding: FragmentSimilarsVacancyBinding,
            viewModel: SimilarVacanciesViewModel
        ) {
        }

        override fun showPlaceholder(
            binding: FragmentSimilarsVacancyBinding,
            viewModel: SimilarVacanciesViewModel
        ) {}
    }

    data class Content(val list: List<Vacancy>) : SimilarVacanciesState {
        override fun showContent(
            binding: FragmentSimilarsVacancyBinding,
            viewModel: SimilarVacanciesViewModel
        ) {
            viewModel.log(thisName, "showContent(list: size=${list.size})")
            binding.placeHolder.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
        }

        override fun showPlaceholder(
            binding: FragmentSimilarsVacancyBinding,
            viewModel: SimilarVacanciesViewModel
        ) {
            viewModel.log(thisName, "showPlaceholder()")
            binding.placeHolder.visibility = View.VISIBLE
            binding.recycler.visibility = View.GONE
        }
    }
}