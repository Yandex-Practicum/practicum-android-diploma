package ru.practicum.android.diploma.features.search.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.features.search.presentation.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}