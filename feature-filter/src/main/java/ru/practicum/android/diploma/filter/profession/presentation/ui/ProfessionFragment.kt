package ru.practicum.android.diploma.filter.profession.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.databinding.FragmentProfessionBinding
import ru.practicum.android.diploma.filter.profession.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.profession.presentation.ui.adapters.FilterIndustryListAdapter
import ru.practicum.android.diploma.filter.profession.presentation.viewmodel.ProfessionViewModel

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L

internal class ProfessionFragment : Fragment() {
    private var _binding: FragmentProfessionBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""

    private val professionViewModel: ProfessionViewModel by viewModel()

    val debouncedFiltration by lazy {
        debounce(
            delayMillis = DELAY_CLICK_VACANCY,
            coroutineScope = lifecycleScope,
            useLastParam = true,
            actionThenDelay = false,
            action = { param: String ->
                if (param == "") {
                    professionViewModel.resetToFullList()
                } else {
                    professionViewModel.filterList(param)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfessionBinding.inflate(inflater, container, false)

        professionViewModel.industriesListLiveData.observe(viewLifecycleOwner) { list ->
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
            professionViewModel.resetToFullList()
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

    private fun showConfirmation(selectedIndustry: IndustryModel?) {
        binding.selectButton.isVisible = true
        binding.selectButton.setOnClickListener {
            // ❗ todo pass selectedIndustry to filter fragment or viewmodel here or do sharedprefs
            findNavController().navigateUp()
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
