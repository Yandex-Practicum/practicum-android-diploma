package ru.practicum.android.diploma.filters.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSettingFiltersBinding
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.util.BindingFragment

class FragmentSettingFilters:BindingFragment<FragmentSettingFiltersBinding>() {

    val viewModel by viewModel<FiltersViewModel>()
    var bundle:Bundle? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingFiltersBinding {
        return FragmentSettingFiltersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchToPlaceOfWorkScreen()
        switchToIndustriesScreen()
    }
    fun switchToPlaceOfWorkScreen(){
        binding.placeOfWorkButton.setOnClickListener{
            Log.d("myLog", "Click")
            bundle = bundleOf(SCREEN to PLACE_OF_WORK)
            findNavController().navigate(R.id.action_settingFilters_to_fragmentChooseFilter, bundle)
        }
    }
    fun switchToIndustriesScreen(){
        binding.industryButton.setOnClickListener{
            bundle = bundleOf(SCREEN to INDUSTRIES)
            findNavController().navigate(R.id.action_settingFilters_to_fragmentChooseFilter, bundle)
        }
    }
    fun back(){
        binding.arrowback
    }
    companion object{
        const val SCREEN = "screen"
        const val PLACE_OF_WORK = 1
        const val INDUSTRIES = 2
    }
}