package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.ui.filter.FilterViewModel
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class IndustryFilterFragment : BindingFragment<FragmentIndustryFilterBinding>() {

    private val viewModel by activityViewModel<FilterViewModel>()
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
            viewModel.saveSelectedIndustry()
            findNavController().popBackStack()
        }

        // системная кн назад
        handleBackPress()
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
