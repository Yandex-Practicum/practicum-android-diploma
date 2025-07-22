package ru.practicum.android.diploma.ui.searchfilters.workplacefilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.WorkplaceFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.SelectionType
import ru.practicum.android.diploma.presentation.workplacescreen.WorkplaceFiltersViewModel
import ru.practicum.android.diploma.util.renderFilterField

class WorkplaceFiltersFragment : Fragment() {

    private var _binding: WorkplaceFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<WorkplaceFiltersViewModel>()

    private var gray: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(SELECTION_RESULT_KEY) { _, bundle ->
            val type = SelectionType.from(bundle.getString(SELECTION_TYPE_KEY))
            val countryName = bundle.getString(COUNTRY_NAME_KEY)

            when (type) {
                SelectionType.COUNTRY -> {
                    val countryId = bundle.getString(COUNTRY_ID_KEY)
                    viewModel.setTempCountrySelection(countryId, countryName)
                }

                SelectionType.REGION -> {
                    val regionName = bundle.getString(REGION_NAME_KEY)
                    viewModel.setTempRegionSelection(regionName, countryName)
                }

                null -> Unit
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = WorkplaceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gray = ContextCompat.getColor(requireContext(), R.color.gray)

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
            viewModel.saveSelection()
            findNavController().popBackStack()
        }

        binding.inputLayoutCountry.setEndIconOnClickListener {
            viewModel.clearCountry()
        }

        binding.inputLayoutRegion.setEndIconOnClickListener {
            viewModel.clearRegion()
        }

        viewModel.getTempCountry.observe(viewLifecycleOwner) { tempCountry ->
            updateCountryView()
        }

        viewModel.getTempRegion.observe(viewLifecycleOwner) { tempRegion ->
            updateCountryView()
        }

        viewModel.getSelectedParams.observe(viewLifecycleOwner) { savedParams ->
            updateCountryView()
        }

        viewModel.loadParameters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCountryView() {
        val tempCountry = viewModel.getTempCountry.value?.name
        val tempRegion = viewModel.getTempRegion.value
        val savedParams = viewModel.getSelectedParams.value

        val countryName = tempCountry ?: savedParams?.countryName
        val regionName = tempRegion ?: savedParams?.regionName

        renderSelectedRegion(regionName)
        renderSelectedCountry(countryName)
        buttonChooseVisibility(countryName, regionName)
    }

    private fun renderSelectedCountry(name: String?) {
        binding.inputLayoutCountry.renderFilterField(
            requireContext(),
            name,
            R.string.country,
            gray
        )
    }

    private fun renderSelectedRegion(name: String?) {
        binding.inputLayoutRegion.renderFilterField(
            context = requireContext(),
            text = name,
            hintResId = R.string.region,
            grayColor = gray
        )
    }

    private fun buttonChooseVisibility(countryName: String?, regionName: String?) {
        binding.btnChoose.isVisible = !countryName.isNullOrBlank() || !regionName.isNullOrBlank()
    }

    private fun openCountry() {
        val action = WorkplaceFiltersFragmentDirections.actionWorkplaceFiltersFragmentToCountryFiltersFragment()
        findNavController().navigate(action)
        viewModel.clearRegion()
    }

    private fun openRegion() {
        val savedId = viewModel.getSelectedParams.value?.countryId
        val tempId = viewModel.getTempCountry.value?.id
        val countryIdToUse = tempId ?: savedId ?: ""
        val action = WorkplaceFiltersFragmentDirections
            .actionWorkplaceFiltersFragmentToRegionsFilterFragment(countryIdToUse)
        findNavController().navigate(action)
    }

    companion object {
        const val SELECTION_TYPE_KEY = "selection_type"
        const val COUNTRY_NAME_KEY = "country_name"
        const val COUNTRY_ID_KEY = "country_id"
        const val REGION_NAME_KEY = "region_name"
        const val SELECTION_RESULT_KEY = "result_key"
    }
}
