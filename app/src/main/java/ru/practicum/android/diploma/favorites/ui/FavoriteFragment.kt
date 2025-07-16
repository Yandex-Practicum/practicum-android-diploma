package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoriteVacancyViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteVacancyViewModel by viewModel()

    private val adapter = VacancyAdapter { vacancyId ->
        findNavController().navigate(
            R.id.action_favoriteFragment_to_vacancyFragment,
            bundleOf("vacancyId" to vacancyId)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewFavouritesVacancies.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavouritesVacancies.adapter = adapter

        observeFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { list ->
                    if (list.isEmpty()) {
                        binding.recyclerViewFavouritesVacancies.visibility = View.GONE
                        binding.placeholderNothing.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewFavouritesVacancies.visibility = View.VISIBLE
                        binding.placeholderNothing.visibility = View.GONE
                        adapter.setItems(list)
                    }
                }
            }
        }
    }
}
