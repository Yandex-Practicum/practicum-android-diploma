package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            findNavController().navigateUp()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvCountryRegionItems)

        recyclerView.adapter = listAdapter

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            Log.d("FilterCountriesFragment", "State received: $state")
            when (state) {
                is CountryViewState.Success -> {
                    hideError()
                    listAdapter.setCountries(state.countryList)
                }
                is CountryViewState.CountrySelected -> {
                    viewModel.saveCountry(state.country)
                    findNavController().navigateUp()
                }
                is CountryViewState.ConnectionError -> {
                    showServerError()
                }
                is CountryViewState.NotFoundError -> {
                    showNotFoundError()
                }
                else -> Unit
            }
        }

        viewModel.getCountries() // Вызов загрузки стран
    }
    private fun showServerError() {
        binding.rvCountryRegionItems.visibility = View.GONE
        binding.serverError.visibility = View.VISIBLE
    }
    private fun showNotFoundError() {
        binding.rvCountryRegionItems.visibility = View.GONE
        binding.emptyError.visibility = View.VISIBLE
    }
    private fun hideError() {
        binding.rvCountryRegionItems.visibility = View.VISIBLE
        binding.serverError.visibility = View.GONE
        binding.emptyError.visibility = View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
