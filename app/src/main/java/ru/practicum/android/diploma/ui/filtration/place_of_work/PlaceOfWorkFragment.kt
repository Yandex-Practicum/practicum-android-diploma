package ru.practicum.android.diploma.ui.filtration.place_of_work

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.place_of_work.view_model.PlaceOfWorkViewModel

class PlaceOfWorkFragment : Fragment() {

    companion object {
        fun newInstance() = PlaceOfWorkFragment()
    }

    private val viewModel: PlaceOfWorkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_place_of_work, container, false)
    }
}
