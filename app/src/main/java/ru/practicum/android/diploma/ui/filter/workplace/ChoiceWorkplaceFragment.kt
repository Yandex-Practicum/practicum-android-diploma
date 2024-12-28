package ru.practicum.android.diploma.ui.filter.workplace

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoiceWorkplaceBinding
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.filter.workplace.country.ChoiceCountryFragment.Companion.COUNTRY_BACKSTACK_KEY
import ru.practicum.android.diploma.ui.filter.workplace.region.ChoiceRegionFragment
import ru.practicum.android.diploma.ui.filter.workplace.region.ChoiceRegionFragment.Companion.REGION_BACKSTACK_KEY

class ChoiceWorkplaceFragment : Fragment() {

    private var _binding: FragmentChoiceWorkplaceBinding? = null
    private val binding get() = _binding!!
    private var regionModel: Region? = null
    private var countryModel: Country? = null
    private var countryContainer: TextInputLayout? = null
    private var countryTextInput: TextInputEditText? = null
    private var regionTextInput: TextInputEditText? = null
    private var regionContainer: TextInputLayout? = null
    private var submitButton: TextView? = null
    private var backButton: ImageView? = null
    private val viewModel by viewModel<ChoiceWorkplaceViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceWorkplaceBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()
        setOnClickListeners()
        setTextChangedListeners()
        setBackStackListeners()

        binding.etCountry.setText(countryModel?.name)
        binding.etRegion.setText(regionModel?.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setViews() {
        countryContainer = binding.tilCountry
        countryTextInput = binding.etCountry
        regionTextInput = binding.etRegion
        regionContainer = binding.tilRegion
        submitButton = binding.btEnter
        backButton = binding.ivBack
    }

    private fun setOnClickListeners() {
        countryContainer?.setEndIconOnClickListener {
            countryClickListener()
        }

        countryTextInput?.setOnClickListener {
            countryClickListener()
        }

        regionContainer?.setEndIconOnClickListener {
            regionClickListener()
        }

        regionTextInput?.setOnClickListener {
            regionClickListener()
        }

        submitButton?.setOnClickListener {
            val filterSettings: Filter = if (regionModel?.name.isNullOrEmpty()) {
                viewModel.clearRegion(Filter(region = regionModel))
                Filter(country = countryModel, region = regionModel)
            } else {
                Filter(country = countryModel, region = regionModel)
            }
            viewModel.setFilter(filterSettings)
            findNavController().popBackStack()
        }

        backButton?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun regionClickListener() {
        val countryId = countryModel?.id ?: ""
        findNavController().navigate(
            R.id.action_choiceWorkplaceFragment_to_choiceRegionFragment,
            ChoiceRegionFragment.createArgs(countryId)
        )
    }

    private fun countryClickListener() {
        findNavController().navigate(
            R.id.action_choiceWorkplaceFragment_to_choiceCountryFragment
        )
    }

    private fun setTextChangedListeners() {
        val countryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                with(countryContainer!!) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.ic_arrow_right)
                        setEndIconOnClickListener {
                            findNavController().navigate(
                                R.id.action_choiceWorkplaceFragment_to_choiceCountryFragment
                            )
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.search_clear_icon)

                        setEndIconOnClickListener {
                            s.clear()
                            regionTextInput?.text?.clear()
                            countryModel = null
                            regionModel = null

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                COUNTRY_BACKSTACK_KEY,
                                null
                            )

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                REGION_BACKSTACK_KEY,
                                null
                            )
                            submitButton?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        val regionTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                with(regionContainer!!) {
                    if (s.isNullOrBlank()) {
                        setEndIconDrawable(R.drawable.ic_arrow_right)
                        setEndIconOnClickListener {
                            val countryId = countryModel?.id ?: ""
                            findNavController().navigate(
                                R.id.action_choiceWorkplaceFragment_to_choiceRegionFragment,
                                ChoiceRegionFragment.createArgs(countryId)
                            )
                        }
                    } else {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.search_clear_icon)

                        setEndIconOnClickListener {
                            s.clear()
                            regionModel = null

                            findNavController().currentBackStackEntry?.savedStateHandle?.set(
                                REGION_BACKSTACK_KEY,
                                null
                            )

                            submitButton?.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        countryTextInput?.addTextChangedListener(countryTextWatcher)
        regionTextInput?.addTextChangedListener(regionTextWatcher)
    }

    private fun setBackStackListeners() {
        with(findNavController().currentBackStackEntry?.savedStateHandle) {
            this?.getLiveData<Region?>(REGION_BACKSTACK_KEY)?.observe(viewLifecycleOwner) { region ->
                regionModel = region

                if (countryModel == null) {
                    countryModel = regionModel?.parentCountry
                    countryTextInput?.setText(regionModel?.parentCountry?.name)
                }

                if (region != null) {
                    regionTextInput?.setText(region.name)
                    submitButton?.visibility = View.VISIBLE
                }
            }

            this?.getLiveData<Country>(COUNTRY_BACKSTACK_KEY)?.observe(viewLifecycleOwner) { country ->
                countryModel = country
                regionModel = get(REGION_BACKSTACK_KEY)

                if (country != null) {
                    countryTextInput?.setText(country.name)
                    submitButton?.visibility = View.VISIBLE

                    if (countryModel?.id != regionModel?.parentCountry?.id) {
                        regionTextInput?.text?.clear()
                        regionModel = null
                        findNavController().currentBackStackEntry?.savedStateHandle?.set(
                            REGION_BACKSTACK_KEY,
                            null
                        )
                    }
                }
            }
        }
    }
}
