package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterPlaceOfWorkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.common.sharedprefs.models.City
import ru.practicum.android.diploma.common.sharedprefs.models.Country
import ru.practicum.android.diploma.common.sharedprefs.models.Filter

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
            findNavController().navigateUp()
        }

        binding.btnSelectPlaceOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterPlaceOfWorkFragment_to_filterSettingsFragment)
        }

        binding.frameCountry.setOnClickListener {
            if (selectFilter?.areaCountry == null) {
                // viewModel.updateFilter(selectFilter!!)
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterCountriesFragment
                )
            } else {
                viewModel.updateFilter(
                    Filter(
                        null,
                        selectFilter!!.areaCity,
                        selectFilter!!.industrySP,
                        selectFilter!!.salary,
                        selectFilter!!.withSalary
                    )
                )
            }
        }

        binding.frameRegion.setOnClickListener {
            if (selectFilter?.areaCity == null) {
                //  viewModel.updateFilter(selectFilter!!)
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterRegionFragment
                )
            } else {
                viewModel.updateFilter(selectFilter!!)
            }
        }

        /* для отладки
        binding.addCountry.setOnClickListener {
            viewModel.updateFilter(Filter(Country("1", "Россия"), selectFilter?.areaCity, null, null, null))
            viewModel.loadData()
        }
        binding.addRegion.setOnClickListener {
            viewModel.updateFilter(Filter(selectFilter?.areaCountry, City("1", "Москва"), null, null, null))
            viewModel.loadData()
        }
    */
    }

    private fun showCountry(country: Country?) {
        if (country == null) {
            binding.smallAndBigCountry.isVisible = false
            binding.onlyBigCountry.isVisible = true
            Glide.with(this)
                .load(R.drawable.ic_arrow_forward_24px)
                .centerCrop()
                .into(binding.buttonImageCountry)
        } else {
            binding.smallAndBigCountry.isVisible = true
            binding.onlyBigCountry.isVisible = false
            binding.bigTextCountry.text = country.name
            Glide.with(this)
                .load(R.drawable.ic_close_24px)
                .centerCrop()
                .into(binding.buttonImageCountry)
        }
    }

    private fun showCity(city: City?) {
        if (city == null) {
            binding.smallAndBigRegion.isVisible = false
            binding.onlyBigRegion.isVisible = true
            Glide.with(this)
                .load(R.drawable.ic_arrow_forward_24px)
                .centerCrop()
                .into(binding.buttonImageRegion)
        } else {
            binding.smallAndBigRegion.isVisible = true
            binding.onlyBigRegion.isVisible = false
            binding.bigTextRegion.text = city.name
            Glide.with(this)
                .load(R.drawable.ic_close_24px)
                .centerCrop()
                .into(binding.buttonImageRegion)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
