package ru.practicum.android.diploma.favorites.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.favorites.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.presentation.ui.adapters.FavoriteAdapter
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.favorites.presentation.viewmodel.state.FavoriteState
import ru.practicum.android.diploma.navigate.observable.Navigate
import ru.practicum.android.diploma.navigate.state.NavigateEventState

private const val DELAY_CLICK_VACANCY = 250L
internal class FavoritesFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val navigate: Navigate<NavigateEventState> by inject()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

    private var vacancyClickDebounce: ((Int) -> Unit)? = null

    private var listFavoriteVacancy: MutableList<FavoriteVacancy> = mutableListOf()

    private val favoriteAdapter: FavoriteAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteAdapter(listFavoriteVacancy) {
            favoriteVacancySelection(it)
        }
    }

    private fun favoriteVacancySelection(vacancy: FavoriteVacancy) {
        vacancyClickDebounce?.let { it(vacancy.idVacancy) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewArray = arrayOf(
            binding.favoriteList,
            binding.placeholderEmptyList,
            binding.placeholderErrorList
        )
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.clearVacancies()

        initDebounces()

        binding.favoriteList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.favoriteList.adapter = favoriteAdapter
        favoriteAdapter.notifyDataSetChanged()

        favoriteViewModel.getVacanciesNumberAndInitFirstList()
        favoriteViewModel.getFavoriteStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.favoriteList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == listFavoriteVacancy.size - 1) {
                    favoriteViewModel.getVacanciesPaginated()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(state: FavoriteState) {
        listFavoriteVacancy.clear()
        when (state) {
            is FavoriteState.Content -> {
                Utils.visibilityView(viewArray, binding.favoriteList)
                listFavoriteVacancy.addAll(state.favoriteVacancies)
                favoriteAdapter.updateFavorites(listFavoriteVacancy)
            }

            is FavoriteState.Empty -> {
                Utils.visibilityView(viewArray, binding.placeholderEmptyList)
            }

            is FavoriteState.Error -> {
                Utils.visibilityView(viewArray, binding.placeholderErrorList)
            }
        }
        favoriteAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewArray = null
    }

    private fun initDebounces() {
        vacancyClickDebounce = onVacancyClickDebounce {
            navigate.navigateTo(NavigateEventState.ToVacancyDataSourceDb(it))
        }
    }

    private fun onVacancyClickDebounce(action: (Int) -> Unit): (Int) -> Unit {
        return debounce<Int>(
            DELAY_CLICK_VACANCY,
            lifecycleScope,
            false,
            true,
            action
        )
    }
}
