package ru.practicum.android.diploma.ui.filter.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkplaceBinding
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared

class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkplaceViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWorkplaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            bindCountry(country)

            viewModel.regionState.observe(viewLifecycleOwner) { region ->
                bindRegion(region, country)
            }
        }

        bindOnClickListeners()
        bindNavigation()
    }

    private fun bindCountry(country: CountryShared?) = with(binding) {
        if (country != null) {
            debugLog(TAG) { "countryState: country = $country" }
            workplaceTextCount.text = country.countryName
            workplaceIcCount.setImageResource(R.drawable.ic_close_24px)
            workplaceIcCount.isClickable = true
            workplaceVisibleCount.visibility = View.VISIBLE
            workplaceButtonApply.visibility = View.VISIBLE
        } else {
            workplaceTextCount.hint = "Страна"
            workplaceIcCount.setImageResource(R.drawable.arrow_forward_24px)
            workplaceIcCount.isClickable = false
            workplaceVisibleCount.visibility = View.GONE
            workplaceButtonApply.visibility = View.GONE
        }
    }

    private fun bindRegion(region: RegionShared?, country: CountryShared?) = with(binding) {
        if (region != null && region.regionParentId == country?.countryId) {
            debugLog(TAG) { "regionState: region = $region" }
            workplaceTextRegion.text = region.regionName
            workplaceIcRegion.setImageResource(R.drawable.ic_close_24px)
            workplaceIcRegion.isClickable = true
            workplaceVisibleRegion.visibility = View.VISIBLE
        } else {
            workplaceTextRegion.text = "Регион"
            workplaceTextRegion.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            workplaceIcRegion.setImageResource(R.drawable.arrow_forward_24px)
            workplaceIcRegion.isClickable = false
            workplaceVisibleRegion.visibility = View.GONE
        }
    }

    private fun bindOnClickListeners() = with(binding) {
        workplaceIcCount.setOnClickListener {
            workplaceTextCount.text = "Страна"
            workplaceTextCount.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            workplaceIcCount.setImageResource(R.drawable.arrow_forward_24px)
            workplaceIcCount.isClickable = false
            workplaceVisibleCount.visibility = View.GONE
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
        }

        workplaceIcRegion.setOnClickListener {
            workplaceTextRegion.text = "Регион"
            workplaceTextRegion.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
            workplaceIcRegion.setImageResource(R.drawable.arrow_forward_24px)
            workplaceIcRegion.isClickable = false
            workplaceVisibleRegion.visibility = View.GONE
            viewModel.setRegionInfo(null)
        }
    }

    private fun bindNavigation() = with(binding) {
        workplaceCount.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_countFragment)
        }

        workplaceRegion.setOnClickListener {
            findNavController().navigate(R.id.action_workplaceFragment_to_regionFragment)
        }

        workplaceButtonApply.setOnClickListener {
            findNavController().navigateUp()
        }

        workplaceToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        workplaceToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WorkplaceFragment"
    }
}
