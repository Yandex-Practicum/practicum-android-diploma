package ru.practicum.android.diploma.presentation.favorites

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
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.domain.models.main.VacancyShort

class FavoritesFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!
    private lateinit var adapter: FavoritesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun setupUI() {
        adapter = FavoritesAdapter(
            onClick = { vacancy ->
                onVacancyClick(vacancy)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun onVacancyClick(vacancy: VacancyShort) {
        openVacancy(vacancy)
    }

    private fun openVacancy(vacancy: VacancyShort) {
        val args = Bundle().apply { putString("vacancyId", vacancy.vacancyId) }
        findNavController().navigate(R.id.action_navigation_main_to_navigation_vacancy, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: FavoriteVacanciesState) {
        when (state) {
            is FavoriteVacanciesState.Content -> showVacancies(state.vacancies)
            is FavoriteVacanciesState.Empty -> showEmpty()
            is FavoriteVacanciesState.Loading -> showLoading()
            is FavoriteVacanciesState.Error -> showError()
        }
    }

    private fun showVacancies(vacancies: List<VacancyShort>) {
        binding.loadingView.isVisible = false
        binding.emptyView.isVisible = false
        binding.recyclerView.isVisible = true
        binding.errorView.isVisible = false
        adapter.submitList(vacancies)
    }

    private fun showEmpty() {
        binding.loadingView.isVisible = false
        binding.emptyView.isVisible = true
        binding.recyclerView.isVisible = false
        binding.errorView.isVisible = false
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
        binding.emptyView.isVisible = false
        binding.recyclerView.isVisible = false
        binding.errorView.isVisible = false
    }
    private fun showError(){
        binding.loadingView.isVisible = false
        binding.emptyView.isVisible = false
        binding.recyclerView.isVisible = false
        binding.errorView.isVisible = true
    }

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }
}
