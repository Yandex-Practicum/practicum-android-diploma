package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.presentation.favorites.FavoritesScreenState
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyFavoriteAdapter
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModel()
    private var adapter: VacancyFavoriteAdapter? = null
    private var onVacancyClickDebounce: (String) -> Unit = { _ -> }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadVacancies()
        initAdapter()
        setupObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        initDebouncedVacancyClick()
        adapter = VacancyFavoriteAdapter { id ->
            onVacancyClickDebounce(id)
        }
        binding.rvVacancies.adapter = adapter
        binding.rvVacancies.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun showContent(vacancies: List<VacanciesPreview>) {
        adapter?.submitList(vacancies)
        binding.placeholder.isVisible = false
        binding.rvVacancies.isVisible = true
    }

    private fun showEmpty() {
        binding.rvVacancies.isVisible = false
        binding.placeholder.isVisible = true
    }

    private fun showError() {
        binding.rvVacancies.isVisible = false
        binding.placeholder.isVisible = true
        binding.placeholder.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.error_cat_placeholder, 0, 0)
        binding.placeholder.setText(R.string.failed_to_get_a_list_of_vacancies)
    }

    private fun render(state: FavoritesScreenState) {
        when (state) {
            is FavoritesScreenState.Content -> showContent(state.vacancies)
            FavoritesScreenState.Empty -> showEmpty()
            FavoritesScreenState.Error -> showError()
        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { state ->
                    render(state)
                }
            }
        }
    }

    private fun initDebouncedVacancyClick() {
        onVacancyClickDebounce = debounce(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { id ->
            lifecycleScope.launch {
                val direction = FavoritesFragmentDirections
                    .actionFavoritesFragmentToVacancyDetailsFragment(vacancyId = id)
                findNavController().navigate(direction)
            }
        }
    }

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 500L
    }
}

