package ru.practicum.android.diploma.ui.favourites.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding

//    private val viewModel: FavouritesViewModel by viewModel()

    private lateinit var templateTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        templateTextView = binding.templateText
    }
}
