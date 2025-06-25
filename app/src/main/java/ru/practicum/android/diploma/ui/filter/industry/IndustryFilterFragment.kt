package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.ui.root.RootActivity

class IndustryFilterFragment : BindingFragment<FragmentIndustryFilterBinding>() {

    private val viewModel: IndustryViewModel by viewModel()
    private var industryAdapter: IndustryAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.industrySearch.searchEditText.hint = getString(R.string.enter_industry)

        binding.industrySearch.searchEditText.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                viewModel.filterIndustries(text.toString())
            }
        )

        viewModel.getIndustries()

        industryAdapter = IndustryAdapter(object : IndustryClickListener {
            override fun onIndustryClick(selectedIndustry: IndustryListItem) {
                val query = binding.industrySearch.searchEditText.text.toString()
                viewModel.selectIndustry(selectedIndustry.id, query)
            }
        })

        binding.industryRecyclerView.adapter = industryAdapter

        viewModel.industryState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryState.CONTENT -> {
                    showContent(state)
                }
                is IndustryState.ERROR -> {
                    showError(state)
                }
                is IndustryState.EMPTY -> {
                    showEmpty()
                }
                is IndustryState.LOADING -> {
                    showLoading()
                }
            }
        }

        binding.buttonActionIndustry.buttonBlue.setOnClickListener {
            viewModel.getSelectedIndustry()?.let { selected ->
                val action = IndustryFilterFragmentDirections.actionIndustryFilterFragmentToFilterFragment(
                    selectedIndustryId = selected.id,
                    selectedIndustryName = selected.name
                )
                findNavController().navigate(action)
            }
        }

        // Системная кнопка или жест назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment(false)
        }

        initUiTopbar()
    }

    private fun initUiTopbar() {
        binding.topbar.apply {
            btnFirst.setImageResource(R.drawable.arrow_back_24px)
            btnSecond.isVisible = false
            btnThird.isVisible = false
            header.text = requireContext().getString(R.string.area)
        }

        binding.topbar.btnFirst.setOnClickListener {
            closeFragment(false)
        }
    }

    private fun closeFragment(barVisibility: Boolean) {
        (activity as RootActivity).setNavBarVisibility(barVisibility)
        findNavController().popBackStack()
    }

    private fun showContent(state: IndustryState.CONTENT) {
        binding.industryRecyclerView.visibility = View.VISIBLE
        binding.includedProgressBar.root.visibility = View.GONE
        binding.placeholderNoList.visibility = View.GONE
        binding.placeholderNoIndustry.visibility = View.GONE
        industryAdapter?.submitList(state.industryListItems)
    }

    private fun showError(state: IndustryState.ERROR) {
        binding.industryRecyclerView.visibility = View.GONE
        binding.includedProgressBar.root.visibility = View.GONE
        binding.placeholderNoList.visibility = View.VISIBLE
        binding.placeholderNoIndustry.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.industryRecyclerView.visibility = View.GONE
        binding.includedProgressBar.root.visibility = View.GONE
        binding.placeholderNoList.visibility = View.GONE
        binding.placeholderNoIndustry.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.industryRecyclerView.visibility = View.GONE
        binding.includedProgressBar.root.visibility = View.VISIBLE
    }
}
