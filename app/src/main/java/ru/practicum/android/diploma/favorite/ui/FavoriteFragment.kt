package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FavoriteFragmentBinding
import ru.practicum.android.diploma.favorite.presintation.FavoriteScreenState
import ru.practicum.android.diploma.favorite.presintation.FavoriteVacancyViewModel
import ru.practicum.android.diploma.favorite.ui.presenter.FavoriteRecycleViewAdapter
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.vacancy.ui.VacancyDetailFragment

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteVacancyViewModel by viewModel()
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val vacancies = mutableListOf<VacancySearch>()
    private var adapter: FavoriteRecycleViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewInit()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getVacancyClickEvent().observe(viewLifecycleOwner) {
            openVacancyDetails(it)
        }

    }

    private fun render(state: FavoriteScreenState) {
        when (state) {
            is FavoriteScreenState.ContentState -> showContent(state.vacancies)
            FavoriteScreenState.EmptyState -> showEmptyScreen()
            FavoriteScreenState.ErrorState -> showErrorScreen()
        }
    }

    private fun showContent(vacancy: List<VacancySearch>) {
        vacancies.clear()
        vacancies.addAll(vacancy)
        adapter?.notifyDataSetChanged()
        binding.recyclerView.isVisible = true
        binding.notFoundPlaceholder.isVisible = false
        binding.canNotGetVacanciesPlaceholder.isVisible = false
    }

    private fun showEmptyScreen() {
        binding.recyclerView.isVisible = false
        binding.notFoundPlaceholder.isVisible = true
        binding.canNotGetVacanciesPlaceholder.isVisible = false
    }

    private fun showErrorScreen() {
        binding.recyclerView.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.canNotGetVacanciesPlaceholder.isVisible = true
    }

    private fun recyclerViewInit() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavoriteRecycleViewAdapter(vacancies) { vacancy ->
            viewModel.onVacancyClick(vacancy)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun openVacancyDetails(vacancyId: String) {
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacancyFragment,
            VacancyDetailFragment.createArgs(vacancyId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
