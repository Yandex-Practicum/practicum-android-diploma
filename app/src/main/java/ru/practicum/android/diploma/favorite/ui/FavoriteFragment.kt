package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.ui.FavoritesScreenState.Empty
import ru.practicum.android.diploma.favorite.ui.FavoritesScreenState.Content
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.favorite.domain.FavoriteViewModel

import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.search.ui.SearchAdapter
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    @Inject @JvmField var vacancyAdapter: SearchAdapter? = null
    private val viewModel: FavoriteViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentFavoriteBinding>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as RootActivity).component.inject(this)
        viewModel.log(thisName, "onViewCreated()")

        binding.recycler.adapter = vacancyAdapter
        initListeners()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is Empty -> { showPlaceholder() }
                    is Content -> { showContent(state.list) }
                }
            }
        }
    }

    private fun showContent(list: List<Vacancy>) {
        binding.placeHolder.visibility = View.INVISIBLE
        binding.recycler.visibility = View.VISIBLE
        vacancyAdapter?.list = list
        vacancyAdapter?.notifyDataSetChanged()
    }

    private fun showPlaceholder() {
        binding.placeHolder.visibility = View.VISIBLE
        binding.recycler.visibility = View.INVISIBLE
    }

    private fun initListeners() {
        vacancyAdapter?.onClick = { vacancy ->
            findNavController().navigate(
                resId = R.id.action_favoriteFragment_to_detailsFragment,
                //args = bundleOf("KEY_DETAILS" to vacancy)
            )
        }
        vacancyAdapter?.onLongClick = { viewModel.removeVacancy(0) }
    }

    override fun onDestroyView() {
        viewModel.log(thisName, "onDestroyView()")
        super.onDestroyView()
        vacancyAdapter = null
    }
}