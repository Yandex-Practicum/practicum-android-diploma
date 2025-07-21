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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val binding = CardFieldsInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding, onIndustryClick)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IndustryViewHolder(
        private val binding: CardFieldsInfoBinding,
        private val onIndustryClick: (Industry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(industry: Industry) {
            binding.radioButton.text = industry.name
            binding.radioButton.isChecked = false

            itemView.setOnClickListener {
                binding.radioButton.isChecked = true
                onIndustryClick(industry)
            }
        }
    }

    class IndustryDiffCallback : DiffUtil.ItemCallback<Industry>() {
        override fun areItemsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Industry, newItem: Industry): Boolean {
            return oldItem == newItem
        }
    }
}
