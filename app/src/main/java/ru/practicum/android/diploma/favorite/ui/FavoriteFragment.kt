package ru.practicum.android.diploma.favorite.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.LoggerImpl
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.di.ViewModelFactory
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val logger = LoggerImpl()
    private val binding by viewBinding<FragmentFavoriteBinding>()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }
//    private val component by lazy {
//        (requireActivity().application as App).component
//            .activityComponentFactory()
//            .create()
//    }

//    @Inject
//    lateinit var logger: Logger



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //component.inject(this)
        super.onViewCreated(view, savedInstanceState)
        logger.log(thisName, "onViewCreated()")
    }

}