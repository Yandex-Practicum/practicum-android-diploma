package ru.practicum.android.diploma.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouriteBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.detail.DetailFragment
import ru.practicum.android.diploma.presentation.search.SearchAdapter
import ru.practicum.android.diploma.presentation.search.SearchFragment
import ru.practicum.android.diploma.util.debounce

class FavouriteFragment : Fragment() {
    val viewModel by viewModel<FavouriteViewModel>()
    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!


    lateinit var onItemClickDebounce: (Vacancy) -> Unit
    private val vacancies = mutableListOf<Vacancy>()
    private val adapter = SearchAdapter(vacancies) { vacancy ->
        onItemClickDebounce(vacancy)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        binding.recyclerViewFavorite.adapter = adapter
        onItemClickDebounce = debounce(
            SearchFragment.CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            DetailFragment.addArgs(vacancy)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }
        viewModel.fill()
    }

    private fun render(favouriteVacancies: List<Vacancy>) {
        vacancies.clear()
        vacancies.addAll(favouriteVacancies)
        adapter.notifyDataSetChanged()
    }


}