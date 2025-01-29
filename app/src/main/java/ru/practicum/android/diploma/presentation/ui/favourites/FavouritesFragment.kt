package ru.practicum.android.diploma.presentation.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.adapter.VacancyAdapter
import ru.practicum.android.diploma.util.debounce

class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModel()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private var onClickVacancy: (Vacancy) -> Unit = {}
    private var adapter = VacancyAdapter()

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

        onClickVacancy = debounce<Vacancy>(
            CLICK_DEBOUNCE_DAYLY,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { vacancy ->
            val bundle = bundleOf(KEY_VACANCY to vacancy.id)
            findNavController().navigate(R.id.action_favouritesFragment_to_vacancyFragment, bundle)
        }

        adapter.onItemClick = { vacancy -> onClickVacancy(vacancy) }

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
        const val KEY_VACANCY = "KEY_VACANCY"
        const val CLICK_DEBOUNCE_DAYLY = 0L
        fun newInstance() = FavouritesFragment()
    }
}
