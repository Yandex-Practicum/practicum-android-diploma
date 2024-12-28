package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industry

class IndustriesAdapter(
    private val onItemClicked: (Industry) -> Unit,
    var selectedPosition: String? = "",
    private var industries: List<Industry> = emptyList(),
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    fun interface Listener {
        fun onClick(industry: Industry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustriesViewHolder(
            IndustryItemBinding.inflate(layoutInflater, parent, false),
            this
        ) {
            onItemClicked(industries[it])
        }
    }

    override fun getItemCount(): Int {
        return industries.size
    }

    override fun onBindViewHolder(holder: IndustriesViewHolder, position: Int) {
        holder.bind(industries[position], position)
    }

    fun updateIndustries(newIndustries: List<Industry>) {
        industries = newIndustries
        notifyDataSetChanged()
    }

    fun updateSelection(model: Industry) {
        selectedPosition = model.id
        notifyDataSetChanged()
    }
}

class IndustriesViewHolder(
    private val binding: IndustryItemBinding,
    private val adapter: IndustriesAdapter,
    private val onItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: Industry, position: Int) {
        binding.radioButton.text = model.name
        binding.radioButton.isChecked = model.id == adapter.selectedPosition
        binding.radioButton.setOnClickListener {
            onItemClicked(position)
            if (model.id != adapter.selectedPosition) {
                adapter.updateSelection(model)
            }
        }
    }
}
