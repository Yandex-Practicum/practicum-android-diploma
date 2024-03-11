package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.detail.VacancyDetail
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

        adapter!!.itemClickListener = { _, vacancy ->
            findNavController().navigate(
                R.id.action_favoritesFragment_to_vacanciesFragment,
                bundleOf("vacancy_id" to vacancy.id)
            )
        }

        viewModel.fillData()

        viewModel.vacancyState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun showContent(vacancy: List<VacancyDetail>) {
        binding.favoriteVacancyRecycler.visibility = View.VISIBLE
        binding.favoriteEmptyList.visibility = View.GONE
        binding.favoriteNothingFound.visibility = View.GONE
        binding.favoriteVacancyProgressBar.visibility = View.GONE

        adapter?.vacancyList?.clear()
        adapter?.vacancyList?.addAll(vacancy)
        adapter?.notifyDataSetChanged()
    }
}
