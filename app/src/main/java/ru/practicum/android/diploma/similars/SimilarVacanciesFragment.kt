package ru.practicum.android.diploma.similars

import android.content.Context
import android.os.Bundle
import android.util.Log
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

    /** Функция для сбора данных из viewModel */
    private fun collector() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                Log.e("SimilarVacanciesState", "state = $state")
                when (state) {
                    is SimilarVacanciesState.Empty -> { showPlaceholder() }
                    is SimilarVacanciesState.Content -> { showContent(state.list) }
                }
            }
        }
    }

    /** Инициализируем адаптер и передаём данные */
    private fun initAdapter() {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = vacancyAdapter
    }

    /** Функция для инициализации слушателей */
    private fun initListeners() {
        vacancyAdapter?.onClick = { vacancy ->
            findNavController().navigate(
                resId = R.id.action_similarsVacancyFragment_to_detailsFragment,
                args = bundleOf(DetailsFragment.VACANCY_KEY to Json.encodeToString(vacancy))
            )
        }
    }

    /** Функция для отображения(отрисовки) списка похожих вакансий */
    private fun showContent(list: List<Vacancy>) {
        viewModel.log(thisName, "showContent(list: size=${list.size})")
        binding.placeHolder.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        vacancyAdapter?.list = list
        vacancyAdapter?.notifyDataSetChanged()
    }

    /** Функция для отображения(отрисовки) плейсхолдера */
    private fun showPlaceholder() {
        viewModel.log(thisName, "showPlaceholder()")
        binding.placeHolder.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }

    override fun onDestroyView() {
        viewModel.log(thisName, "onDestroyView()")
        super.onDestroyView()
        vacancyAdapter = null
    }
}