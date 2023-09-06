package ru.practicum.android.diploma.similars

import android.view.View
import android.widget.Toast
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.details.ui.DetailsScreenState
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter

sealed interface SimilarVacanciesState {
    fun render(binding: FragmentSimilarsVacancyBinding)

    object Empty : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.iwAnim.visibility = View.GONE
            binding.recycler.visibility = View.GONE
            binding.placeHolder.visibility = View.VISIBLE
        }
    }

    data class Content(val list: List<Vacancy>) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.placeHolder.visibility = View.GONE
            binding.iwAnim.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            val adapter = (binding.recycler.adapter as SearchAdapter)
            adapter.list = list
            adapter.notifyDataSetChanged()
        }
    }

    data class Offline(val message: String) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.placeHolderText.text = message
            binding.placeHolder.visibility = View.VISIBLE
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    data class Error(val message: String) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.iwAnim.visibility = View.GONE
            binding.placeHolder.visibility = View.VISIBLE
            binding.placeHolderText.text = message
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

     object Loading : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            binding.iwAnim.visibility = View.VISIBLE
        }
    }
}