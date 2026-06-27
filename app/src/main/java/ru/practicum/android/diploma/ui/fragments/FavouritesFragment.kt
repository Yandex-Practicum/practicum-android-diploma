package ru.practicum.android.diploma.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.toVacancyCard
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyCard
import ru.practicum.android.diploma.presentation.viewmodels.FavoritesState
import ru.practicum.android.diploma.presentation.viewmodels.FavouritesViewModel
import ru.practicum.android.diploma.ui.adapter.VacancyAdapter

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModel()

    private var adapter: VacancyAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VacancyAdapter { clickOnVacancy(it) }
        binding.vacancyList.adapter = adapter

        observeViewModel()
        viewModel.fetchFavorites()
    }

    private fun observeViewModel() {
        viewModel.favoritesViewState().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: FavoritesState) {
        when (state) {
            is FavoritesState.Loading -> showLoading()
            is FavoritesState.Content -> showContent(state.vacancies)
            is FavoritesState.Empty -> showEmpty()
            is FavoritesState.Error -> showError()
        }
    }

    private fun showLoading() {
        binding.vacancyList.isVisible = false
        binding.noItemsPlaceholder.isVisible = false
        binding.errorPlaceholder.isVisible = false
    }

    private fun showEmpty() {
        binding.vacancyList.isVisible = false
        binding.errorPlaceholder.isVisible = false
        binding.noItemsPlaceholder.isVisible = true
    }

    private fun showError() {
        binding.vacancyList.isVisible = false
        binding.noItemsPlaceholder.isVisible = false
        binding.errorPlaceholder.isVisible = true
    }

    private fun showContent(vacancies: List<Vacancy>) {
        binding.vacancyList.isVisible = true
        binding.noItemsPlaceholder.isVisible = false
        binding.errorPlaceholder.isVisible = false
        adapter?.submitList(vacancies.map { it.toVacancyCard() })
    }

    private fun clickOnVacancy(vacancy: VacancyCard) {
        findNavController().navigate(
            R.id.action_favouritesFragment_to_vacancyDetailsFragment,
            VacancyDetailsFragment.createArgs(vacancy.vacancyId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter = null
    }
}
