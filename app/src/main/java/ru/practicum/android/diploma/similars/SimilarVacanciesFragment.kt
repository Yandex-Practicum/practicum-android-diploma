package ru.practicum.android.diploma.similars

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.details.ui.DetailsFragment
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter
import ru.practicum.android.diploma.similars.SimilarVacanciesState.Empty.showContent
import ru.practicum.android.diploma.similars.SimilarVacanciesState.Empty.showPlaceholder
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class SimilarVacanciesFragment : Fragment(R.layout.fragment_similars_vacancy)  {

    @Inject @JvmField var vacancyAdapter: SearchAdapter? = null
    private val viewModel: SimilarVacanciesViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSimilarsVacancyBinding>()
    private var vacancy: Vacancy? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated()")
        vacancy = requireArguments().getString(DetailsFragment.VACANCY_KEY)?.let { Json.decodeFromString<Vacancy>(it) }
        initAdapter()
        initListeners()
        collector()
        viewModel.getSimilarVacancies(vacancy?.id ?: 0)
    }

    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is SimilarVacanciesState.Empty -> { showPlaceholder(binding, viewModel) }
                    is SimilarVacanciesState.Content -> {
                        showContent(binding, viewModel)
                        vacancyAdapter?.list = state.list
                        vacancyAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        viewModel.log(thisName, "initAdapter()")
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = vacancyAdapter
    }

    private fun initListeners() {
        viewModel.log(thisName, "initListeners()")
        vacancyAdapter?.onClick = { vacancy ->
            findNavController().navigate(
                resId = R.id.action_similarsVacancyFragment_to_detailsFragment,
                args = bundleOf(DetailsFragment.VACANCY_KEY to Json.encodeToString(vacancy))
            )
        }
    }

}