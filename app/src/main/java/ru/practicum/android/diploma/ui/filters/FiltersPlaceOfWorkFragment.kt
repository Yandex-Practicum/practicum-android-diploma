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
import ru.practicum.android.diploma.domain.models.Country

class FiltersPlaceOfWorkFragment : Fragment() {

    private var _binding: FragmentFilterChoosePlaceOfWorkBinding? = null
    private val binding get() = _binding!!
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
    }

    private fun initFilters() {
        val country: Country? = arguments?.getParcelable("country")

        if (country != null) {
            if (country.name.isNotEmpty()) {
                binding.country.setText(country.name)
            }
        }
    }

    companion object {
        private const val ARGS_COUNTRY_ID = "countryId"
        private const val ARGS_COUNTRY = "country"
        private const val ARGS_AREA_ID = "areaId"
        private const val ARGS_AREA = "area"

        fun createArgs(countryId: String?, country: String?, areaId: String?,
                       area: String?): Bundle =
            bundleOf(
                ARGS_COUNTRY_ID to countryId,
                ARGS_COUNTRY to country,
                ARGS_AREA_ID to areaId,
                ARGS_AREA to area
            )
    }
}
