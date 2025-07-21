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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.practicum.android.diploma.databinding.FragmentFieldsBinding
import ru.practicum.android.diploma.search.presenter.filter.model.FieldsState
import ru.practicum.android.diploma.search.presenter.filter.ui.fields.viewmodel.FieldsViewModel
import ru.practicum.android.diploma.util.Debouncer

class FieldsFragment : Fragment() {

    private var _binding: FragmentFieldsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FieldsViewModel by viewModel()
    private lateinit var adapter: IndustriesAdapter
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

        setupRecyclerView()
        setupListeners()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = IndustriesAdapter { industry ->
            viewModel.onIndustrySelected(industry)
        }
        binding.fieldsRecyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.backButtonId.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.selectButton.setOnClickListener {
            viewModel.applySelection()
            findNavController().popBackStack()
        }

        binding.fieldEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                debouncer?.searchDebounce {
                    s?.let { viewModel.filter(it.toString()) }
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderState(state)
            }
        }
    }

    private fun renderState(state: FieldsState) {
        binding.selectButton.isVisible = state is FieldsState.Content && state.selectedIndustry != null

        when (state) {
            is FieldsState.Content -> {
                binding.fieldsRecyclerView.isVisible = true
                adapter.submitList(state.industries) {
                    adapter.updateSelection(state.selectedIndustry)
                }
            }
            is FieldsState.Empty -> {
                binding.fieldsRecyclerView.isVisible = false
            }
            is FieldsState.Loading -> {
                binding.fieldsRecyclerView.isVisible = false
            }
            is FieldsState.Error -> {
                binding.fieldsRecyclerView.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        debouncer?.cancelDebounce()
        debouncer = null
        _binding = null
    }
}
