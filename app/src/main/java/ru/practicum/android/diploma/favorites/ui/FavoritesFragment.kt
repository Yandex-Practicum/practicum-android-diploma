package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.presentation.FavoritesUiState
import ru.practicum.android.diploma.favorites.presentation.FavoritesViewModel
import ru.practicum.android.diploma.search.domain.models.VacancyPreview
import ru.practicum.android.diploma.search.ui.VacanciesAdapter
import ru.practicum.android.diploma.util.BindingFragment

class FavoritesFragment : BindingFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModel()
    private lateinit var vacanciesAdapter: VacanciesAdapter

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRV()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStateFlow.collect {
                render(it)
            }
        }
    }

    private fun render(state: FavoritesUiState) {
        when (state) {
            is FavoritesUiState.Content -> showFavoriteVacancies(state.list)

            FavoritesUiState.Default -> renderDefaultState()

            FavoritesUiState.Empty -> showPlaceholder(
                R.drawable.placeholder_favorites_empty,
                R.string.empty_list
            )

            FavoritesUiState.Failure -> showPlaceholder(
                R.drawable.placeholder_error,
                R.string.no_vacancies
            )
        }
    }

    private fun renderDefaultState() {
        with(binding) {
            favPlaceholderIcon.isVisible = false
            favPlaceholderMessage.isVisible = false
        }
    }

    private fun showFavoriteVacancies(list: List<VacancyPreview>){
        vacanciesAdapter.vacancies = list
        with(binding){
            favRvVacancies.apply{
                adapter?.notifyDataSetChanged()
                isVisible = true
            }
            favPlaceholderIcon.isVisible = false
            favPlaceholderMessage.isVisible = false
        }
    }

    private fun showPlaceholder(
        imageRes: Int,
        textRes: Int
    ) {
        with(binding) {
            favRvVacancies.isVisible = false
            favPlaceholderIcon.apply {
                isVisible = true
                setImageResource(imageRes)
            }
            favPlaceholderMessage.apply {
                isVisible = true
                setText(textRes)
            }
        }
    }

    private fun initializeRV() {
        vacanciesAdapter = VacanciesAdapter { element -> {/*пока ничего не происходит*/}}
            .apply{
                vacancies = emptyList()
            }
        binding.favRvVacancies.adapter = vacanciesAdapter
    }
}
