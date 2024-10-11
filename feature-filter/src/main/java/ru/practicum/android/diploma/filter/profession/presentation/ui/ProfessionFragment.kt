package ru.practicum.android.diploma.filter.profession.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.databinding.FragmentProfessionBinding
import ru.practicum.android.diploma.filter.profession.domain.model.Industry
import ru.practicum.android.diploma.filter.profession.presentation.ui.adapters.FilterIndustryListAdapter

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L
private const val NUMBER_OF_ITEMS_FOR_TESTING = 10 // ❗ delete when proper list is available

internal class ProfessionFragment : Fragment() {
    private var _binding: FragmentProfessionBinding? = null
    private val binding get() = _binding!!
    private var localIndustriesList: ArrayList<Industry> = ArrayList()
    private var userInputReserve = ""

    private val professionViewModel: ViewModel by viewModel()

    val debouncedFiltration by lazy {
        debounce(
            delayMillis = DELAY_CLICK_VACANCY,
            coroutineScope = lifecycleScope,
            useLastParam = true,
            actionThenDelay = false,
            action = { param: String ->
                // ❗ professionViewModel.someMethod(param)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfessionBinding.inflate(inflater, container, false)

        recyclerSetup()
        searchBarSetup()

        for (i in 0 until NUMBER_OF_ITEMS_FOR_TESTING) { // ❗ delete when proper list is available
            val mockIndustry = Industry(
                "$i",
                "$i$i$i$i$i$i$i$i$i"
            ) // ❗
            localIndustriesList.add(mockIndustry) // ❗
        } // ❗

        return binding.root
    }

    private fun searchBarSetup() {
        binding.searchBar.doOnTextChanged { text, start, before, count ->
            if (text?.isNotEmpty() == true) {
                unselectAndHideConfirmation()
                binding.clearSearchIcon.isVisible = true
                binding.searchBarLoupeIcon.isVisible = false
                localIndustriesList = ArrayList()
                debouncedFiltration(text.toString())
            } else {
                binding.clearSearchIcon.isVisible = false
                binding.searchBarLoupeIcon.isVisible = true
            }
            userInputReserve = text.toString()
        }

        binding.clearSearchIcon.setOnClickListener {
            binding.searchBar.text.clear()
            requireContext().closeKeyBoard(binding.searchBar)
            localIndustriesList = ArrayList()
            unselectAndHideConfirmation()
        }

    }

    private fun recyclerSetup() {
        val adapter = FilterIndustryListAdapter(object : FilterIndustryListAdapter.IndustryClickListener {

            override fun onIndustryClick(industry: Industry?) {
                if (industry == null) {
                    binding.selectButton.isVisible = false
                } else {
                    showConfirmation(industry)
                }
            }
        })
        binding.vacancyRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.vacancyRecycler.adapter = adapter
        (binding.vacancyRecycler.adapter as FilterIndustryListAdapter).setOptionsList(localIndustriesList)

    }

    private fun showConfirmation(selectedIndustry: Industry?) {
        binding.selectButton.isVisible = true
        binding.selectButton.setOnClickListener {
            // ❗ pass selectedIndustry to viewmodel here
        }
    }

    private fun unselectAndHideConfirmation() {
        (binding.vacancyRecycler.adapter as FilterIndustryListAdapter).undoSelection()
        binding.selectButton.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLeftProfession.setOnClickListener {
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
}
