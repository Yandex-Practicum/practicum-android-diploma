package ru.practicum.android.diploma.ui.filter.industry

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustryDiffCallback : DiffUtil.ItemCallback<IndustryListItem>() {
    override fun areItemsTheSame(
        oldItem: IndustryListItem,
        newItem: IndustryListItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: IndustryListItem,
        newItem: IndustryListItem
    ): Boolean {
        return oldItem == newItem
    }

}

class IndustryAdapter(
    private val clickListener: IndustryClickListener
) : ListAdapter<IndustryListItem, IndustryAdapter.IndustryViewHolder>(IndustryDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IndustryViewHolder {
        val binding = IndustryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IndustryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        var item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            clickListener.onIndustryClick(item)
        }
    }

    inner class IndustryViewHolder(private val binding: IndustryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IndustryListItem) {
            binding.industryItem.text = item.name
            binding.radioButton.setImageResource(
                if (item.isSelected) {
                    ru.practicum.android.diploma.R.drawable.radio_button_on__24px
                } else {
                    ru.practicum.android.diploma.R.drawable.radio_button_off__24px
                }
            )
        }
    }
}
