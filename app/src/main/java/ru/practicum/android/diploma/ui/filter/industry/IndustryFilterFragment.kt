package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.ui.filter.FilterViewModel
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class IndustryFilterFragment : BindingFragment<FragmentIndustryFilterBinding>() {

    private val viewModel: FilterViewModel by viewModel()
    private lateinit var industryAdapter: IndustryAdapter

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // подсказка Введите отрасль
        binding.industrySearch.searchEditText.hint = getString(R.string.enter_industry)

        industryAdapter = IndustryAdapter(object : IndustryClickListener {
            override fun onIndustryClick(selectedIndustry: IndustryListItem) {
                viewModel.selectIndustry(selectedIndustry.id)
            }
        })

        binding.industryRecyclerView.adapter = industryAdapter

        viewModel.industryState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustryState.CONTENT -> {
                    // Отображаем данные в адаптере
                    industryAdapter.submitList(state.industryListItems)
                }
                is IndustryState.ERROR -> {
                    // Показать плейсхолдер об ошибке

                }
                is IndustryState.EMPTY -> {
                    // Показать плейсхолдер, что данных нет
                }
            }
        }

        // Получаем список отраслей
        viewModel.getIndustries()
        // системная кн назад
        handleBackPress()
    }
}
