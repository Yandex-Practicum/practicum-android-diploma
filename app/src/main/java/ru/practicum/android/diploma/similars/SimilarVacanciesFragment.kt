package ru.practicum.android.diploma.similars

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class SimilarVacanciesFragment : Fragment(R.layout.fragment_similars_vacancy)  {

    @Inject @JvmField var vacancyAdapter: SearchAdapter? = null
    private val viewModel: SimilarVacanciesViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSimilarsVacancyBinding>()
    private val args by navArgs<SimilarVacanciesFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        initAdapter()
        initListeners()
        collector()
        viewModel.getSimilarVacancies(args.vacancy.id )
    }

    private fun collector() {
        viewModel.log(thisName, "collector()")
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state -> state.render(binding, vacancyAdapter) }
        }
    }

    private fun initAdapter() {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = vacancyAdapter
    }

    private fun initListeners() {
        vacancyAdapter?.onClick = { vacancy ->
            navigateToDetails(vacancy)
        }
    }

    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            SimilarVacanciesFragmentDirections.actionSimilarsVacancyFragmentToDetailsFragment(vacancy)
        )
    }

}