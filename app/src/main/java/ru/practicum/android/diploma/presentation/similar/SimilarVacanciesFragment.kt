package ru.practicum.android.diploma.presentation.similar

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.SearchState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.ModelFragment
import ru.practicum.android.diploma.presentation.SalaryPresenter
import ru.practicum.android.diploma.presentation.search.SearchAdapter
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY_MILLIS
import ru.practicum.android.diploma.util.debounce

class SimilarVacanciesFragment : ModelFragment() {
    private val viewModel by viewModel<SimilarViewModel>()
    private val salaryPresenter: SalaryPresenter by inject()
    lateinit var onItemClickDebounce: (Vacancy) -> Unit
    private val vacancies = mutableListOf<Vacancy>()
    private val adapter = SearchAdapter(vacancies, salaryPresenter) { vacancy ->
        onItemClickDebounce(vacancy)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarInclude.headerTitle.text = getString(R.string.similar_vacancies)
        binding.container.isVisible = false
        binding.RecyclerView.adapter = adapter
        onItemClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            val bundle = bundleOf("vacancyId" to vacancy.id)
            findNavController().navigate(
                R.id.action_similarVacanciesFragment_to_detailFragment,
                bundle
            )
        }
        val vacancyId = arguments?.getString("vacancyId") ?: ""
        viewModel.searchVacancy(vacancyId)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.Content -> showContent(state.vacancies)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.RecyclerView.isVisible = false
        binding.placeholderMessage.isVisible = false
    }

    private fun showContent(searchVacancies: List<Vacancy>) {
        binding.progressBar.isVisible = false
        binding.RecyclerView.isVisible = true
        binding.placeholderMessage.isVisible = false
        vacancies.clear()
        vacancies.addAll(searchVacancies)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty(message: String) {
        binding.progressBar.isVisible = false
        binding.RecyclerView.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageImage.setImageResource(R.drawable.search_placeholder_nothing_found)
        binding.placeholderMessageText.text = message
    }

    private fun showError(errorMessage: String) {
        binding.apply {
            progressBar.isVisible = false
            RecyclerView.isVisible = false
            placeholderMessage.isVisible = true
            if (errorMessage == getString(R.string.no_internet)) placeholderMessageImage.setImageResource(R.drawable.no_internet_error)
            else placeholderMessageImage.setImageResource(R.drawable.server_error2)
                placeholderMessageText.text = errorMessage
        }
    }
}