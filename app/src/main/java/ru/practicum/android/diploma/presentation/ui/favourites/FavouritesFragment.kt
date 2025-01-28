package ru.practicum.android.diploma.presentation.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.adapter.VacancyAdapter

class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModel()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val adapter = VacancyAdapter().apply {
        onItemClick = { vacancy ->
            // Реализация логики при нажатии, открытие подробной информации о вакансии
        }
    }

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

        setupRecyclerView()

        viewModel.loadVacancy()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: FavouriteState) {
        when (state) {
            is FavouriteState.Empty -> showEmpty(state.message)
            is FavouriteState.Content -> showContent(state.vacancy)
        }
    }

    private fun setupRecyclerView() {
        binding.listVacancy.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavouritesFragment.adapter
        }
    }

    private fun showEmpty(message: Int) {
        binding.listVacancy.visibility = View.GONE
        binding.placeholderButton.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.VISIBLE
        binding.placeholderText.setText(message)
    }

    private fun showContent(vacancies: List<Vacancy>) {
        binding.listVacancy.isVisible = true
        binding.placeholderButton.isVisible = false
        binding.placeholderText.isVisible = false
        adapter.updateItems(vacancies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}
