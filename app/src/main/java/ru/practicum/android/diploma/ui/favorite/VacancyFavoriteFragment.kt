package ru.practicum.android.diploma.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.ui.search.VacancyAdapter

class VacancyFavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FavoriteViewModel>()

    private val toolbar by lazy { (requireActivity() as RootActivity).toolbar }

    private var favoriteAdapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarSetup()

        viewModel.stateFavorite.observe(viewLifecycleOwner) {
            render(it)
        }
        favoriteAdapter = VacancyAdapter(arrayListOf(), object : VacancyAdapter.OnClickListener {
            override fun onClick(vacancy: Vacancy) {
                openFragmentVacancy(vacancyId = vacancy.id)
            }
        })

        binding.rvFavorite.adapter = favoriteAdapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        viewModel.stateFavorite.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.fillData()
    }

    private fun toolbarSetup() {
        toolbar.title = getString(R.string.title_favorite)
        toolbar.menu.findItem(R.id.share).isVisible = false
        toolbar.menu.findItem(R.id.favorite).isVisible = false
        toolbar.menu.findItem(R.id.filters).isVisible = false
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Empty -> renderFavoriteEmpty()
            is FavoriteState.Loading -> renderFavoriteLoading()
            is FavoriteState.Error -> renderFavoriteError()
            is FavoriteState.Content -> renderFavoriteContent(state)
        }
    }

    private fun renderFavoriteEmpty() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.empty_favorite)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.favorite_empty_list)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderFavoriteLoading() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = true
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
        }
    }

    private fun renderFavoriteError() {
        with(binding) {
            rvFavorite.isVisible = false
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.setImageResource(R.drawable.server_error_towel)
            ivPlaceholder.isVisible = true
            tvPlaceholder.setText(R.string.search_server_error)
            tvPlaceholder.isVisible = true
        }
    }

    private fun renderFavoriteContent(state: FavoriteState) {
        val vacancyList = (state as FavoriteState.Content).favorite
        val dictionary = (state as FavoriteState.Content).currencyDictionary
        favoriteAdapter?.vacancyList?.clear()
        favoriteAdapter?.vacancyList?.addAll(vacancyList)
        favoriteAdapter?.currencyDictionary?.putAll(dictionary)
        favoriteAdapter?.notifyDataSetChanged()
        with(binding) {
            progressBar.isVisible = false
            progressBarBottom.isVisible = false
            ivPlaceholder.isVisible = false
            tvPlaceholder.isVisible = false
            rvFavorite.isVisible = true
        }
    }

    private fun openFragmentVacancy(vacancyId: String) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacanciesFragment,
            Bundle().apply { putString("vacancy_model", vacancyId) }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
