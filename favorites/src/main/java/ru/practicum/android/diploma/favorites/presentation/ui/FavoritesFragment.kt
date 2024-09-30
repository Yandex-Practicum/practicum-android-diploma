package ru.practicum.android.diploma.favorites.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.favorites.R
import ru.practicum.android.diploma.favorites.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.presentation.ui.adapters.FavoriteAdapter
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.favorites.presentation.viewmodel.state.FavoriteState

class FavoritesFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private var viewArray: Array<View>? = null

    private var listFavoriteVacancy: MutableList<FavoriteVacancy> = mutableListOf()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(listFavoriteVacancy) {
            favoriteVacancySelection(it)
        }
    }

    private fun favoriteVacancySelection(vacancy: FavoriteVacancy) {
        findNavController().navigate(
            R.id.action_favoritesFragment_to_vacancy_navigation
        )
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

        binding.favoriteList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.favoriteList.adapter = favoriteAdapter
        favoriteAdapter.notifyDataSetChanged()

        favoriteViewModel.getVacancies()
        favoriteViewModel.getFavoriteStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(state: FavoriteState) {
        listFavoriteVacancy.clear()
        when (state) {
            is FavoriteState.Content -> {
                Utils.visibilityView(viewArray, binding.favoriteList)
                listFavoriteVacancy.addAll(state.favoriteVacancies)
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
        viewArray = emptyArray()
        viewArray = null
    }
}
