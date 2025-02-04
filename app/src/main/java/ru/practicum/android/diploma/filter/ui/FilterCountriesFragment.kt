package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterCountriesBinding
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterCountriesViewModel
import ru.practicum.android.diploma.filter.ui.adapters.CountryAdapter

class FilterCountriesFragment : Fragment() {
    private var _binding: FragmentFilterCountriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterCountriesViewModel>()
    private var listAdapter = CountryAdapter { country ->
        viewModel.onCountryClicked(country)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topBar.setOnClickListener {
            findNavController().navigate(R.id.action_filterCountriesFragment_to_filterPlaceOfWorkFragment)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvCountryRegionItems)

        recyclerView.adapter = listAdapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryViewState.Success -> {
                    listAdapter.setCountries(state.countryList)
                }
                is CountryViewState.CountrySelected -> {
                    viewModel.saveCountry(state.country)
                    findNavController().navigate(R.id.action_filterCountriesFragment_to_filterPlaceOfWorkFragment)
                }
                is CountryViewState.NotFoundError -> {
                    Toast.makeText(requireContext(), "Страны не найдены", Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }

        viewModel.getCountries() // Вызов загрузки стран
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
