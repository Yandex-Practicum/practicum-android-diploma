package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.details.ui.DetailsViewModel
import ru.practicum.android.diploma.root.DebouncingFragment
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.Vacancy
import ru.practicum.android.diploma.search.ui.SearchAdapter
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class FavoriteFragment : DebouncingFragment(R.layout.fragment_favorite) {

    //@Inject
    var vacancyAdapter: SearchAdapter? = null
    private val viewModel: FavoriteViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentFavoriteBinding>()
    private val detailsViewModel : DetailsViewModel by viewModels { (activity as RootActivity).viewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as RootActivity).component.inject(this)
        viewModel.log(thisName, "onViewCreated()")

        binding.recycler.adapter = vacancyAdapter
//        detailsViewModel.addToFavorites(Vacancy(11L,"url","android-developer", "yandex","3000$",123L))
//        detailsViewModel.addToFavorites(Vacancy(12L,"url","java-developer", "google","3000$",123L))
//        detailsViewModel.addToFavorites(Vacancy(14L,"url","python-developer", "tesla","3000$",123L))
//        detailsViewModel.addToFavorites(Vacancy(15L,"url","kotlin-developer", "tinkoff","3000$",123L))
//        detailsViewModel.deleteVacancy(14L)

        detailsViewModel.favs
            .onEach {
                viewModel.log(thisName,"______")
                it.forEach {
                viewModel.log(thisName, "vac = ${it.title}")
            } }
            .launchIn(viewLifecycleOwner.lifecycleScope)


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