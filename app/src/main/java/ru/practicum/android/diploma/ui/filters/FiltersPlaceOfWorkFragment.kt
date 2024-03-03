package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterChoosePlaceOfWorkBinding
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.ui.filter.ChooseIndustryFragment

class FiltersPlaceOfWorkFragment : Fragment() {

    private var _binding: FragmentFilterChoosePlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    private var country: Country? = null
    private var region: Area? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterChoosePlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initFilters()


    }

    private fun initListeners() {
        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.country.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filtersCountryFragment)
        }

        binding.region.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filtersRegionFragment)
        }

        binding.textView2.setOnClickListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(
                COUNTRY_KEY to country,
                REGION_KEY to region))
            findNavController().popBackStack()
        }
    }

    private fun initFilters() {
        parentFragmentManager.setFragmentResultListener(FiltersCountryFragment.REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            country = bundle.getParcelable(FiltersCountryFragment.COUNTRY_KEY)
            if (country != null) {
                binding.country.setText(country!!.name)
            }
        }
    }

    companion object {
        const val REQUEST_KEY = "PLACE_KEY"
        const val COUNTRY_KEY = "COUNTRY"
        const val REGION_KEY = "REGION"
    }

}
