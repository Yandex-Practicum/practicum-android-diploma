package ru.practicum.android.diploma.filter.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterScreenViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterSettingsViewModel
import ru.practicum.android.diploma.filter.ui.adapters.IndustryAdapter

class FilterIndustryFragment : Fragment() {
    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterScreenViewModel>()
    private val viewModelSettings by viewModel<FilterSettingsViewModel>()
    private var listAdapter = IndustryAdapter { clickOnIndustry(it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchDebounce("")
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIcon.isVisible = !s.isNullOrEmpty()
                binding.searchIcon.isVisible = s.isNullOrEmpty()
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }
            override fun afterTextChanged(s: Editable?) = Unit
        }
        textWatcher.let { binding.textInput.addTextChangedListener(it) }

        binding.topBar.setOnClickListener {
            findNavController().navigate(R.id.action_filterIndustryFragment_to_filterSettingsFragment)
        }
        binding.btnSelectIndustry.setOnClickListener {
            viewModelSettings.updateFilter(
                Filter(
                    industrySP = viewModel.getIndustry()
                )
            )
            findNavController().navigate(R.id.action_filterIndustryFragment_to_filterSettingsFragment)
        }

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.apply {
            rvIndustryList.adapter = listAdapter
            clearIcon.setOnClickListener {
                if (!progressBar.isVisible) {
                    textInput.text.clear()
                    closeKeyboard()
                }
            }
        }
    }

    private fun render(state: IndustryViewState) {
        when (state) {
            is IndustryViewState.Success -> {
                listAdapter.setIndustries(state.industryList)
                binding.rvIndustryList.isVisible = true
                binding.progressBar.isVisible = false
                binding.noFoundPH.isVisible = false
                binding.serverErrorPH.isVisible = false
                binding.rvIndustryList.removeAllViews()
            }
            is IndustryViewState.Loading -> {
                binding.rvIndustryList.isVisible = false
                binding.progressBar.isVisible = true
                binding.noFoundPH.isVisible = false
                binding.serverErrorPH.isVisible = false
                binding.btnSelectIndustry.isVisible = false
            }
            is IndustryViewState.ConnectionError, is IndustryViewState.ServerError -> {
                binding.rvIndustryList.isVisible = false
                binding.progressBar.isVisible = false
                binding.noFoundPH.isVisible = false
                binding.serverErrorPH.isVisible = true
                binding.btnSelectIndustry.isVisible = false
            }
            is IndustryViewState.NotFoundError -> {
                binding.rvIndustryList.isVisible = false
                binding.progressBar.isVisible = false
                binding.noFoundPH.isVisible = true
                binding.serverErrorPH.isVisible = false
                binding.btnSelectIndustry.isVisible = false
            }
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity?.currentFocus ?: view
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun clickOnIndustry(industry: Industry) {
        binding.btnSelectIndustry.isVisible = true
        viewModel.setIndustry(industry)
    }

}
