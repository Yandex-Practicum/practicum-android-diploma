package ru.practicum.android.diploma.filter.industry.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentBranchBinding
import ru.practicum.android.diploma.filter.industry.domain.model.Industry
import ru.practicum.android.diploma.filter.industry.presentation.BranchAdapter
import ru.practicum.android.diploma.filter.industry.presentation.BranchScreenState
import ru.practicum.android.diploma.filter.industry.presentation.BranchViewModel

class BranchFragment : Fragment() {

    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<BranchViewModel>()
    private var branchAdapter: BranchAdapter = BranchAdapter { industry, index ->
        viewModel.onSelectIndustryEvent(industry, index)
    }
    private var selectedIndustry: Industry? = null
    private var selectedIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBranches(binding.search.text.toString(), selectedIndustry)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        initListeners()
        binding.branchRecycler.adapter = branchAdapter
        binding.branchRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.branchRecycler.isVisible = true
        binding.btnSave.isVisible = false
    }

    private fun render(branchScreenState: BranchScreenState) {
        when (branchScreenState) {
            is BranchScreenState.Error -> showError()
            is BranchScreenState.Content -> showContent(branchScreenState)
        }
    }

    private fun showError() {
        binding.branchRecycler.isVisible = false
        binding.btnSave.isVisible = false
    }

    private fun showContent(content: BranchScreenState.Content) {
        val newIndustries = content.branches.map { it to false }.toMutableList()
        if (content.selectedIndex != null && content.selectedIndustry != null) {
            for (i in content.branches.indices) {
                if (selectedIndustry?.id == content.branches[i].id) {
                    selectedIndex = i
                    newIndustries[i] = newIndustries[i].copy(second = false)
                    break
                }
            }
            newIndustries[content.selectedIndex] = newIndustries[content.selectedIndex].copy(second = true)
            selectedIndustry = content.selectedIndustry
            selectedIndex = content.selectedIndex
            binding.btnSave.isVisible = true
        } else {
            binding.btnSave.isVisible = false
        }
        branchAdapter.branches.clear()
        branchAdapter.branches.addAll(newIndustries)
        branchAdapter.notifyDataSetChanged()
        binding.branchRecycler.isVisible = true
    }

    private fun initListeners() {
        binding.filterToolbarBranch.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            if (viewModel.clickDebounce()) {
                selectedIndustry?.let { industry ->
                    viewModel.saveIndustry(industry)
                }
                findNavController().popBackStack()
            }
        }

        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.getBranches(binding.search.text.toString(), selectedIndustry)
            if (text.isNullOrEmpty()) {
                binding.btnClear.setImageResource(R.drawable.ic_search)
            } else {
                binding.btnClear.setImageResource(R.drawable.ic_close)
            }
        }

        binding.btnClear.setOnClickListener {
            binding.search.text = null
        }
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getBranches(binding.search.text.toString(), selectedIndustry)
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(binding.search.windowToken, 0)
            }
            true
        }
    }
}
