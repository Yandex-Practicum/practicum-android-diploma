package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterPlaceOfWorkViewModel

class FilterPlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterPlaceOfWorkViewModel>()
    private var selectFilter: Filter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFilterLiveData().observe(viewLifecycleOwner) { filter ->
            selectFilter = filter
            showCountry(filter.areaCountry)
            showCity(filter.areaCity)
            binding.btnSelectPlaceOfWork.isVisible = filter.areaCountry != null || filter.areaCity != null
        }

        binding.topBar.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filterSettingsFragment)
        }

        binding.btnSelectPlaceOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filterSettingsFragment)
        }

        binding.frameCountry.setOnClickListener {
            if (selectFilter?.areaCountry == null) {
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterCountriesFragment
                )
            }
        }

        binding.frameRegion.setOnClickListener {
            if (selectFilter?.areaCity == null) {
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterRegionFragment
                )
            }
        }
    }

    private fun showCountry(country: Country?) {
        if (country == null) {
            binding.smallAndBigCountry.isVisible = false
            binding.onlyBigCountry.isVisible = true
            binding.buttonImageCountry.setImageResource(R.drawable.ic_arrow_forward_24px)
        } else {
            binding.smallAndBigCountry.isVisible = true
            binding.onlyBigCountry.isVisible = false
            binding.bigTextCountry.text = country.name
            binding.buttonImageCountry.setImageResource(R.drawable.ic_close_24px)
            binding.buttonImageCountry.setOnClickListener {
                viewModel.clearFilterField("areaCountry")
                showCountry(null)
            }
        }
    }

    private fun showCity(city: City?) {
        if (city == null) {
            binding.smallAndBigRegion.isVisible = false
            binding.onlyBigRegion.isVisible = true
            binding.buttonImageRegion.setImageResource(R.drawable.ic_arrow_forward_24px)
        } else {
            binding.smallAndBigRegion.isVisible = true
            binding.onlyBigRegion.isVisible = false
            binding.bigTextRegion.text = city.name
            binding.buttonImageRegion.setImageResource(R.drawable.ic_close_24px)
            binding.buttonImageRegion.setOnClickListener {
                viewModel.clearFilterField("areaCity")
                showCity(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
