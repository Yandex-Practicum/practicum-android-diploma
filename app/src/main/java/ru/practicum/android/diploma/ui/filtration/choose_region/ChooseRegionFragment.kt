package ru.practicum.android.diploma.ui.filtration.choose_region

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.choose_region.view_model.ChooseRegionViewModel

class ChooseRegionFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseRegionFragment()
    }

    private val viewModel: ChooseRegionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_choose_region, container, false)
    }
}
