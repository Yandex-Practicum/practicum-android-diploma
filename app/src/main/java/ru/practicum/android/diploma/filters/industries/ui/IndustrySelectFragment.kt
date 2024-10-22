package ru.practicum.android.diploma.filters.industries.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.IndustrySelectFragmentBinding
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.filters.industries.presentation.IndustrySelectScreenState
import ru.practicum.android.diploma.filters.industries.presentation.IndustrySelectViewModel
import ru.practicum.android.diploma.filters.industries.ui.presenter.IndustrySelectRecyclerViewAdapter
import ru.practicum.android.diploma.util.hideKeyboard

class IndustrySelectFragment : Fragment() {

    private var _adapter: IndustrySelectRecyclerViewAdapter? = null
    private val adapter get() = _adapter!!

    private var _binding: IndustrySelectFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<IndustrySelectViewModel>()

    private var inputTextValue = DEF_TEXT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IndustrySelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        _adapter = IndustrySelectRecyclerViewAdapter {
            onIndustryClick(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.recyclerView.adapter = adapter

        viewModel.getStateLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }

        binding.searchLine.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchDebounce(inputTextValue)
                view.hideKeyboard()
                true
            }
            false
        }

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // коммент для детекта
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(s)

                inputTextValue = s.toString()

                viewModel.searchDebounce(
                    changedText = inputTextValue
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // коммент для детекта
            }
        }
        binding.searchLine.addTextChangedListener(searchTextWatcher)

        binding.searchLineCleaner.setOnClickListener {
            clearFilter()
        }

        binding.applyButton.setOnClickListener {
            viewModel.transferIndustryToQuery()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onIndustryClick(industry: Industry) {
        binding.applyButton.isVisible = true
        viewModel.onItemClick(industry)
        adapter.notifyDataSetChanged()
    }

    private fun render(state: IndustrySelectScreenState) {
        when (state) {
            is IndustrySelectScreenState.ChooseItem -> showContent(state.items)
            IndustrySelectScreenState.Empty -> showEmpty()
            IndustrySelectScreenState.NetworkError -> showNetworkError()
            IndustrySelectScreenState.ServerError -> showServerError()
            is IndustrySelectScreenState.FilterRequest -> showFilteredResult(state.request)
            IndustrySelectScreenState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.progressCircular.isVisible = true
        binding.notFoundPlaceholder.isVisible = false
        binding.serverErrorPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
    }

    private fun showContent(item: List<Industry>) {
        binding.progressCircular.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.serverErrorPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = false
        binding.recyclerView.isVisible = true
        adapter.list.clear()
        adapter.list.addAll(item)
        adapter.notifyDataSetChanged()

    }

    private fun showNetworkError() {
        binding.progressCircular.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.serverErrorPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = true
        binding.recyclerView.isVisible = false
    }

    private fun showServerError() {
        binding.progressCircular.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.serverErrorPlaceholder.isVisible = true
        binding.notConnectedPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
    }

    private fun showEmpty() {
        binding.progressCircular.isVisible = false
        binding.notFoundPlaceholder.isVisible = true
        binding.serverErrorPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
    }

    private fun showFilteredResult(request: String) {
        adapter.filterResults(request)

        binding.progressCircular.isVisible = false
        binding.serverErrorPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = adapter.list.isEmpty() && request.isNotEmpty()
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        val visibility = !s.isNullOrEmpty()
        binding.searchLineCleaner.isVisible = visibility
        binding.icSearch.isVisible = !visibility
    }

    private fun clearFilter() {
        view?.hideKeyboard()
        binding.searchLine.setText(DEF_TEXT)
    }

    companion object {
        const val DEF_TEXT = ""
    }
}
