package ru.practicum.android.diploma.ui.searchfilters.workplacefilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.WorkplaceFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.FilterParameters
import ru.practicum.android.diploma.presentation.workplacescreen.WorkplaceFiltersViewModel

class WorkplaceFiltersFragment : Fragment() {

    private var _binding: WorkplaceFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkplaceFiltersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = WorkplaceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editTextCountry.setOnClickListener {
            openCountry()
        }

        binding.editTextRegion.setOnClickListener {
            openRegion()
        }

        binding.btnChoose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.inputLayoutCountry.setEndIconOnClickListener {
            viewModel.clearCountry()
        }

        binding.inputLayoutRegion.setEndIconOnClickListener {
            viewModel.clearRegion()
        }

        viewModel.getSelectedCountry.observe(viewLifecycleOwner) {
            renderSelectedCountry(it)
        }

        viewModel.loadParameters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderSelectedCountry(state: FilterParameters) {
        val isEmptyCountry = state.countryName.isNullOrBlank()
        val isEmptyRegion = state.regionName.isNullOrBlank()

        binding.editTextCountry.setText(state.countryName)
        binding.editTextRegion.setText(state.regionName)

        binding.inputLayoutCountry.hint = if (isEmptyCountry) getString(R.string.country) else ""
        binding.inputLayoutRegion.hint = if (isEmptyRegion) getString(R.string.region) else ""

        val icon = if (isEmptyCountry) R.drawable.arrow_forward_24px else R.drawable.close_24px
        binding.inputLayoutCountry.setEndIconDrawable(icon)

        val iconRegions = if (isEmptyRegion) R.drawable.arrow_forward_24px else R.drawable.close_24px
        binding.inputLayoutRegion.setEndIconDrawable(iconRegions)
    }

    private fun openCountry() {
        val action = WorkplaceFiltersFragmentDirections.actionWorkplaceFiltersFragmentToCountryFiltersFragment()
        findNavController().navigate(action)
    }

    private fun openRegion() {
        val action = WorkplaceFiltersFragmentDirections.actionWorkplaceFiltersFragmentToRegionsFilterFragment()
        findNavController().navigate(action)
    }
}
