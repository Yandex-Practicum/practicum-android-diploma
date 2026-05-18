package ru.practicum.android.diploma.ui.filtration.industry_selection

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.industry_selection.view_model.IndustrySelectionViewModel

class IndustrySelectionFragment : Fragment() {

    companion object {
        fun newInstance() = IndustrySelectionFragment()
    }

    private val viewModel: IndustrySelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_industry_selection, container, false)
    }
}
