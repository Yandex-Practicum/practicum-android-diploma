package ru.practicum.android.diploma.search.presenter.filter.ui.fields

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.CardFieldsInfoBinding
import ru.practicum.android.diploma.search.domain.model.Industry

class IndustriesAdapter(
    private val onIndustryClick: (Industry) -> Unit
) : ListAdapter<Industry, IndustriesAdapter.IndustryViewHolder>(IndustryDiffCallback()) {

    var selectedIndustry: Industry? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = CardFieldsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val industry = getItem(position)
        holder.bind(industry, industry.id == selectedIndustry?.id)
    }

    fun updateSelection(newSelectedIndustry: Industry?) {
        val oldSelectedPosition = currentList.indexOf(selectedIndustry)
        selectedIndustry = newSelectedIndustry
        val newSelectedPosition = currentList.indexOf(selectedIndustry)

        if (oldSelectedPosition != -1) {
            notifyItemChanged(oldSelectedPosition)
        }
        if (newSelectedPosition != -1) {
            notifyItemChanged(newSelectedPosition)
        }
    }

    inner class IndustryViewHolder(private val binding: CardFieldsInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onIndustryClick(getItem(adapterPosition))
                }
            }
        }

        fun bind(industry: Industry, isSelected: Boolean) {
            binding.radioButton.text = industry.name
            binding.radioButton.isChecked = isSelected
        }
    }

    class IndustryDiffCallback : DiffUtil.ItemCallback<Industry>() {
        override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean = oldItem == newItem
    }
}
