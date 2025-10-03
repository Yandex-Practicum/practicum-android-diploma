package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.ui.models.SearchState
import ru.practicum.android.diploma.ui.view_models.MainViewModel
import ru.practicum.android.diploma.util.Debounce
import ru.practicum.android.diploma.util.SearchDebounce

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private var textWatcher: TextWatcher? = null
    private val viewModel:MainViewModel by viewModel()
    private val debounce = SearchDebounce(scope = lifecycleScope)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        val trailingButton = binding.trailingButton
        trailingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtrationFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textWatcher = binding.editSearchText.doOnTextChanged { text, _, _, _ ->
            binding.clearTheField.isVisible = !text.isNullOrEmpty()

            if(text.isNullOrEmpty())
                viewModel.setIdleState()
            else
                debounce.execute(text.toString())
        }
        textWatcher.let { binding.editSearchText.addTextChangedListener(it) }
        setupClickListeners()
        setupObservers()
    }

    private fun setupClickListeners(){
        binding.clearTheField.setOnClickListener {
            binding.editSearchText.setText("")
        }
    }

    private fun setupObservers(){
        viewModel.observeSearchState.observe(viewLifecycleOwner){ state ->
            Log.v("my",state.toString())
            when(state){
                is SearchState.Idle ->{renderImage(true, R.drawable.image_search_default)}
                is SearchState.Nothing -> { renderImage(false) }
                is SearchState.Loading ->{renderImage(false)}
                is SearchState.Complete ->{}
                is SearchState.Error ->{}
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                debounce.debounceFlow.collect { searchRequestText ->
                    searchRequestText?.let{
                        viewModel.search(searchRequestText)
                    }
                }
            }
        }
    }

    private fun renderImage(visible:Boolean, @DrawableRes resource:Int? = null){
        binding.searchImage.isVisible = visible
        binding.textImage.isVisible = visible
        if(visible){
            resource?.let {
                binding.searchImage.setImageResource(resource)
            }
        }
    }

    override fun onDestroy() {
        textWatcher?.let { binding.editSearchText.removeTextChangedListener(it) }
        super.onDestroy()
    }
}
