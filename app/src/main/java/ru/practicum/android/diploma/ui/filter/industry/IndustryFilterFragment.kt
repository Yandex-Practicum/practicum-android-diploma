package ru.practicum.android.diploma.ui.filter.industry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentIndustryFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress
import ru.practicum.android.diploma.R

class IndustryFilterFragment : BindingFragment<FragmentIndustryFilterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndustryFilterBinding {
        return FragmentIndustryFilterBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //подсказка Введите отрасль
        binding.industrySearch.searchEditText.hint = getString(R.string.enter_industry)

        // системная кн назад
        handleBackPress()
    }
}
