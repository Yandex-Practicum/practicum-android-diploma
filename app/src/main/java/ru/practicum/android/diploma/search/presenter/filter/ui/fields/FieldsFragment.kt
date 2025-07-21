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

    // private val placeholderNotFound by lazy { binding.notFoundPlaceholder }
    // private val placeholderError by lazy { binding.errorPlaceholder }
    // private val progressBar by lazy { binding.progressBar }


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
            if (debouncer?.clickDebounce() == true) {
                viewModel.onIndustrySelected(industry)
                findNavController().popBackStack()
            }
        }
        binding.fieldsRecyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.backButtonId.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fieldEdittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                debouncer?.searchDebounce {
                    s?.let { viewModel.filter(it.toString()) }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
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
        // binding.progressBar.isVisible = state is FieldsState.Loading
        binding.fieldsRecyclerView.isVisible = state is FieldsState.Content
        // binding.notFoundPlaceholder.isVisible = state is FieldsState.Empty
        // binding.errorPlaceholder.isVisible = state is FieldsState.Error

        when (state) {
            is FieldsState.Content -> {
                adapter.submitList(state.industries)
            }
            is FieldsState.Error -> {
                // Обработка ошибки
            }
            is FieldsState.Empty -> {
                // Показать плейсхолдер "Ничего не найдено"
            }
            is FieldsState.Loading -> {
                // Показать ProgressBar
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
