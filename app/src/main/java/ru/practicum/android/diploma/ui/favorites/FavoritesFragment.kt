package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.presentation.favorite.FavoriteAdapter
import ru.practicum.android.diploma.presentation.favorite.FavoriteVacancyState
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FavoriteViewModel>()
    private var adapter: FavoriteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAdapter(requireContext())
        binding.favoriteVacancyRecycler.adapter = adapter
        binding.favoriteVacancyRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fillData()

        viewModel.vacancyState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FavoriteVacancyState) {
        when (state) {
            is FavoriteVacancyState.Error -> showNothing()
            is FavoriteVacancyState.EmptyList -> showEmpty()
            is FavoriteVacancyState.Loading -> showLoading()
            is FavoriteVacancyState.Content -> showContent(state.vacancy)
        }
    }

    private fun showEmpty() {
        binding.favoriteVacancyRecycler.visibility = View.GONE
        binding.favoriteEmptyList.visibility = View.VISIBLE
        binding.favoriteNothingFound.visibility = View.GONE
        binding.favoriteVacancyProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.favoriteVacancyRecycler.visibility = View.GONE
        binding.favoriteEmptyList.visibility = View.GONE
        binding.favoriteNothingFound.visibility = View.GONE
        binding.favoriteVacancyProgressBar.visibility = View.VISIBLE
    }

    private fun showNothing() {
        binding.favoriteVacancyRecycler.visibility = View.GONE
        binding.favoriteEmptyList.visibility = View.GONE
        binding.favoriteNothingFound.visibility = View.VISIBLE
        binding.favoriteVacancyProgressBar.visibility = View.GONE
    }

    private fun showContent(vacancy: List<VacancyDetailDto>) {
        binding.favoriteVacancyRecycler.visibility = View.VISIBLE
        binding.favoriteEmptyList.visibility = View.GONE
        binding.favoriteNothingFound.visibility = View.GONE
        binding.favoriteVacancyProgressBar.visibility = View.GONE

        adapter?.vacancy?.clear()
        adapter?.vacancy?.addAll(vacancy)
        adapter?.notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
