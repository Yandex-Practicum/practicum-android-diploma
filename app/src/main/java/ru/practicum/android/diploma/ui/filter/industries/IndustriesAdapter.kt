package ru.practicum.android.diploma.ui.filter.industries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinApplication.Companion.init
import org.koin.core.qualifier.named
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.databinding.IndustryItemBinding
import ru.practicum.android.diploma.domain.models.Industries

class IndustriesAdapter(
    private val onItemClicked: (Industries) -> Unit,
    private var industries: List<Industries> = emptyList()
) : RecyclerView.Adapter<IndustriesViewHolder>() {

    var selectedPosition: Int = -1

    fun getIndustries(): List<Industries> = industries
    fun interface Listener {
        fun onClick(industry: Industries)
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
        // holder.itemView.setOnClickListener { listener.onClick(industries[position]) }
    }

    fun updateIndustries(newIndustries: List<Industries>) {
        industries = newIndustries
        notifyDataSetChanged()
    }

    fun updateSelection(position: Int) {
        val previousSelectedPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousSelectedPosition)
        notifyItemChanged(selectedPosition)
    }
}

class IndustriesViewHolder(
    private val binding: IndustryItemBinding,
    private val adapter: IndustriesAdapter,
    onItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: Industries, position: Int) {
        binding.radioButton.text = model.name

        binding.radioButton.isChecked = position == adapter.selectedPosition
        binding.radioButton.setOnClickListener {
            if (position != adapter.selectedPosition) {
                adapter.updateSelection(position)
            }
        }

    }
}
