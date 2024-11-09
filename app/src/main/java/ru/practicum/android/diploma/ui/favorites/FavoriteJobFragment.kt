package ru.practicum.android.diploma.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.databinding.FragmentFavoriteJobBinding

class FavoriteJobFragment : Fragment() {
    private var binding: FragmentFavoriteJobBinding? = null
    private val viewModel: FavoriteJobViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteJobBinding.inflate(layoutInflater)
        return binding?.root
    }
}
