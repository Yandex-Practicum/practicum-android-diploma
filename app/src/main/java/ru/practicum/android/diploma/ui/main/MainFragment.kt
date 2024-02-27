package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.presentation.main.MainAdapter
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel
import ru.practicum.android.diploma.util.extensions.onTextChange
import ru.practicum.android.diploma.util.extensions.onTextChangeDebounce
import ru.practicum.android.diploma.util.extensions.visibleOrGone

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainRecycler()
        clearSearchText()

        binding.searchEditText.onTextChange {
            binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_clear)
            binding.clearButton.isEnabled = true
        }

        binding.searchEditText.onTextChangeDebounce()
            .debounce(2000)
            .onEach { viewModel.onSearch(it?.toString().orEmpty()) }
            .launchIn(lifecycleScope)

        binding.clearButton.setOnClickListener {
            clearSearchText()
        }

        binding.filterImageView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtersFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().collect { state ->
                    if (state.state is SearchState.Content) {
                        adapter.submitList(state.state.vacancy)
                    }

                    binding.progressBar.visibleOrGone(state.state is SearchState.Loading)
                    binding.searchRecyclerView.visibleOrGone(state.state is SearchState.Content || state.state is SearchState.Loading)
                    binding.imageBinoculars.visibleOrGone(state.state == null)
                    binding.placeholderError.visibleOrGone(state.state is SearchState.Empty)
                    binding.placeholderNoConnection.visibleOrGone(state.state is SearchState.Error)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        clearSearchText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMainRecycler() {
        adapter = MainAdapter()
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clearSearchText() {
        binding.searchEditText.setText("")
        binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_search)
        binding.clearButton.isEnabled = false
    }
}
