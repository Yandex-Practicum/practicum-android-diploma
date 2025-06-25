package ru.practicum.android.diploma.ui.filter.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentPlaceFilterBinding
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.PlaceState
import ru.practicum.android.diploma.ui.filter.place.models.Region
import ru.practicum.android.diploma.ui.filter.place.region.RegionFilterFragment
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity
import ru.practicum.android.diploma.util.COUNTRY_KEY
import ru.practicum.android.diploma.util.REGION_KEY
import ru.practicum.android.diploma.util.getSerializable

class PlaceFilterFragment : BindingFragment<FragmentPlaceFilterBinding>() {
    private val placeViewModel: PlaceViewModel by viewModel {
        parametersOf(
            getSerializable(requireArguments(), ARG_COUNTRY, Country::class.java),
            getSerializable(requireArguments(), ARG_REGION, Region::class.java),
        )
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaceFilterBinding {
        return FragmentPlaceFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTitles()

        placeViewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is PlaceState.Content -> changeContent(it.country, it.region)
                is PlaceState.Save -> saveAndExit(it.country, it.region)
                is PlaceState.ResponseRegion -> setOfRegion(it.country)
                is PlaceState.Loading -> loading()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Country>(COUNTRY_KEY)
            ?.observe(viewLifecycleOwner) { data ->
                placeViewModel.changeCountry(data)
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Region>(REGION_KEY)
            ?.observe(viewLifecycleOwner) { data ->
                placeViewModel.changeRegion(data)
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(false)
        }
        initTopbar()

        binding.countryItem.listLocationItem.setOnClickListener {
            findNavController().navigate(
                R.id.action_placeFilterFragment_to_countryFilterFragment
            )
        }

        binding.regionItem.listLocationItem.setOnClickListener {
            placeViewModel.responseRegion()
        }

        binding.selectedCountry.selectedItemClose.setOnClickListener {
            placeViewModel.changeCountry(null)
        }

        binding.selectedRegion.selectedItemClose.setOnClickListener {
            placeViewModel.changeRegion(null)
        }

        binding.buttonActionPlace.buttonBlue.setOnClickListener {
            placeViewModel.saveChanged()
        }
    }

    private fun initTopbar() {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.place_of_work)
        }

        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(false)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()
    }

    private fun setOfRegion(country: Country?) {
        placeViewModel.clearLiveData()
        findNavController().navigate(
            R.id.action_placeFilterFragment_to_regionFilterFragment,
            RegionFilterFragment.createArgs(country)
        )
    }

    private fun changeContent(country: Country?, region: Region?) {
        if (country != null) {
            changeCountry(country)
        } else {
            clearCountry()
        }
        if (region != null) {
            changeRegion(region)
        } else {
            clearRegion()
        }
        updateButtonVisibility(country, region)
    }

    private fun updateButtonVisibility(country: Country?, region: Region?) {
        val isAnyFilterSelected = country != null || region != null
        binding.buttonActionPlace.root.isVisible = isAnyFilterSelected
    }

    private fun saveAndExit(country: Country?, region: Region?) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_KEY, country)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(REGION_KEY, region)
        findNavController().popBackStack()
    }

    private fun changeCountry(country: Country) {
        binding.selectedCountry.selectedItem.text = country.name
        binding.selectedCountry.root.isVisible = true
        binding.countryItem.root.isVisible = false
    }

    private fun changeRegion(region: Region) {
        binding.selectedRegion.selectedItem.text = region.name
        binding.selectedRegion.root.isVisible = true
        binding.regionItem.root.isVisible = false
    }

    private fun clearCountry() {
        binding.selectedCountry.selectedItem.text = ""
        binding.selectedCountry.root.isVisible = false
        binding.countryItem.root.isVisible = true
    }

    private fun clearRegion() {
        binding.selectedRegion.selectedItem.text = ""
        binding.selectedRegion.root.isVisible = false
        binding.regionItem.root.isVisible = true
    }

    private fun changeTitles() {
        binding.countryItem.listLocationItem.text = getString(R.string.country_text)
        binding.regionItem.listLocationItem.text = getString(R.string.region_text)
        binding.selectedCountry.nameOfSelected.text = getString(R.string.country_text)
        binding.selectedRegion.nameOfSelected.text = getString(R.string.region_text)
    }

    private fun loading() {
        return
    }

    companion object {
        private const val ARG_COUNTRY = "country"
        private const val ARG_REGION = "region"

        fun createArgs(country: Country?, region: Region?): Bundle = bundleOf(
            ARG_COUNTRY to country,
            ARG_REGION to region
        )
    }
}
