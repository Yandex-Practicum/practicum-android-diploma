package ru.practicum.android.diploma.ui.filter.industry.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.ItemIndustryBinding
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.diffutils.IndustryDiffCallback

class IndustryAdapter(
    private val onItemSelected: (Industry) -> Unit
) : ListAdapter<Industry, IndustryViewHolder>(IndustryDiffCallback()) {

    var selectedIndustryId: String? = null
    private var selectedIndustryName: String? = null
    var industriesFull: List<Industry> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = ItemIndustryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = getItem(position)
        val isSelected = industry.id == selectedIndustryId

        holder.bind(industry, isSelected) { selectedIndustry ->
            if (selectedIndustryId != selectedIndustry.id) {
                selectedIndustryId = selectedIndustry.id
                selectedIndustryName = selectedIndustry.name
                notifyDataSetChanged()
                onItemSelected(selectedIndustry)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectIndustry(industry: Industry) {
        selectedIndustryId = industry.id
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String, onFilterCompleted: (Int) -> Unit) {
        val filteredList = if (query.isEmpty()) {
            industriesFull.sortedBy { it.name }
        } else {
            industriesFull.filter { it.name?.contains(query, ignoreCase = true) == true }
                .sortedBy { it.name }
        }
        submitList(filteredList)
        notifyDataSetChanged()
        onFilterCompleted(filteredList.size)
    }

}
