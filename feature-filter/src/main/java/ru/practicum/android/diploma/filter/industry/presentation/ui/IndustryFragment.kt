package ru.practicum.android.diploma.filter.industry.presentation.ui

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
import ru.practicum.android.diploma.commonutils.Utils
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentIndustryBinding
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.presentation.ui.adapters.FilterIndustryListAdapter
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.IndustryViewModel
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.state.IndustryState

private const val USER_INPUT = "userInput"
private const val DELAY_CLICK_VACANCY = 2000L

internal class IndustryFragment : Fragment() {

    private var _binding: FragmentIndustryBinding? = null
    private val binding get() = _binding!!
    private var userInputReserve = ""
    private var currentIndustryBuffer: IndustryModel = IndustryModel(null, null)
    private var currentIndustry: IndustryModel = IndustryModel(null, null)

    private var viewArray: Array<View>? = null

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
        viewArray = arrayOf(
            binding.loadingProgressBar,
            binding.listIndustry,
            binding.placeholderIndustriesError,
            binding.placeholderIndustryDoesNotExist
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        industryViewModel.industriesListLiveData.observe(viewLifecycleOwner) { state ->
            render(state)
            industryViewModel.getBranchOfProfessionDataFilterBuffer()
        }

        industryViewModel.currentIndustryInDataFilterLiveData.observe(viewLifecycleOwner) { industry ->
            industry?.let {
                (binding.listIndustry.adapter as FilterIndustryListAdapter).selectItemById(it.id)
            }
            binding.selectButton.visibility = View.GONE
            currentIndustryBuffer = industry ?: IndustryModel(null, null)
            currentIndustry = industry ?: IndustryModel(null, null)
        }

        recyclerSetup()
        searchBarSetup()

        binding.buttonLeftIndustry.setOnClickListener(listener)
        binding.selectButton.setOnClickListener(listener)
    }

    private val listener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.buttonLeftIndustry -> {
                findNavController().navigateUp()
            }
            R.id.selectButton -> {
                industryViewModel.updateProfessionInDataFilterBuffer(currentIndustry)
                findNavController().navigateUp()
            }
        }
    }

    private fun render(state: IndustryState) {
        when (state) {
            is IndustryState.Loading -> {
                Utils.visibilityView(viewArray, binding.loadingProgressBar)
            }
            is IndustryState.Content -> {
                (binding.listIndustry.adapter as FilterIndustryListAdapter).setOptionsList(state.industryList)
                Utils.visibilityView(viewArray, binding.listIndustry)
            }
            is IndustryState.Empty -> {
                (binding.listIndustry.adapter as FilterIndustryListAdapter).undoSelection()
                Utils.visibilityView(viewArray, binding.placeholderIndustryDoesNotExist)
            }
            is IndustryState.Error -> {
                (binding.listIndustry.adapter as FilterIndustryListAdapter).undoSelection()
                Utils.visibilityView(viewArray, binding.placeholderIndustriesError)
            }
        }
    }

    private fun searchBarSetup() {
        binding.searchBar.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotEmpty() == true) {
                (binding.listIndustry.adapter as FilterIndustryListAdapter).undoSelection()
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
            (binding.listIndustry.adapter as FilterIndustryListAdapter).undoSelection()
        }

    }

    private fun recyclerSetup() {
        val adapter = FilterIndustryListAdapter(object : FilterIndustryListAdapter.IndustryClickListener {

            override fun onIndustryClick(industryModel: IndustryModel?) {
                binding.selectButton.isVisible = industryModel?.id != currentIndustryBuffer.id
                currentIndustry = industryModel ?: IndustryModel(null, null)
                requireContext().closeKeyBoard(binding.searchBar)
            }
        })
        binding.listIndustry.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.listIndustry.adapter = adapter
        (binding.listIndustry.adapter as FilterIndustryListAdapter).setOptionsList(emptyList())

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
        viewArray = null
    }
}
