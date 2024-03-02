package ru.practicum.android.diploma.filter.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentBranchBinding
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.BRANCH_KEY
import ru.practicum.android.diploma.filter.ui.FilterFragment.Companion.FILTER_RECEIVER_KEY

class BranchFragment : Fragment() {

    private var _binding: FragmentBranchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBranchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterToolbarBranch.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val branch = "Результат, который нужно отправить"
        val branchBundle = Bundle().apply {
            putString(BRANCH_KEY, branch)
        }
        setFragmentResult(FILTER_RECEIVER_KEY, branchBundle)
    }
}

