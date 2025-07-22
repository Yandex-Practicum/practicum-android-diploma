package ru.practicum.android.diploma.ui.searchfilters.countryfilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.CountryFiltersFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.domain.models.filters.SelectionType
import ru.practicum.android.diploma.presentation.countryfiltersscreen.CountryFiltersViewModel
import ru.practicum.android.diploma.presentation.countryfiltersscreen.uistate.CountryFiltersUiState
import ru.practicum.android.diploma.ui.searchfilters.recycleview.WorkplaceAdapter
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.COUNTRY_ID_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.COUNTRY_NAME_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.SELECTION_RESULT_KEY
import ru.practicum.android.diploma.ui.searchfilters.workplacefilters.WorkplaceFiltersFragment.Companion.SELECTION_TYPE_KEY

class CountryFiltersFragment : Fragment(), WorkplaceAdapter.OnClickListener {

    private var _binding: CountryFiltersFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CountryFiltersViewModel>()
    private val adapter = WorkplaceAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CountryFiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvItemList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemList.adapter = adapter

        binding.btnArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getCountryState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: CountryFiltersUiState) {
        when (state) {
            is CountryFiltersUiState.Content -> {
                binding.rvItemList.isVisible = true
                adapter.submitList(state.countries)
                binding.progressBar.isVisible = false
                binding.groupPlaceholder.isVisible = false
            }
            is CountryFiltersUiState.Error -> {
                binding.groupPlaceholder.isVisible = true
                binding.progressBar.isVisible = false
                binding.rvItemList.isVisible = false
            }

            CountryFiltersUiState.Loading -> {
                binding.progressBar.isVisible = true
                binding.rvItemList.isVisible = false
                binding.groupPlaceholder.isVisible = false
            }
        }
    }

    override fun onClick(country: Country) {
        val result = Bundle().apply {
            putString(SELECTION_TYPE_KEY, SelectionType.COUNTRY.value)
            putString(COUNTRY_NAME_KEY, country.name)
            putString(COUNTRY_ID_KEY, country.id)
        }
        setFragmentResult(SELECTION_RESULT_KEY, result)
        findNavController().popBackStack()
    }
}
