package ru.practicum.android.diploma.presentation.similar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarVacanciesBinding
import ru.practicum.android.diploma.domain.SearchState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.detail.DetailFragment
import ru.practicum.android.diploma.presentation.search.SearchAdapter
import ru.practicum.android.diploma.presentation.search.SearchFragment
import ru.practicum.android.diploma.util.debounce

class SimilarVacanciesFragment : Fragment() {
    val viewModel by viewModel<SimilarViewModel>()
    private var _binding: FragmentSimilarVacanciesBinding? = null
    private val binding get() = _binding!!
    lateinit var onItemClickDebounce: (Vacancy) -> Unit
    private val vacancies = mutableListOf<Vacancy>()
    private val adapter = SearchAdapter(vacancies) { vacanciy ->
        onItemClickDebounce(vacanciy)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimilarVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.similarVacanciesRecyclerView.adapter = adapter
        onItemClickDebounce = debounce(
            SearchFragment.CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            DetailFragment.addArgs(vacancy.id)
            findNavController().navigate(R.id.action_similarVacanciesFragment_to_detailFragment)
        }
        viewModel.searchVacancy(vacancyId)
        binding.similarVacanciesBackArrowImageview.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.Content -> showContent(state.vacancies)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.similarVacanciesRecyclerView.isVisible = false
        binding.placeholderMessage.isVisible = false
    }

    private fun showContent(searchVacancies: List<Vacancy>) {
        binding.progressBar.isVisible = false
        binding.similarVacanciesRecyclerView.isVisible = true
        binding.placeholderMessage.isVisible = false
        vacancies.clear()
        vacancies.addAll(searchVacancies)
        adapter.notifyDataSetChanged()
    }

    private fun showEmpty(message: String) {
        binding.progressBar.isVisible = false
        binding.similarVacanciesRecyclerView.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageImage.setImageResource(R.drawable.search_placeholder_nothing_found)
        binding.placeholderMessageText.text = message
    }

    private fun showError(errorMessage: String) {
        binding.progressBar.isVisible = false
        binding.similarVacanciesRecyclerView.isVisible = false
        binding.placeholderMessage.isVisible = true
        binding.placeholderMessageText.text = errorMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        var vacancyId: String = ""
        fun addArgs(id: String) {
            vacancyId = id
        }
    }
}