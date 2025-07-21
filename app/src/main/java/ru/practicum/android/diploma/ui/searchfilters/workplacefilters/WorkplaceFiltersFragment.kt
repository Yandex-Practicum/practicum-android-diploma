package ru.practicum.android.diploma.ui.searchfilters.workplacefilters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.WorkplaceFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.SelectionType
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

        //на фрагменте региона также использовать этот ключ в результате
        setFragmentResultListener(SELECTION_RESULT_KEY) { _, bundle ->
            val type = SelectionType.from(bundle.getString(SELECTION_TYPE_KEY))
            val value = bundle.getString(SELECTION_VALUE_KEY)

            when (type) {
                SelectionType.COUNTRY -> viewModel.setTempCountrySelection(value)
                SelectionType.REGION -> TODO()
                null -> Unit
            }
        }

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editTextCountry.setOnClickListener {
            openCountry()
        }

        binding.btnChoose.setOnClickListener {
            viewModel.saveSelection()
            findNavController().popBackStack()
        }

        binding.inputLayoutCountry.setEndIconOnClickListener {
            viewModel.clearCountry()
        }

        viewModel.getTempCountry.observe(viewLifecycleOwner) { tempCountry ->
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
        val tempCountry = viewModel.getTempCountry.value
        val tempRegion = viewModel.getTempRegion.value
        val savedParams = viewModel.getSelectedParams.value

        val countryName = tempCountry ?: savedParams?.countryName
        val regionNAme = tempRegion ?: savedParams?.regionName

        renderSelectedCountry(countryName)
        buttonChooseVisibility(countryName, regionNAme)
    }

    private fun renderSelectedCountry(name: String?) {
        val isEmpty = name.isNullOrBlank()

        binding.editTextCountry.setText(name)
        binding.inputLayoutCountry.hint = if (isEmpty) getString(R.string.country) else ""

        val icon = if (isEmpty) R.drawable.arrow_forward_24px else R.drawable.close_24px
        binding.inputLayoutCountry.setEndIconDrawable(icon)
    }

    private fun buttonChooseVisibility(countryName: String?, regionName: String?) {
        binding.btnChoose.isVisible = !countryName.isNullOrBlank() || !regionName.isNullOrBlank()
    }

    private fun openCountry() {
        val action = WorkplaceFiltersFragmentDirections.actionWorkplaceFiltersFragmentToCountryFiltersFragment()
        findNavController().navigate(action)
    }

    companion object {
        const val SELECTION_TYPE_KEY = "selection_type"
        const val SELECTION_VALUE_KEY = "selection_value"
        const val SELECTION_RESULT_KEY = "country_result_key"
    }
}
