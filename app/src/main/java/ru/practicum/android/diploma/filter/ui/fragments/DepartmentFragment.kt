package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.fragments.painters.IndustryPainter
import ru.practicum.android.diploma.filter.ui.view_models.DepartmentViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName

class DepartmentFragment : ChooseFragment() {
    override val fragment = DEPARTMENT
    override val viewModel: DepartmentViewModel by viewModels { (activity as RootActivity).viewModelFactory }


    @Suppress("UNCHECKED_CAST")
    override fun renderContent(list: List<Any?>) {
        super.renderContent(list)
        filterAdapter.industryList = list as List<Industry>
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
        viewModel.log(thisName, "renderContent: list.size = ${list.size})")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val painter = IndustryPainter(binding)

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log(thisName, "uiState.collect { state -> ${state.thisName}")
                when (state) {
                    is UiState.Loading -> painter.showLoadingScreen()
                    is UiState.Content -> renderContent(state.list)
                    is UiState.NoData  -> painter.showNoData(state.message)
                    is UiState.Offline -> painter.showOffline(state.message)
                    is UiState.Error   -> painter.showError(state.message)
                }
            }
        }


    }

    override fun initListeners() {
        super.initListeners()

    }

    companion object {
        const val DEPARTMENT = "DepartmentFragment"
    }
}