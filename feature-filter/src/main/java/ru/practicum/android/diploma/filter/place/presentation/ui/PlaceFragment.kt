package ru.practicum.android.diploma.filter.place.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentPlaceBinding
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.RegionsCountriesViewModel
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState

internal class PlaceFragment : Fragment() {

    private val regionsCountriesViewModel: RegionsCountriesViewModel by viewModel()

    private var _binding: FragmentPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlaceState()

        regionsCountriesViewModel.observePlaceState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.clickCountry.setOnClickListener(listener)
        binding.clickRegion.setOnClickListener(listener)
        binding.clickCountryClear.setOnClickListener(listener)
        binding.clickRegionClear.setOnClickListener(listener)
        binding.buttonBack.setOnClickListener(listener)
    }

    private fun initPlaceState() {
        arguments?.let { args ->
            val idCountry = args.getString(ARGS_COUNTRY_ID)
            val nameCountry = args.getString(ARGS_COUNTRY_NAME)
            val idRegion = args.getString(ARGS_REGION_ID)
            val nameRegion = args.getString(ARGS_REGION_NAME)

            when {
                idCountry != null && nameCountry != null -> {
                    if (idRegion == null && nameRegion == null) {
                        regionsCountriesViewModel.setPlaceState(
                            PlaceState.ContentCountry(
                                country = Country(
                                    id = idCountry,
                                    name = nameCountry
                                )
                            )
                        )
                    } else {
                        regionsCountriesViewModel.setPlaceState(
                            PlaceState.ContentPlace(
                                place = Place(
                                    idCountry = idCountry,
                                    nameCountry = nameCountry,
                                    idRegion = idRegion ?: "",
                                    nameRegion = nameRegion ?: ""
                                )
                            )
                        )
                    }
                }
                else -> {
                    regionsCountriesViewModel.setPlaceState(PlaceState.Empty)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun render(state: PlaceState?) {
        when (state) {
            is PlaceState.ContentCountry -> {
                binding.inputCountry.setText(state.country.name)
                binding.inputRegion.setText("")
                binding.clickCountryClear.visibility = View.VISIBLE
                binding.clickCountry.visibility = View.GONE
                binding.clickRegionClear.visibility = View.GONE
                binding.clickRegion.visibility = View.VISIBLE
                binding.selectButton.visibility = View.VISIBLE
            }

            is PlaceState.ContentPlace -> {
                binding.inputCountry.setText(state.place.nameCountry)
                binding.inputRegion.setText(state.place.nameRegion)
                binding.clickCountryClear.visibility = View.VISIBLE
                binding.clickCountry.visibility = View.GONE
                binding.clickRegionClear.visibility = View.VISIBLE
                binding.clickRegion.visibility = View.GONE
                binding.selectButton.visibility = View.VISIBLE
            }

            is PlaceState.Empty, null -> {
                binding.inputCountry.text?.clear()
                binding.inputRegion.text?.clear()
                binding.clickCountryClear.visibility = View.GONE
                binding.clickCountry.visibility = View.VISIBLE
                binding.clickRegionClear.visibility = View.GONE
                binding.clickRegion.visibility = View.VISIBLE
                binding.selectButton.visibility = View.GONE
            }

            is PlaceState.Error -> {
                binding.inputCountry.text?.clear()
                binding.inputRegion.text?.clear()
                binding.clickCountryClear.visibility = View.GONE
                binding.clickCountry.visibility = View.VISIBLE
                binding.clickRegionClear.visibility = View.GONE
                binding.clickRegion.visibility = View.VISIBLE
                binding.selectButton.visibility = View.GONE
            }
        }
    }

    private val listener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.clickCountry -> {
                    findNavController().navigate(R.id.action_placeFragment_to_countryFragment)
                }

                R.id.clickRegion -> {
                    findNavController().navigate(R.id.action_placeFragment_to_regionFragment)
                }

                R.id.clickCountryClear -> {
                    regionsCountriesViewModel.setPlaceState(PlaceState.Empty)
                    arguments?.clear()
                }

                R.id.clickRegionClear -> {
                    arguments?.putString(ARGS_REGION_ID, null)
                    arguments?.putString(ARGS_REGION_NAME, null)

                    initPlaceState()
                }

                R.id.buttonBack -> {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARGS_COUNTRY_ID = "country_id"
        private const val ARGS_COUNTRY_NAME = "country_name"
        private const val ARGS_REGION_ID = "region_id"
        private const val ARGS_REGION_NAME = "region_name"

        fun createArgsCounty(countryId: String, countryName: String): Bundle =
            bundleOf(ARGS_COUNTRY_ID to countryId, ARGS_COUNTRY_NAME to countryName)

        fun createArgsRegion(countryId: String, countryName: String, regionId: String, regionName: String): Bundle =
            bundleOf(
                ARGS_COUNTRY_ID to countryId,
                ARGS_COUNTRY_NAME to countryName,
                ARGS_REGION_ID to regionId,
                ARGS_REGION_NAME to regionName
            )
    }
}
