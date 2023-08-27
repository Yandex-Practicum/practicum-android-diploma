package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.root.DebouncingFragment
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding



class SearchFragment : DebouncingFragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()
    private var adapter: SearchAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated   $viewModel")

        adapter = SearchAdapter().apply {
            onClick = { vacancy ->
                onClickWithDebounce {
                    viewModel.log(thisName, "onClickWithDebounce $vacancy")
                }
            }
        }
    }
}