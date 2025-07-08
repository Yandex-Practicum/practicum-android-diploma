package ru.practicum.android.diploma.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.AboutFragmentBinding

class AboutFragment : Fragment() {

    private var binding: AboutFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = AboutFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

}
