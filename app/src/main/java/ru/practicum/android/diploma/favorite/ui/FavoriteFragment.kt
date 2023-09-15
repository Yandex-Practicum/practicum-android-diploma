package ru.practicum.android.diploma.favorite.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.favorite.ui.FavoritesScreenState.Content
import ru.practicum.android.diploma.favorite.ui.FavoritesScreenState.Empty
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.MainCompositeAdapter
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.RecyclerViewSwipeCallback
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.VacancyAdapterDelegate
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    
    private val viewModel: FavoriteViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentFavoriteBinding>()
    private var trackDialog: AlertDialog? = null
    
    private val vacancyAdapter by lazy {
        MainCompositeAdapter
            .Builder()
            .add(
                VacancyAdapterDelegate(
                    onClick = { vacancy -> navigateToDetails(vacancy) },
                    onLongClick = { vacancy -> showTrackDialog(vacancy.id) })
            )
            .build()
    }

    private val swipeHandler by lazy {
        RecyclerViewSwipeCallback(context = requireContext(), onDeleteSwipe = { vacancy ->
            viewModel.removeVacancy(vacancy.id)
        })
    }

    private val itemTouchHelper by lazy {
        ItemTouchHelper(swipeHandler)
    }
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        binding.recycler.adapter = vacancyAdapter

        itemTouchHelper.attachToRecyclerView(binding.recycler)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is Empty -> { showPlaceholder() }
                    is Content -> {
                        showContent(state.list)
                    }
                }
            }
        }
    }

    private fun showTrackDialog(vacancy: String) {
        trackDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_vacancy))
            .setNegativeButton(R.string.no) { _, _ -> trackDialog?.dismiss() }
            .setPositiveButton(R.string.yes) { _, _ -> viewModel.removeVacancy(vacancy) }
            .create()
        trackDialog?.setCanceledOnTouchOutside(false)
        trackDialog?.setCancelable(false)
        trackDialog?.show()
    }

    private fun showContent(list: List<Vacancy>) {
        viewModel.log(thisName, "showContent(list: size=${list.size})")
        binding.placeHolder.visibility = View.INVISIBLE
        binding.recycler.visibility = View.VISIBLE
        swipeHandler.list = list
        vacancyAdapter.submitList(list)
    }

    private fun showPlaceholder() {
        viewModel.log(thisName, "showPlaceholder()")
        binding.placeHolder.visibility = View.VISIBLE
        binding.recycler.visibility = View.INVISIBLE
    }

    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(vacancy.id)
        )
    }
}