package ru.practicum.android.diploma.presentation.filter.selectArea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSelectCountryBinding
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.CountryAdapter
import ru.practicum.android.diploma.presentation.filter.selectArea.state.CountryState

class SelectCountryFragment : Fragment() {


    private var _binding: FragmentSelectCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelectCountryViewModel by viewModel()
    private var countryAdapter: CountryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countryListRecyclerView
        binding.headerTextview.text = getString(R.string.selectionCountries)
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is CountryState.Display -> displayCountries(state.content)
                is CountryState.Error -> displayError(state.errorText)
                else -> {}
            }
        }
        viewModel.getCountries()
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun displayCountries(countries: List<Country>) {
        binding.apply {
            countryListRecyclerView.visibility = View.VISIBLE
            errorCountriesLayout.visibility = View.GONE
        }
        if (countryAdapter == null) {
            countryAdapter = CountryAdapter(countries) { country ->
                viewModel.onClicked(country)
                findNavController().popBackStack()
            }

            binding.countryListRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = countryAdapter
            }
        }
    }

    private fun displayError(errorText: String) {
        binding.apply {
            countryListRecyclerView.visibility = View.INVISIBLE
            errorCountriesLayout.visibility = View.VISIBLE
            countriesErrorText.text = errorText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
