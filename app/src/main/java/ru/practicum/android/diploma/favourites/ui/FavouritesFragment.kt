package ru.practicum.android.diploma.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.search.ui.adapter.VacanciesAdapter
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

    private fun showContent(screenState: FavouritesState.Content) {
        if (screenState.favouritesList.isNotEmpty()) {
            vacanciesAdapter.setItems(screenState.favouritesList)
            hidePlaceholder()
        } else {
            vacanciesAdapter.clearItems()
            showPlaceholder(R.drawable.placeholder_empty_favorites, R.string.favorites_empty)
        }
        binding.progressBar.isVisible = false
    }

    private fun showError() {
        println("Error")
        showPlaceholder(R.drawable.placeholder_no_results_cat, R.string.search_no_results)
        binding.progressBar.isVisible = false
    }

    private fun showLoading() {
        println("Loading")
        binding.progressBar.isVisible = true
    }

    private fun showPlaceholder(@DrawableRes drawableResId: Int, @StringRes textResId: Int? = null) {
        with(binding.tvPlaceholder) {
            isVisible = true
            setCompoundDrawablesWithIntrinsicBounds(0, drawableResId, 0, 0)

            textResId?.let { setText(it) } ?: run { text = null }
        }
    }

    private fun hidePlaceholder() {
        binding.tvPlaceholder.isVisible = false
    }
}
