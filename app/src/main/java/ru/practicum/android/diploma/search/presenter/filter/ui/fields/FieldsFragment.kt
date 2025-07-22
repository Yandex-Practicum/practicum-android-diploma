package ru.practicum.android.diploma.search.presenter.filter.ui.fields

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFieldsBinding
import ru.practicum.android.diploma.search.domain.model.Industry
import ru.practicum.android.diploma.search.presenter.filter.FiltersViewModel
import ru.practicum.android.diploma.search.presenter.filter.model.FieldsState
import ru.practicum.android.diploma.search.presenter.filter.ui.fields.viewmodel.FieldsViewModel
import ru.practicum.android.diploma.util.Debouncer

class FieldsFragment : Fragment() {

    private var _binding: FragmentFieldsBinding? = null
    private val binding get() = _binding!!

    private val fieldsViewModel: FieldsViewModel by viewModel()
    private val filtersViewModel: FiltersViewModel by sharedViewModel()
    private val adapter by lazy {
        IndustriesAdapter { industry ->
            fieldsViewModel.onIndustrySelected(industry)
        }
    }
    private var debouncer: Debouncer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFieldsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debouncer = get { parametersOf(viewLifecycleOwner.lifecycleScope) }
        binding.fieldsRecyclerView.adapter = adapter
        setupListeners()
        observeViewModel()
        clearEditText()
    }

    private fun setupListeners() {
        binding.backButtonId.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.selectButton.setOnClickListener {
            val currentState = fieldsViewModel.state.value
            if (currentState is FieldsState.Content) {
                filtersViewModel.updateIndustry(currentState.selectedIndustry)
            }
            findNavController().popBackStack()
        }

        binding.fieldEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSearchIcon(s)
                debouncer?.searchDebounce {
                    s?.let { fieldsViewModel.filter(it.toString()) }
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun updateSearchIcon(text: CharSequence?) {
        if (text.isNullOrBlank()) {
            binding.searchIcon.setImageResource(R.drawable.search_24px)
            binding.searchIcon.tag = R.drawable.search_24px
        } else {
            binding.searchIcon.setImageResource(R.drawable.cross_light)
            binding.searchIcon.tag = R.drawable.cross_light
        }
    }

    private fun clearEditText() {
        binding.searchIcon.setOnClickListener {
            if (binding.searchIcon.tag == R.drawable.cross_light) {
                binding.fieldEdittext.text.clear()
                debouncer?.cancelDebounce()
                fieldsViewModel.filter("")
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            fieldsViewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: FieldsState) {
        when (state) {
            is FieldsState.Content -> showContent(state.industries, state.selectedIndustry)
            is FieldsState.Empty -> showEmpty()
            is FieldsState.Error -> showError()
            is FieldsState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.fieldsRecyclerView.isVisible = false
        binding.placeholderEmpty.isVisible = false
        binding.placeholderError.isVisible = false
        binding.selectButton.isVisible = false
    }

    private fun showContent(industries: List<Industry>, selectedIndustry: Industry?) {
        binding.progressBar.isVisible = false
        binding.fieldsRecyclerView.isVisible = true
        binding.placeholderEmpty.isVisible = false
        binding.placeholderError.isVisible = false
        binding.selectButton.isVisible = selectedIndustry != null
        adapter.submitList(industries) {
            adapter.updateSelection(selectedIndustry)
        }
    }

    private fun showError() {
        binding.progressBar.isVisible = false
        binding.fieldsRecyclerView.isVisible = false
        binding.placeholderEmpty.isVisible = false
        binding.placeholderError.isVisible = true
        binding.selectButton.isVisible = false
    }

    private fun showEmpty() {
        binding.progressBar.isVisible = false
        binding.fieldsRecyclerView.isVisible = false
        binding.placeholderEmpty.isVisible = true
        binding.placeholderError.isVisible = false
        binding.selectButton.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debouncer?.cancelDebounce()
        debouncer = null
        _binding = null
    }
}
