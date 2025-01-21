package ru.practicum.android.diploma.presentation.ui.filter.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R

class SelectWorkplaceFragment : Fragment() {

    companion object {
        fun newInstance() = SelectWorkplaceFragment()
    }

    private val viewModel: SelectWorkplaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_workplace, container, false)
    }
}
