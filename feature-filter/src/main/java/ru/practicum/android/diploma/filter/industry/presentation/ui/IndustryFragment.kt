package ru.practicum.android.diploma.filter.industry.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.presentation.ui.adapters.FilterIndustryListAdapter
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.IndustryViewModel

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L
private const val ARGS_INDUSTRY_ID = "industry_id"
private const val ARGS_INDUSTRY_NAME = "industry_name"

internal class IndustryFragment : Fragment() {
    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    private val industryViewModel: IndustryViewModel by viewModel()

    val debouncedFiltration by lazy {
        debounce(
            delayMillis = DELAY_CLICK_VACANCY,
            coroutineScope = lifecycleScope,
            useLastParam = true,
            actionThenDelay = false,
            action = { param: String ->
                if (param == "") {
                    industryViewModel.resetToFullList()
                } else {
                    industryViewModel.filterList(param)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndustryBinding.inflate(inflater, container, false)

        industryViewModel.industriesListLiveData.observe(viewLifecycleOwner) { list ->
            if (list == null) {
                binding.vacancyRecycler.isVisible = false
                binding.industriesError.isVisible = true
                binding.industriesErrorText.isVisible = true
                unselectAndHideConfirmation()
            } else {
                (binding.vacancyRecycler.adapter as FilterIndustryListAdapter).setOptionsList(list)
                binding.vacancyRecycler.isVisible = true
                binding.industriesError.isVisible = false
                binding.industriesErrorText.isVisible = false
            }
        }

        recyclerSetup()
        searchBarSetup()

        return binding.root
    }

    private fun searchBarSetup() {
        binding.searchBar.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                unselectAndHideConfirmation()
                binding.clearSearchIcon.isVisible = true
                binding.searchBarLoupeIcon.isVisible = false
                debouncedFiltration(text.toString())
            } else {
                binding.clearSearchIcon.isVisible = false
                binding.searchBarLoupeIcon.isVisible = true
                debouncedFiltration(text.toString())
            }
            userInputReserve = text.toString()
        }

        binding.clearSearchIcon.setOnClickListener {
            binding.searchBar.text.clear()
            requireContext().closeKeyBoard(binding.searchBar)
            industryViewModel.resetToFullList()
            unselectAndHideConfirmation()
        }

    }

    private fun recyclerSetup() {
        val adapter = FilterIndustryListAdapter(object : FilterIndustryListAdapter.IndustryClickListener {

            override fun onIndustryClick(industryModel: IndustryModel?) {
                if (industryModel == null) {
                    binding.selectButton.isVisible = false
                } else {
                    showConfirmation(industryModel)
                }
            }
        })
        binding.vacancyRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.vacancyRecycler.adapter = adapter
        (binding.vacancyRecycler.adapter as FilterIndustryListAdapter).setOptionsList(emptyList())

    }

    private fun showConfirmation(selectedIndustry: IndustryModel) {
        binding.selectButton.isVisible = true
        binding.selectButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_industryFragment_to_filterFragment, createArgs(selectedIndustry)
                )
        }
    }

    private fun unselectAndHideConfirmation() {
        (binding.vacancyRecycler.adapter as FilterIndustryListAdapter).undoSelection()
        binding.selectButton.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLeftIndustry.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isAdded) {
            outState.putString(USER_INPUT, userInputReserve)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            userInputReserve = savedInstanceState.getString(USER_INPUT, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun createArgs(selectedIndustry: IndustryModel): Bundle? =
            bundleOf(ARGS_INDUSTRY_ID to selectedIndustry.id, ARGS_INDUSTRY_NAME to selectedIndustry.name)
    }
}
