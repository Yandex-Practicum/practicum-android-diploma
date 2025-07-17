package ru.practicum.android.diploma.ui.favouritevacancies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FavouriteVacanciesFragmentBinding
import ru.practicum.android.diploma.presentation.favouritevacancies.uistate.FavouriteVacanciesUiState
import ru.practicum.android.diploma.presentation.favouritevacancies.viewmodel.FavouriteVacanciesViewModel
import ru.practicum.android.diploma.presentation.models.vacancies.VacancyUiModel
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter
import ru.practicum.android.diploma.util.DebounceConstants.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Debouncer

class FavouriteVacanciesFragment : Fragment(), VacancyItemAdapter.Listener {

    private var _binding: FavouriteVacanciesFragmentBinding? = null
    private val binding get() = _binding!!

    private val vacancies = mutableListOf<VacancyUiModel>()
    private val adapter = VacancyItemAdapter(this)

    private val debounce by lazy {
        Debouncer(viewLifecycleOwner.lifecycleScope, SEARCH_DEBOUNCE_DELAY)
    }

    private val favouriteVacanciesViewModel by viewModel<FavouriteVacanciesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FavouriteVacanciesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.toolbarTitle.text = getString(R.string.favorite)

        binding.recyclerViewFavourite.adapter = adapter
        binding.recyclerViewFavourite.layoutManager = LinearLayoutManager(requireContext())

        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        favouriteVacanciesViewModel.refreshVacancies()
    }

    private fun observeViewModel() {
        favouriteVacanciesViewModel.favouriteUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouriteVacanciesUiState.Content -> {
                    if (state.vacancies.isEmpty()) {
                        showPlaceholder(
                            R.drawable.empty_list_favorites_placeholder,
                            R.string.empty_list
                        )
                    } else {
                        showVacancies(state.vacancies)
                    }
                }
                is FavouriteVacanciesUiState.Placeholder -> {
                    showPlaceholder(state.drawable, state.message)
                }
            }
        }
    }

    private fun showVacancies(vacanciesList: List<VacancyUiModel>) {
        binding.recyclerViewFavourite.isVisible = true
        binding.favouritePlaceholder.isVisible = false

        adapter.submitList(vacanciesList)
    }

    private fun showPlaceholder(imageRes: Int, message: Int) {
        binding.recyclerViewFavourite.isVisible = false
        binding.favouritePlaceholder.isVisible = true

        binding.favouriteCoverPlaceholder.setImageResource(imageRes)
        binding.favouriteTextPlaceholder.setText(message)
    }

    override fun onClick(id: String) {
        val action = FavouriteVacanciesFragmentDirections.actionFavouriteVacanciesFragmentToVacancyDetailsFragment(id)
        findNavController().navigate(action)
    }
}
