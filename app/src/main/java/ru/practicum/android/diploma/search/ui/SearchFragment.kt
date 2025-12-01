package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.presentation.SearchScreenState
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.search.presentation.UiError

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var textWatcher: TextWatcher? = null
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchStatusLiveData.observe(viewLifecycleOwner) {
            render(it)
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {
                    viewModel.searchDebounce(s.toString())
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentText = s?.toString() ?: ""

                viewModel.searchDebounce(
                    changedText = currentText
                )
                updateClearButtonVisibility(currentText)
            }
        }

        textWatcher?.let { binding.searchField.addTextChangedListener(it) }

        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.actionFilter -> {
                    findNavController().navigate(
                        R.id.action_searchFragment_to_filtersFragment,
                        Bundle.EMPTY
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun hideAllView() {
        binding.apply {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE
            searchInfo.visibility = View.GONE
            searchScreenCover.visibility = View.GONE
            errorPlaceholder.visibility = View.GONE
        }
    }

    private fun render(state: SearchScreenState) {
        hideAllView()

        when (state) {
            is SearchScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is SearchScreenState.ShowContent -> {
                binding.recyclerView.visibility = View.VISIBLE
//                (recyclerView.adapter as? VacancyAdapter)?.submitList(state.tracks)
            }
            is SearchScreenState.Default -> {
                binding.searchScreenCover.visibility = View.VISIBLE
            }
            is SearchScreenState.Error -> {
                binding.apply {
                    errorPlaceholder.visibility = View.VISIBLE
                    errorPlaceholder.text = when (state.error) {
                        UiError.NoInternet -> getString(R.string.error_no_internet)
                        UiError.ServerError -> getString(R.string.error_server)
                        UiError.NothingFound -> getString(R.string.error_nothing_found)
                        is UiError.Unknown -> getString(R.string.error_server)
                    }
                    val drawable = when (state.error) {
                        UiError.NoInternet -> R.drawable.img_error_connection
                        UiError.ServerError -> R.drawable.img_error_connection
                        UiError.NothingFound -> R.drawable.img_error_nothing_found
                        is UiError.Unknown -> R.drawable.img_error_connection
                    }

                    errorPlaceholder.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        drawable,
                        0,
                        0
                    )

                    if (state.error == UiError.NothingFound) {
                        searchInfo.visibility = View.VISIBLE
                        searchInfo.text = getString(R.string.empty_vacancy)
                    }
                }
            }
        }
    }

    private fun updateClearButtonVisibility(text: String) {
        val shouldShowClearButton = text.isNotEmpty()
        if (shouldShowClearButton) {
            binding.searchIcon.visibility = View.GONE
            binding.clearIcon.visibility = View.VISIBLE
        } else {
            binding.searchIcon.visibility = View.VISIBLE
            binding.clearIcon.visibility = View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher.let { binding.searchField.removeTextChangedListener(it) }
        _binding = null
    }
}
