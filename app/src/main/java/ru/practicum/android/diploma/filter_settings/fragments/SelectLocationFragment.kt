package ru.practicum.android.diploma.filter_settings.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter_settings.view_models.SelectLocationViewModel

class SelectLocationFragment : Fragment() {

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private val viewModel: SelectLocationViewModel by viewModel<SelectLocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_location, container, false)
    }
}
