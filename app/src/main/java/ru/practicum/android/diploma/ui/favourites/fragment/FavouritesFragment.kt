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
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favourites.viewmodel.FavouritesViewModel
import ru.practicum.android.diploma.ui.search.fragment.VacancyAdapter
import ru.practicum.android.diploma.ui.vacancydetails.fragment.VacancyFragment

class FavouritesFragment : Fragment() {

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

        binding.emptyListPlaceholder.isVisible = true
        binding.favoritesRecyclerView.isVisible = false

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.adapter = adapter

        val owner = getViewLifecycleOwner()
        viewModel.getVacancyTrigger().observe(owner) { vacancyId ->
            openVacancyDetails(vacancyId)
        }

        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> showErrorPlaceholder()
                is Resource.Success -> resource.value?.let { favoritesRender(it) }
            }
        }
    }

    // перейти на экран деаталей вакансии
    private fun openVacancyDetails(vacancyId: Long) {
        findNavController().navigate(
            R.id.action_searchFragment_to_vacancyFragment2,
            VacancyFragment.createArgs(vacancyId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorPlaceholder() {
        binding.errorListPlaceholder.isVisible = true
        binding.favoritesRecyclerView.isVisible = false
        binding.emptyListPlaceholder.isVisible = false
    }

    private fun favoritesRender(vacancies: List<Vacancy>) {
        binding.favoritesRecyclerView.isVisible = vacancies.isNotEmpty()
        binding.emptyListPlaceholder.isVisible = vacancies.isEmpty()
        binding.errorListPlaceholder.isVisible = false

        if (vacancies.isNotEmpty()) {
            adapter?.submitList(vacancies)
        }
    }
}
