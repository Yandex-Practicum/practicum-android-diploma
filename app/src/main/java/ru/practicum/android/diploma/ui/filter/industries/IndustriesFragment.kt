package ru.practicum.android.diploma.ui.filter.industries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding
import ru.practicum.android.diploma.domain.models.industries.ParentIndustry

class IndustriesFragment : Fragment() {

    private val viewModel by viewModel<IndustriesFragmentViewModel>()

    private var _binding: FragmentIndustriesBinding? = null
    private val binding get() = _binding!!

    private val data = ArrayList<ParentIndustry>()
    private var recyclerView: RecyclerView? = null

    private fun showIndustriesWithoutSelectButton(){
        binding.industriesRecyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.GONE
        binding.getIndustriesErrorFrame.visibility = View.GONE
    }

    private fun showIndustriesWithSelectButton(){
        binding.industriesRecyclerView.visibility = View.VISIBLE
        binding.industriesFilterApply.visibility = View.VISIBLE
        binding.getIndustriesErrorFrame.visibility = View.GONE
    }

    private fun showGetIndustriesError(){
        binding.industriesRecyclerView.visibility = View.GONE
        binding.industriesFilterApply.visibility = View.GONE
        binding.getIndustriesErrorFrame.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndustriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.industriesRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)


        viewModel.getState().observe(viewLifecycleOwner){
            when(it){
                is IndustriesFragmentUpdate.GetIndustriesError -> {
                    showGetIndustriesError()
                }

                is IndustriesFragmentUpdate.IndustriesList -> {
                    Log.d("DATA INCOME", it.industries.size.toString() )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
