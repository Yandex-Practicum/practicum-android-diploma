package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.SearchAdapter
import ru.practicum.android.diploma.util.debounce

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter: SearchAdapter by lazy {
        val onVacancyClick = debounce<Vacancy>(
            delayMillis = 500L,
            coroutineScope = lifecycleScope,
            false
        ) { vacancy ->
            val action = FavoritesFragmentDirections.actionFavoritesToVacancyDetails(vacancy.id)
            findNavController().navigate(action)
        }
        SearchAdapter(onVacancyClick)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupAdapter()
        setupObservers()
    }

    private fun setupUI() {
        binding.includeToolbar.btnBack.visibility = View.GONE
        binding.includeToolbar.toolbar.contentInsetStartWithNavigation =
            resources.getDimensionPixelSize(R.dimen.indent_16)

        binding.includeToolbar.toolbar.title = getString(R.string.favorites)
    }

    private fun setupAdapter() {
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    private fun renderState(state: FavoritesState) {
        when (state) {
            is FavoritesState.Loading -> {
                showLoading()
            }

            is FavoritesState.Content -> {
                showContent(state.vacancies)
            }

            is FavoritesState.Empty -> {
                showEmpty()
            }

            is FavoritesState.Error -> {
                showError()
            }
        }
    }

    private fun showLoading() {
        binding.placeholderFavorites.visibility = View.GONE
        binding.textImageCaptionFavorites.visibility = View.GONE
        binding.recyclerViewFavorites.visibility = View.GONE
    }

    private fun showContent(vacancies: List<Vacancy>) {
        binding.placeholderFavorites.visibility = View.GONE
        binding.textImageCaptionFavorites.visibility = View.GONE
        binding.recyclerViewFavorites.visibility = View.VISIBLE
        adapter.setVacancies(vacancies, true)
    }

    private fun showEmpty() {
        binding.placeholderFavorites.setImageResource(R.drawable.img_list_empty)
        binding.textImageCaptionFavorites.text = getString(R.string.error_empty_vacancy_list)
        binding.placeholderFavorites.visibility = View.VISIBLE
        binding.textImageCaptionFavorites.visibility = View.VISIBLE
        binding.recyclerViewFavorites.visibility = View.GONE
    }

    private fun showError() {
        binding.placeholderFavorites.setImageResource(R.drawable.img_nothing_found)
        binding.textImageCaptionFavorites.text = getString(R.string.error_unable_to_retr_vac_list)
        binding.placeholderFavorites.visibility = View.VISIBLE
        binding.textImageCaptionFavorites.visibility = View.VISIBLE
        binding.recyclerViewFavorites.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
