package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.presentation.main.PagingMainAdapter
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel
import ru.practicum.android.diploma.util.extensions.onTextChange
import ru.practicum.android.diploma.util.extensions.onTextChangeDebounce
import ru.practicum.android.diploma.util.extensions.visibleOrGone

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PagingMainAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMainRecycler()

        binding.searchEditText.onTextChange {
            binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_clear)
            binding.clearButton.isEnabled = true
        }

        binding.searchEditText.onTextChangeDebounce()
            .debounce(2000)
            .onEach {
                val query = it?.toString().orEmpty()
                viewModel.onSearch(query)
            }
            .launchIn(lifecycleScope)

        binding.clearButton.setOnClickListener {
            clearSearchText()
            viewModel.onSearch("")
        }

        binding.filterImageView.setOnClickListener {
            findNavController()
                .navigate(R.id.action_mainFragment_to_filtersFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().collect { state ->
                    binding.progressBar.visibleOrGone(state.state is SearchState.Loading)
                    binding.searchRecyclerView.visibleOrGone(state.state is SearchState.Content || state.state is SearchState.Loading)
                    binding.imageBinoculars.visibleOrGone(state.state == null)
                    binding.placeholderError.visibleOrGone(state.state is SearchState.Empty)
                    binding.placeholderNoConnection.visibleOrGone(state.state is SearchState.Error)

                    binding.tvRvHeader.visibleOrGone(state.foundVacancies != null && state.state !is SearchState.Error)
                    state.foundVacancies?.let {
                        binding.tvRvHeader.text = it
                    }

                    if (state.state is SearchState.Content && searchJob == null) {
                        searchJob = launch {
                            viewModel.flow.collect {
                                adapter.submitData(it)
                            }
                        }
                    } else {
                        searchJob?.cancel()
                        searchJob = null
                    }

                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flow.collect{
                    adapter.submitData(it)
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupMainRecycler() {
        adapter = PagingMainAdapter {
            findNavController().navigate(R.id.action_mainFragment_to_vacanciesFragment, bundleOf("vacancy_id" to it))
        }
        binding.searchRecyclerView.adapter = adapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clearSearchText() {
        binding.searchEditText.setText("")
        binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_search)
        binding.clearButton.isEnabled = false
        binding.tvRvHeader.visibility = View.GONE
    }
}
