package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.databinding.IndustryItemBinding

class IndustriesAdapter(
    private val onItemClicked: (IndustriesFullDto) -> Unit,
    private var industries: List<IndustriesFullDto> = emptyList()
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    fun getIndustries(): List<IndustriesFullDto> = industries
    fun interface Listener {
        fun onClick(industry: IndustriesFullDto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(IndustryItemBinding.inflate(layoutInflater, parent, false)) {
            onItemClicked(industries[it])
        }
    }

    override fun getItemCount(): Int {
        return industries.size
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        holder.bind(industries[position])
        // holder.itemView.setOnClickListener { listener.onClick(industries[position]) }
    }

    fun updateIndustries(newIndustries: List<IndustriesFullDto>) {
        industries = newIndustries
        notifyDataSetChanged()
    }
}

class IndustriesViewHolder(
    private val binding: IndustryItemBinding,
    onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: IndustriesFullDto) {
        binding.checkBox.text = model.name
    }
}
