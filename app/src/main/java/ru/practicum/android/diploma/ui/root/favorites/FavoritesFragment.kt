package ru.practicum.android.diploma.ui.root.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.vmodels.FavoritesViewModel
import ru.practicum.android.diploma.ui.root.favorites.models.FavoritesState
import ru.practicum.android.diploma.ui.search.VacanciesAdapter

class FavoritesFragment : Fragment() {
    private val viewModel by viewModel<FavoritesViewModel>()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val adapter = VacanciesAdapter { vacancy ->
        findNavController().navigate(
            R.id.action_favoritesFragment_to_vacancyDetailFragment,
            bundleOf(
                ARGS_VACANCY_ID_BY_DB to vacancy.id
             )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        viewModel.getListAllFavorites()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state ->
            renderUi(state = state)
        }
    }

    private fun renderUi(state: FavoritesState) {
        when (state) {
            FavoritesState.Idle -> {
                renderRecycler(false)
                renderGroupImageText(false, state)
                renderProgressBar(false)
            }

            FavoritesState.Loading -> {
                renderRecycler(false)
                renderGroupImageText(false, state)
                renderProgressBar(true)
            }

            is FavoritesState.Complete -> {
                adapter.submitVacancies(state.data)
                renderGroupImageText(false, state)
                renderRecycler(true)
                renderProgressBar(false)
            }

            is FavoritesState.Nothing -> {
                renderRecycler(false)
                renderGroupImageText(true, state)
                renderProgressBar(false)
            }

            is FavoritesState.Error -> {
                renderRecycler(false)
                renderGroupImageText(true, state)
                renderProgressBar(false)
            }
        }
    }

    private fun renderGroupImageText(value: Boolean, state: FavoritesState) {
        binding.groupImageText.isVisible = value
        if (value) {
            when (state) {
                FavoritesState.Nothing -> {
                    binding.textView.text = getString(R.string.empty_list)
                    binding.imageView.setImageResource(R.drawable.image_empty_list_database_)
                }

                else -> {
                    binding.imageView.setImageResource(R.drawable.cat_with_the_plate)
                    binding.textView.text = getString(R.string.error_database_list)
                }
            }
        }
    }

    private fun renderProgressBar(value: Boolean) {
        binding.loadingProgressBar.isVisible = value
    }

    private fun renderRecycler(value: Boolean) {
        binding.recycler.isVisible = value
    }

    private companion object{
        private const val ARGS_VACANCY_ID_BY_DB = "vacancyIdByDatabase"
    }
}
