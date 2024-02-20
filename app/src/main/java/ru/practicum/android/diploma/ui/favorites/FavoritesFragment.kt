package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.model.VacancyModel
import ru.practicum.android.diploma.ui.search.adapter.VacanciesAdapter
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    private var onTrackClickDebounce: (VacancyModel) -> Unit = {}

    private val adapter = VacanciesAdapter(
        itemClickListener = { vacancy: VacancyModel -> onTrackClickDebounce(vacancy) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onTrackClickDebounce = debounce<VacancyModel>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vavancy ->
            findNavController().navigate(
                R.id.action_favoritesFragment_to_vacancyFragment3,
                bundleOf(VacancyFragment.ARGS_VACANCY to vavancy.id)
            )

        }

        binding.rvFavorites.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvFavorites.adapter = adapter

        viewModel.getState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FavoritesState) {
        when (state) {
            is FavoritesState.Content -> showContent(state.vacancies)
            is FavoritesState.Empty -> showEmpty()
            is FavoritesState.Error -> showError()
            is FavoritesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.apply {
            rvFavorites.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            loadingIndicator.isVisible = true
        }
    }

    private fun showError() {
        binding.apply {
            rvFavorites.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.ic_not_faund)
            tvPlaceholder.text = getString(R.string.can_not_get_list)
            ivPlaceholder.isVisible = true
            tvPlaceholder.isVisible = true
            loadingIndicator.isVisible = false
        }
    }

    private fun showEmpty() {
        binding.apply {
            rvFavorites.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.ic_nothing_in_favorites)
            tvPlaceholder.text = getString(R.string.empty_list)
            ivPlaceholder.isVisible = true
            tvPlaceholder.isVisible = true
            loadingIndicator.isVisible = false
        }
    }

    private fun showContent(vacancies: List<VacancyModel>) {
        binding.apply {
            rvFavorites.isVisible = true
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            loadingIndicator.isVisible = false
        }
        adapter.vacancies.clear()
        adapter.vacancies.addAll(vacancies)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        onTrackClickDebounce = {}
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 100L
        const val ARGS_VACANCY_ID = "VACANCY_ID"
    }
}
