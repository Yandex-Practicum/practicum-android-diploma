package ru.practicum.android.diploma.similars

import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.details.ui.DetailsScreenState
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter

sealed interface SimilarVacanciesState {
    fun render(binding: FragmentSimilarsVacancyBinding)
    
    private fun isScrollingEnabled(binding: FragmentSimilarsVacancyBinding, isEnable: Boolean) {
        
        with(binding) {
            val toolbarLayoutParams: AppBarLayout.LayoutParams =
                similarVacancyToolbar.layoutParams as AppBarLayout.LayoutParams
            
            if (!isEnable) {
                toolbarLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            } else {
                toolbarLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }
            
            similarVacancyToolbar.layoutParams = toolbarLayoutParams
        }
    }

    object Empty : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            super.isScrollingEnabled(binding, false)
            binding.iwAnim.visibility = View.GONE
            binding.recycler.visibility = View.GONE
            binding.placeHolder.visibility = View.VISIBLE
        }
    }

    data class Content(val list: List<Vacancy>) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            super.isScrollingEnabled(binding, true)
            binding.placeHolder.visibility = View.GONE
            binding.iwAnim.visibility = View.GONE
            binding.recycler.visibility = View.VISIBLE
            val adapter = (binding.recycler.adapter as SearchAdapter)
            adapter.submitList(list)
        }
    }

    data class Offline(val message: String) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            super.isScrollingEnabled(binding, false)
            binding.placeHolder.text = message
            binding.placeHolder.visibility = View.VISIBLE
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    data class Error(val message: String) : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            super.isScrollingEnabled(binding, false)
            binding.iwAnim.visibility = View.GONE
            binding.placeHolder.visibility = View.VISIBLE
            binding.placeHolder.text = message
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

     object Loading : SimilarVacanciesState {
        override fun render(binding: FragmentSimilarsVacancyBinding) {
            super.isScrollingEnabled(binding, false)
            binding.iwAnim.visibility = View.VISIBLE
            binding.placeHolder.visibility = View.GONE
        }
    }
}