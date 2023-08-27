package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.favorite.ui.FavoriteViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding


class SearchFragment : Fragment(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
private val binding by viewBinding<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated   $viewModel" )
    }
}