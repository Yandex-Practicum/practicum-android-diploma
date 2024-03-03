package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentFilterChooseCountryBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.filters.FiltersCountriesState
import ru.practicum.android.diploma.presentation.filters.FiltersCountryViewModel
import ru.practicum.android.diploma.ui.filters.recycler.FilterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R


class FiltersCountryFragment : Fragment() {

    private var _binding: FragmentFilterChooseCountryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltersCountryViewModel by viewModel()
    private var countriesAdapter: FilterAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterChooseCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fillData()

        viewModel.getFiltersCountriesStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        countriesAdapter = FilterAdapter(object : FilterAdapter.CountryClickListener {
            override fun onCountryClick(country: Country) {
                val bundle = Bundle().apply {
                    putParcelable("country", country)
                }
                findNavController().navigateUp().apply {
                    arguments = bundle
                }
            }
        })

        binding.countryList.adapter = countriesAdapter
        binding.countryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun render(state: FiltersCountriesState) {
        when (state) {
            is FiltersCountriesState.Content -> showContent(state.countries)
            is FiltersCountriesState.Empty -> showEmpty()
            is FiltersCountriesState.Error -> showError()
            is FiltersCountriesState.Loading -> showLoading()
        }
    }

    private fun showLoading() {

    }

    private fun showError() {

    }

    private fun showEmpty() {

    }

    private fun showContent(countries: List<Country>) {

        countriesAdapter!!.countriesList.clear()
        countriesAdapter!!.countriesList.addAll(countries)
        countriesAdapter!!.notifyDataSetChanged()

    }

    companion object {
        const val REQUEST_KEY = "KEY"
        const val COUNTRY = "COUNTRY"
    }
}
