package ru.practicum.android.diploma.ui.filter_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.ui.fragment.BindingFragment

class FilterSettingsFragment : BindingFragment<FragmentFilterSettingsBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterSettingsBinding {
        return FragmentFilterSettingsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
