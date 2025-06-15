package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.ui.favorite.adapters.FavoriteAdapter
import ru.practicum.android.diploma.ui.favorite.models.FavoriteState
import ru.practicum.android.diploma.ui.favorite.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.debounce

class FavoriteFragment : BindingFragment<FragmentFavoriteBinding>() {
    private val viewModel: FavoriteViewModel by viewModel()
    private var favoriteAdapter: FavoriteAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onFavoriteClickDebounce = debounce<VacancyDetail>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy -> clickToFavorite(vacancy) }

        favoriteAdapter = FavoriteAdapter(
            object : FavoriteAdapter.FavoriteClickListener {
                override fun onFavoriteClick(vacancy: VacancyDetail) {
                    onFavoriteClickDebounce(vacancy)
                }
            }
        )

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getFavoriteList()
    }

    private fun clickToFavorite(vacancy: VacancyDetail) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacancyFragment,
            //VacancyFragment.createArgs(vacancy.id)
        )
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Context -> showContext(state.vacancies)
            is FavoriteState.EmptyList -> showEmpty()
            is FavoriteState.Error -> error(state.message)
            is FavoriteState.Loading -> loading()
        }
    }

    private fun showContext(vacancies: List<VacancyDetail>) {
        return
    }

    private fun showEmpty() {
        binding.placeholder.isVisible = true
        binding.progress.isVisible = false
    }

    private fun loading() {
        binding.placeholder.isVisible = false
        binding.progress.isVisible = true
    }

    private fun error(message: String) {
        return
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
