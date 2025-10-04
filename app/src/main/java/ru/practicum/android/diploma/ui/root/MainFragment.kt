package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.ui.models.SearchState
import ru.practicum.android.diploma.ui.view_models.MainViewModel
import ru.practicum.android.diploma.util.SearchDebounce

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var textWatcher: TextWatcher? = null
    private val viewModel: MainViewModel by viewModel()
    private val debounce = SearchDebounce<String>(scope = lifecycleScope)
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        val trailingButton = binding.trailingButton
        trailingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtrationFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.dataList
        textWatcher = binding.editSearchText.doOnTextChanged { text, _, _, _ ->
            binding.clearTheField.isVisible = !text.isNullOrEmpty()
            if (text.isNullOrEmpty()) {
                viewModel.setIdleState()
            } else {
                viewModel.setNothingState()
            }
            debounce.execute(text.toString())
        }
        textWatcher.let { binding.editSearchText.addTextChangedListener(it) }
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners() {
        binding.clearTheField.setOnClickListener {
            binding.editSearchText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(
                binding.clearTheField.windowToken,
                0
            )
        }
    }

    private fun setupObservers() {
        viewModel.observeSearchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Idle -> {
                    renderImage(true, R.drawable.image_search_default)
                    renderLoading(false)
                    renderRecyclerSearchList(false)
                }

                is SearchState.Nothing -> {
                    renderImage(false)
                    renderLoading(false)
                    renderRecyclerSearchList(false)
                }

                is SearchState.Loading -> {
                    renderImage(false)
                    renderLoading(true)
                    renderRecyclerSearchList(false)
                }

                is SearchState.Complete -> {
                    renderLoading(false)
                    if (state.data.isNotEmpty()) {
                        renderRecyclerSearchList(true, data = state.data)
                    } else {
                        renderImage(true, R.drawable.cat_with_the_plate, R.string.no_list_vacancies)
                    }
                }

                is SearchState.Error -> {
                    renderImage(true, R.drawable.image_yorik, R.string.not_internet)
                    renderRecyclerSearchList(false)
                    renderLoading(false)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                debounce.debounceFlow.collect { searchRequestText ->
                    searchRequestText?.let {
                        if (searchRequestText.isNotEmpty()) {
                            viewModel.search(searchRequestText)
                        }
                    }
                }
            }
        }
    }

    private fun renderImage(visible: Boolean, @DrawableRes resource: Int? = null, @StringRes message: Int? = null) {
        binding.searchImage.isVisible = visible
        binding.textImage.isVisible = visible
        if (visible) {
            resource?.let {
                binding.searchImage.setImageResource(resource)
            }
            message?.let { binding.textImage.text = getString(it) }
        }
    }

    private fun renderLoading(value: Boolean) {
        binding.progressCircular.isVisible = value
    }

    private fun renderRecyclerSearchList(value: Boolean, data: List<Int>? = null) {
        binding.dataList.isVisible = value
    }

    override fun onDestroy() {
        textWatcher?.let { binding.editSearchText.removeTextChangedListener(it) }
        super.onDestroy()
    }
}
