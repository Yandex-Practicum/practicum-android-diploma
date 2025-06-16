package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.ui.favorite.adapters.FavoriteAdapter
import ru.practicum.android.diploma.ui.favorite.models.FavoriteState
import ru.practicum.android.diploma.ui.favorite.utils.VacanciesCallback
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
        Log.d("HH_LOG", "Show context")
        binding.favoriteResults.isVisible = true
        favoriteAdapter?.let {
            val diffCallback = VacanciesCallback(it.vacancies.toList(), vacancies)
            val diffVacancies = DiffUtil.calculateDiff(diffCallback)
            it.vacancies.clear()
            it.vacancies.addAll(vacancies)
            diffVacancies.dispatchUpdatesTo(it)
        }
    }

    private fun showEmpty() {
        Log.d("HH_LOG", "Empty")
        binding.favoriteResults.isVisible = false
        Glide.with(requireContext())
            .load(R.drawable.favorite_empty)
            .placeholder(R.drawable.placeholder_32px)
            .into(binding.placeHolderImg)
        binding.placeHolderText.text = resources.getString(R.string.empty_list)
        binding.placeholder.isVisible = true
        binding.progress.isVisible = false
    }

    private fun loading() {
        Log.d("HH_LOG", "Loading")
        binding.favoriteResults.isVisible = false
        binding.placeholder.isVisible = false
        binding.progress.isVisible = true
    }

    private fun error(message: String) {
        Log.d("HH_LOG", "Error")
        binding.favoriteResults.isVisible = false
        Glide.with(requireContext())
            .load(R.drawable.placeholder_not_find)
            .placeholder(R.drawable.placeholder_32px)
            .into(binding.placeHolderImg)
        binding.placeHolderText.text = resources.getString(R.string.cant_get_vacations_list)
        binding.placeholder.isVisible = true
        binding.progress.isVisible = false
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
