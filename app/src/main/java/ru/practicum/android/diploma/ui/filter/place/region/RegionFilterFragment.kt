package ru.practicum.android.diploma.ui.filter.place.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.ui.root.BindingFragment
import ru.practicum.android.diploma.util.handleBackPress

class RegionFilterFragment : BindingFragment<FragmentRegionFilterBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegionFilterBinding {
        return FragmentRegionFilterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // подсказка Введите регион
        binding.regionSearch.searchEditText.hint = getString(R.string.enter_region)

        // системная кн назад
        handleBackPress()
    }
}
