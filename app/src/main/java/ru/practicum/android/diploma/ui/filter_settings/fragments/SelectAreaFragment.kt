package ru.practicum.android.diploma.ui.filter_settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.filter_settings.view_models.SelectAreaViewModel

class SelectAreaFragment : Fragment() {

    companion object {
        fun newInstance() = SelectAreaFragment()
    }

    private val viewModel: SelectAreaViewModel by viewModel<SelectAreaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_area, container, false)
    }
}
