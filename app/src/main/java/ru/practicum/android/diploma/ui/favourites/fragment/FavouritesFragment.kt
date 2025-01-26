package ru.practicum.android.diploma.ui.favourites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favourites.viewmodel.FavouritesViewModel
import ru.practicum.android.diploma.ui.search.fragment.VacancyAdapter

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

        val onSearchTrackClick: (Vacancy) -> Unit =
            { vacancy: Vacancy -> viewModel.onVacancyClicked(vacancy) }
        adapter = VacancyAdapter(onSearchTrackClick)

        binding.emptyListPlaceholder.isVisible = true
        binding.favoritesRecyclerView.isVisible = false

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerView.adapter = adapter

        viewModel.favoriteVacancies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> showErrorPlaceholder()
                is Resource.Success -> resource.value?.let { favoritesRender(it) }
            }
        }
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
