package ru.practicum.android.diploma.filter_settings.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter_settings.view_models.FilterParametersViewModel

class FilterParametersFragment : Fragment() {

    companion object {
        fun newInstance() = FilterParametersFragment()
    }

    private val viewModel: FilterParametersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_filter_parameters, container, false)
    }
}
