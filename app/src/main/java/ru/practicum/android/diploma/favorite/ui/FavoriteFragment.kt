package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FavoriteFragmentBinding
import ru.practicum.android.diploma.favorite.presintation.FavoriteScreenState
import ru.practicum.android.diploma.favorite.presintation.FavoriteVacancyViewModel
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.search.ui.presenter.RecycleViewAdapter
import ru.practicum.android.diploma.util.debounce

class FavoriteFragment : Fragment() {

    companion object {
        const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val viewModel: FavoriteVacancyViewModel by viewModel()
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val vacancies = mutableListOf<VacancySearch>()
    private var adapter: RecycleViewAdapter? = null

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
        adapter!!.notifyDataSetChanged()
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
        val onVacancyClickDebounce: ((VacancySearch) -> Unit) =
            debounce(CLICK_DEBOUNCE_DELAY, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
                TODO("Реализовать клик в вьюмодел")
            }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecycleViewAdapter(vacancies, onVacancyClickDebounce)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
