package ru.practicum.android.diploma.ui.favourites.fragment

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
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.DatabaseResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favourites.viewmodel.FavouritesViewModel
import ru.practicum.android.diploma.ui.search.fragment.VacancyAdapter
import ru.practicum.android.diploma.ui.vacancydetails.fragment.VacancyFragment

class FavouritesFragment : Fragment() {

    enum class PlaceholderState {
        ERROR, EMPTY, LOAD
    }

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavouritesViewModel by viewModel()
    private var adapter: VacancyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onSearchTrackClick: (Long) -> Unit =
            { vacancyId: Long -> viewModel.onVacancyClicked(vacancyId) }
        adapter = VacancyAdapter(onSearchTrackClick)

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.adapter = adapter

        val owner = getViewLifecycleOwner()
        viewModel.getVacancyTrigger().observe(owner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }

        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                DatabaseResult.Empty -> showPlaceholder(PlaceholderState.EMPTY)
                is DatabaseResult.Error -> showPlaceholder(PlaceholderState.ERROR)
                is DatabaseResult.Success -> renderListVacancy(resource.vacancies)
            }
        }
    }

    // перейти на экран деаталей вакансии
    private fun openVacancyDetails(vacancyId: Long) {
        findNavController().navigate(
            R.id.action_favouritesFragment_to_vacancyFragment,
            VacancyFragment.createArgs(vacancyId, true)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPlaceholder(state: PlaceholderState) {
        when (state) {
            PlaceholderState.ERROR -> {
                binding.errorListPlaceholder.isVisible = true
                binding.favoritesRecyclerView.isVisible = false
                binding.emptyListPlaceholder.isVisible = false
            }

            PlaceholderState.EMPTY -> {
                binding.errorListPlaceholder.isVisible = false
                binding.favoritesRecyclerView.isVisible = false
                binding.emptyListPlaceholder.isVisible = true
            }

            PlaceholderState.LOAD -> {
                binding.errorListPlaceholder.isVisible = false
                binding.favoritesRecyclerView.isVisible = true
                binding.emptyListPlaceholder.isVisible = false
            }
        }
    }

    private fun renderListVacancy(vacancy: List<Vacancy>) {
        adapter?.submitList(vacancy)
        showPlaceholder(PlaceholderState.LOAD)
    }
}
