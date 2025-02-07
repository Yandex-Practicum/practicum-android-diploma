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
    private var selectedIndustry: Industry? = null

//    fun setIndustries(newIndustries: List<Industry>) {
//        selectedIndex = null
//        industries.clear()
//        industries.addAll(newIndustries)
//    }

    fun setIndustries(newIndustries: List<Industry>) {
        // Сохраняем выбранный элемент
        if (selectedIndex != null) {
            selectedIndustry = selectedIndex?.let { industries.getOrNull(it) }
        }

        // Очищаем список и добавляем новые элементы
        industries.clear()
        industries.addAll(newIndustries)

        // Обновляем selectedIndex, если выбранный элемент есть в новом списке
        selectedIndex = selectedIndustry?.let { selected ->
            newIndustries.indexOfFirst { it.id == selected.id }.takeIf { it != -1 }
        }
        selectedIndex?.let {
            industries[it] = industries[it].copy(selected = true)
            notifyItemChanged(it)
        }

        notifyDataSetChanged()
    }

    fun setSelectedIndustry(selectedIndustry: Industry?) {
        selectedIndex = selectedIndustry?.let { industry ->
            industries.indexOfFirst { it.id == industry.id }.takeIf { it != -1 }
        }
        selectedIndex?.let {
            industries[it] = industries[it].copy(selected = true)
            notifyItemChanged(it)
        }
        notifyDataSetChanged()
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
