package ru.practicum.android.diploma.ui.job_search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.job_search.view_model.JobSearchViewModel

class JobSearchFragment : Fragment() {

    companion object {
        fun newInstance() = JobSearchFragment()
    }

    private val viewModel: JobSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_job_search, container, false)
    }
}
