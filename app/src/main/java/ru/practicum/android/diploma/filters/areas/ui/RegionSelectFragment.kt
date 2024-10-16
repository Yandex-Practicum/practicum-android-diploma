package ru.practicum.android.diploma.filters.areas.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.RegionSelectFragmentBinding
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.presentation.RegionSelectViewModel
import ru.practicum.android.diploma.filters.areas.ui.presentation.RegionSelectScreenState
import ru.practicum.android.diploma.filters.industries.ui.IndustrySelectFragment
import ru.practicum.android.diploma.util.hideKeyboard

class RegionSelectFragment : Fragment() {
    private var _binding: RegionSelectFragmentBinding? = null
    private val binding get() = _binding!!

    private var _adapter: RegionSelectRecyclerViewAdapter? = null
    private val adapter get() = _adapter!!
    private var inputTextValue = DEF_TEXT
    private val viewModel by viewModel<RegionSelectViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegionSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        _adapter = RegionSelectRecyclerViewAdapter {
            onAreaClick(it)
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // detekt
            }

            override fun onTextChanged(textInInputField: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility(textInInputField)

                inputTextValue = textInInputField.toString()

                viewModel.searchDebounce(
                    changedText = inputTextValue
                )
            }

            override fun afterTextChanged(s: Editable?) {
                // detekt
            }
        }
        binding.searchLine.addTextChangedListener(searchTextWatcher)
        binding.searchLineCleaner.setOnClickListener {
            clearFilter()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllRegions()
    }

    private fun clearButtonVisibility(text: CharSequence?) {
        val visibility = !text.isNullOrEmpty()
        binding.searchLineCleaner.isVisible = visibility
        binding.icSearch.isVisible = !visibility
    }

    private fun onAreaClick(area: Area) {
        viewModel.finishSelect(area.id)
        findNavController().popBackStack()
    }

    private fun render(state: RegionSelectScreenState) {
        when (state) {
            is RegionSelectScreenState.ChooseItem -> showContent(state.items)
            RegionSelectScreenState.Empty -> showEmpty()
            RegionSelectScreenState.NetworkError -> showNetworkError()
            RegionSelectScreenState.ServerError -> showServerError()
            is RegionSelectScreenState.FilterRequest -> showFilteredResult(state.request)
        }
    }

    private fun showContent(item: List<Area>) {
        binding.recyclerView.isVisible = true
        binding.emptyPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        adapter.list.clear()
        adapter.list.addAll(item)
        adapter.notifyDataSetChanged()
    }

    private fun showFilteredResult(request: String) {
        view?.hideKeyboard()
        adapter.filterResults(request)
    }

    private fun showEmpty() {
        binding.notFoundPlaceholder.isVisible = true
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = false
    }

    private fun showNetworkError() {
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = true
    }

    private fun showServerError() {
        binding.notFoundPlaceholder.isVisible = false
        binding.recyclerView.isVisible = false
        binding.emptyPlaceholder.isVisible = true
        binding.image.setImageResource(R.drawable.search_server_error_placeholder)
        binding.text.setText(R.string.server_error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearFilter() {
        viewModel.getAllRegions()
        binding.searchLine.setText(IndustrySelectFragment.DEF_TEXT)
    }

    companion object {
        const val DEF_TEXT = ""
    }
}
