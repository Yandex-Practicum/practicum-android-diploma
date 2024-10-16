package ru.practicum.android.diploma.filters.areas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.RegionSelectFragmentBinding
import ru.practicum.android.diploma.filters.areas.presentation.RegionSelectViewModel

class RegionSelectFragment : Fragment() {
    private var _binding: RegionSelectFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter: RegionSelectRecyclerViewAdapter? = null
    private val viewModel by viewModel<RegionSelectViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegionSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val regionSelectAdapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = regionSelectAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
