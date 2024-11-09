package ru.practicum.android.diploma.ui.favorite_job

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding
import ru.practicum.android.diploma.databinding.FragmentFavoriteJobBinding

class FavoriteJobFragment : Fragment() {
    private var binding: FragmentFavoriteJobBinding? = null

    private val viewModel: FavoriteJobViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
binding = FragmentFavoriteJobBinding.inflate(layoutInflater)
        return binding?.root
    }
}
