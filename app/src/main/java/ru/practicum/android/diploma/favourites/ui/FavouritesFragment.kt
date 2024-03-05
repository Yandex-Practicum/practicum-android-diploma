package ru.practicum.android.diploma.favourites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding
import ru.practicum.android.diploma.favourites.domain.models.FavouritesState
import ru.practicum.android.diploma.favourites.presentation.FavouritesViewModel
import ru.practicum.android.diploma.favourites.presentation.VacancyAdapter

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val adapter: VacancyAdapter? by lazy {
        VacancyAdapter {
            onVacancyClick(it)
        }

    }

    private val favouritesViewModel by viewModel<FavouritesViewModel>()

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

        favouritesViewModel.getFavouritesList()

        favouritesViewModel.favouritesStatus.observe(viewLifecycleOwner) {
            renderState(it)
        }

        binding.vacancyRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.vacancyRecycler.adapter = adapter!!

    }

    private fun renderState(state: FavouritesState) {
        when (state) {
            FavouritesState.SUCCESS -> {
                adapter!!.updateVacancyList(favouritesViewModel.favouritesListMutable.value!!)
                adapter!!.notifyDataSetChanged()
            }

            FavouritesState.ERROR -> {
                binding.errorPlaceholder.setImageResource(R.drawable.placeholder_nothing_found)
                binding.placeholderText.setText(R.string.placeholder_cannot_get_list_of_vacancy)
            }

            FavouritesState.EMPTY_RESULT -> {
                binding.errorPlaceholder.setImageResource(R.drawable.placeholder_empty_favourites)
                binding.placeholderText.setText(R.string.placeholder_list_is_empty)
            }
        }
        if (favouritesViewModel.favouritesListMutable.value.isNullOrEmpty()) {
            binding.errorPlaceholder.visibility = View.VISIBLE
            binding.placeholderText.visibility = View.VISIBLE
            binding.vacancyRecycler.visibility = View.GONE
        } else {
            binding.errorPlaceholder.visibility = View.GONE
            binding.placeholderText.visibility = View.GONE
            binding.vacancyRecycler.visibility = View.VISIBLE
        }
    }

    private fun onVacancyClick(vacancyId: Long) {
        if (favouritesViewModel.clickDebounce()) {
            val action = FavouritesFragmentDirections.actionFavouritesFragmentToVacancyFragment(vacancyId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
