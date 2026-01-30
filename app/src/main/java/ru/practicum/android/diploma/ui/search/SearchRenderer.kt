package ru.practicum.android.diploma.ui.search

import android.content.Context
import androidx.core.view.isVisible
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class SearchRenderer(
    private val binding: FragmentSearchBinding,
    private val context: Context,
    private val adapter: SearchAdapter,
    private val hideKeyboard: () -> Unit
) {

    fun render(state: SearchState) = with(binding) {
        when (state) {
            is SearchState.Initial -> renderInitial()
            is SearchState.Loading -> renderLoading()
            is SearchState.Content -> renderContent(state)
            is SearchState.Empty -> renderEmpty()
        }
    }

    private fun renderInitial() = with(binding) {
        textImageCaption.isVisible = false
        resultSearchInformation.isVisible = false
        recyclerView.isVisible = false
        progressBar1.isVisible = false
        progressBar2.isVisible = false
        placeholderSearch.setImageResource(R.drawable.img_empty_search)
        placeholderSearch.isVisible = true
    }

    private fun renderLoading() = with(binding) {
        placeholderSearch.isVisible = false
        textImageCaption.isVisible = false
        resultSearchInformation.isVisible = false
        recyclerView.isVisible = false
        progressBar2.isVisible = false
        progressBar1.isVisible = true
    }

    private fun renderContent(state: SearchState.Content) = with(binding) {
        hideKeyboard()

        val count = state.totalFound
        resultSearchInformation.text = context.resources.getQuantityString(
            R.plurals.vacancies_found,
            count,
            count
        )

        placeholderSearch.isVisible = false
        textImageCaption.isVisible = false
        progressBar1.isVisible = false
        progressBar2.isVisible = false
        resultSearchInformation.isVisible = true
        recyclerView.isVisible = true

        adapter.setVacancies(state.vacancies)
    }

    private fun renderEmpty() = with(binding) {
        hideKeyboard()
        resultSearchInformation.text = context.getString(R.string.no_vacancy)
        resultSearchInformation.isVisible = true
        recyclerView.isVisible = false
        progressBar1.isVisible = false
        progressBar2.isVisible = false

        placeholderSearch.setImageResource(R.drawable.img_nothing_found)
        placeholderSearch.isVisible = true
        textImageCaption.text =
            context.getString(R.string.error_unable_to_retr_vac_list)
        textImageCaption.isVisible = true
    }
}
