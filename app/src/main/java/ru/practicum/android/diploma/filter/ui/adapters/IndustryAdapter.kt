package ru.practicum.android.diploma.filter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemFilterIndustryBinding
import ru.practicum.android.diploma.filter.domain.model.Industry

class IndustryAdapter(
    private val listener: IndustryClickListener,
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private val industries = ArrayList<Industry>()
    private var selectedIndex: Int? = null

    fun setIndustries(newIndustries: List<Industry>) {
        selectedIndex = null
        industries.clear()
        industries.addAll(newIndustries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IndustryViewHolder(
            ItemFilterIndustryBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun getItemCount(): Int = industries.size

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = industries[position]

        holder.bind(item)
        holder.binding.rbIndustryButton.setOnClickListener {
            manageListRadioButtons(position)
            listener.onIndustryClick(item)
        }

        holder.binding.tvIndustryValue.setOnClickListener {
            manageListRadioButtons(position)
            listener.onIndustryClick(item)
        }

        if (item.selected) {
            selectedIndex = position.toInt()
        }
    }

    private fun manageListRadioButtons(position: Int) {
        selectedIndex?.let {
            industries[it] = industries[it].copy(selected = false)
            notifyItemChanged(it)
        }
        industries[position] = industries[position].copy(selected = true)
        notifyItemChanged(position)
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(item: Industry)
    }
}
