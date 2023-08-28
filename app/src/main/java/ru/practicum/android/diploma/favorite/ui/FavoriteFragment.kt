package ru.practicum.android.diploma.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.root.DebouncingFragment
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.ui.SearchAdapter
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class FavoriteFragment : DebouncingFragment(R.layout.fragment_favorite) {

    private var vacancyAdapter: SearchAdapter? = null
    private val viewModel: FavoriteViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentFavoriteBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        binding.recycler.adapter = vacancyAdapter
        initListeners()
    }

    private fun initListeners() {
        viewModel.log(thisName, "initListeners()")
        vacancyAdapter?.onClick = { vacancy ->
            findNavController().navigate(
                resId = R.id.action_favoriteFragment_to_detailsFragment,
                args = bundleOf("KEY_DETAILS" to vacancy),
            ) }
        vacancyAdapter?.onLongClick = { viewModel.removeVacancy() }
    }

    override fun onDestroyView() {
        viewModel.log(thisName, "onDestroyView()")
        super.onDestroyView()
        vacancyAdapter = null
    }
}