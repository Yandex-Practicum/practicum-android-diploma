package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.ui.favorite.adapters.FavoriteAdapter
import ru.practicum.android.diploma.ui.favorite.models.FavoriteState
import ru.practicum.android.diploma.ui.favorite.utils.VacanciesCallback
import ru.practicum.android.diploma.ui.favorite.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.vacancy.VacancyFragment
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
        val onFavoriteClickDebounce = debounce<VacancyDetails>(
            CLICK_DEBOUNCE_DELAY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy -> clickToFavorite(vacancy) }

        favoriteAdapter = FavoriteAdapter(
            object : FavoriteAdapter.FavoriteClickListener {
                override fun onFavoriteClick(vacancy: VacancyDetails) {
                    onFavoriteClickDebounce(vacancy)
                }
            }
        )

        binding.favoriteResults.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favoriteResults.adapter = favoriteAdapter

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteList()
    }

    private fun clickToFavorite(vacancy: VacancyDetails) {
        (activity as RootActivity).setNavBarVisibility(false)
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancy.vacancy.id)
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

    private fun showContext(vacancies: List<VacancyDetails>) {
        binding.favoriteResults.isVisible = true
        binding.placeholder.isVisible = false
        binding.progress.isVisible = false
        favoriteAdapter?.let {
            val diffCallback = VacanciesCallback(it.vacancies.toList(), vacancies)
            val diffVacancies = DiffUtil.calculateDiff(diffCallback)
            it.vacancies.clear()
            it.vacancies.addAll(vacancies)
            diffVacancies.dispatchUpdatesTo(it)
        }
    }

    private fun showEmpty() {
        binding.favoriteResults.isVisible = false
        loadPlaceholder(R.drawable.err_empty_list, R.string.empty_list)
        binding.placeholder.isVisible = true
        binding.progress.isVisible = false
    }

    private fun loading() {
        binding.favoriteResults.isVisible = false
        binding.placeholder.isVisible = false
        binding.progress.isVisible = true
    }

    private fun error(message: String) {
        binding.favoriteResults.isVisible = false
        loadPlaceholder(R.drawable.err_wtf_cat, R.string.cant_get_vacations_list)
        binding.placeholder.isVisible = true
        binding.progress.isVisible = false
    }

    private fun loadPlaceholder(resourceIdImage: Int, resourceIdText: Int) {
        Glide.with(requireContext())
            .load(resourceIdImage)
            .placeholder(R.drawable.err_empty_list)
            .into(binding.placeHolderImg)
        binding.placeHolderText.text = resources.getString(resourceIdText)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
