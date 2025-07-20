package ru.practicum.android.diploma.ui.searchfilters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.SearchFiltersFragmentBinding
import ru.practicum.android.diploma.domain.models.filters.FilterParameters
import ru.practicum.android.diploma.presentation.SearchFiltersViewModel

class SearchFiltersFragment : Fragment() {

    private var _binding: SearchFiltersFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SearchFiltersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = SearchFiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextWorkplace.setOnClickListener {
            findNavController().navigate(R.id.action_searchFiltersFragment_to_workplaceFiltersFragment)
        }

        binding.editTextIndustry.setOnClickListener {
            findNavController().navigate(R.id.action_searchFiltersFragment_to_industryFilterFragment)
        }

        binding.editText.doOnTextChanged { text, start, before, count ->
            val query = text?.toString()

            if (query?.isNotEmpty() == true && binding.editText.hasFocus()) {
                binding.topHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.icon.isVisible = true
                binding.icon.setOnClickListener {
                    binding.editText.setText("")
                }
                binding.btnApply.isVisible = true
                binding.btnCancel.isVisible = true

            } else if (query?.isNotEmpty() == true) {
                binding.topHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

            } else {
                binding.icon.isVisible = false
                binding.btnApply.isVisible = false
                binding.btnCancel.isVisible = false
                binding.topHint.setTextColor(
                    MaterialColors.getColor(
                        requireContext(),
                        com.google.android.material.R.attr.colorOnContainer,
                        Color.BLACK
                    )
                )
            }
        }
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.inputLayoutWorkplace.setEndIconOnClickListener {
            viewModel.clearWorkplace()
        }

        binding.btnCancel.setOnClickListener {
            binding.editText.setText("")
            binding.materialCheckbox.isChecked = false
            viewModel.resetFilters()
        }

        binding.btnApply.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean(SEARCH_WITH_FILTERS_KEY, true)
            }
            setFragmentResult(SEARCH_WITH_FILTERS_KEY, bundle)
            findNavController().popBackStack()
        }

        viewModel.getFiltersParametersScreen.observe(viewLifecycleOwner) {
            renderWorkplace(it)
        }

        viewModel.loadParameters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderWorkplace(state: FilterParameters) {
        val country = state.countryName
        val region = state.regionName
        val isEmpty = country.isNullOrBlank() && region.isNullOrBlank()

        val workplaceText = listOfNotNull(country, region)
            .filter { it.isNotBlank() }
            .joinToString(", ")

        binding.editTextWorkplace.setText(workplaceText)

        binding.inputLayoutWorkplace.hint = if (isEmpty) getString(R.string.workplace) else ""

        val icon = if (isEmpty) R.drawable.arrow_forward_24px else R.drawable.close_24px
        binding.inputLayoutWorkplace.setEndIconDrawable(icon)

        binding.btnApply.isVisible = !isEmpty
        binding.btnCancel.isVisible = !isEmpty
    }

    companion object{
        const val SEARCH_WITH_FILTERS_KEY ="search_with_filters_key"
    }
}
