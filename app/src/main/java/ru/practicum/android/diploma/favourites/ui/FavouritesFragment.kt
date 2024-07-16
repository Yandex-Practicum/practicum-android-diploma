package ru.practicum.android.diploma.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.search.ui.adapter.VacanciesAdapter
import ru.practicum.android.diploma.utils.Placeholder
import ru.practicum.android.diploma.vacancy.ui.VacancyFragment

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouritesViewModel by viewModel()

    private val vacanciesAdapter: VacanciesAdapter by lazy {
        VacanciesAdapter { vacancy ->
            findNavController().navigate(
                resId = R.id.action_favouritesFragment_to_vacancyFragment,
                args = VacancyFragment.createArguments(vacancy.id)
            )
        }
    }

    private var placeholder: Placeholder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAdapters()
        initializeObservers()
        initializeOther()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeAdapters() {
        binding.rvFavorites.adapter = vacanciesAdapter
    }

    private fun initializeObservers() {
        viewModel.screenState.observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is FavouritesState.Content -> showContent(screenState)
                FavouritesState.Error -> showError()
                FavouritesState.Loading -> showLoading()
            }
        }
    }

    private fun initializeOther() {
        placeholder = Placeholder(binding.tvPlaceholder)
    }

    private fun showContent(screenState: FavouritesState.Content) {
        if (screenState.favouritesList.isNotEmpty()) {
            vacanciesAdapter.setItems(screenState.favouritesList)
            placeholder?.hide()
        } else {
            vacanciesAdapter.clearItems()
            placeholder?.show(R.drawable.placeholder_empty_favorites, R.string.favorites_empty)
        }
        binding.progressBar.isVisible = false
    }

    private fun showError() {
        println("Error")
        placeholder?.show(R.drawable.placeholder_no_results_cat, R.string.search_no_results)
        binding.progressBar.isVisible = false
    }

    private fun showLoading() {
        println("Loading")
        binding.progressBar.isVisible = true
    }
}
