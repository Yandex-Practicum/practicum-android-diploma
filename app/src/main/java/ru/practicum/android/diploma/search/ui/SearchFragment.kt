package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject
    @JvmField
    var searchAdapter: SearchAdapter? = null
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.log(thisName, "onViewCreated   $viewModel")
        initListeners()

        searchAdapter?.onClick = { vacancy ->
            viewModel.log(thisName, "onClickWithDebounce $vacancy")
        }
    }
    private fun initListeners() {
        binding.filterBtnToolbar.setOnClickListener {
            viewModel.log(thisName, "navigate to filterBaseFragment")
            findNavController().navigate(
                resId = R.id.action_searchFragment_to_filterBaseFragment,
            )
        }
    }

}