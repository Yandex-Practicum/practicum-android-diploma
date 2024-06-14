package ru.practicum.android.diploma.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.VacancyViewBinding
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter(
    val vacancyList: ArrayList<Vacancy>,
    private var onClickListener: OnClickListener,
) : RecyclerView.Adapter<VacancyViewHolder>() {
    val currencyDictionary: MutableMap<String, Currency> = mutableMapOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return VacancyViewHolder(VacancyViewBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int = vacancyList.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val currencySymbol = currencyDictionary[vacancyList[position].salary?.currency]?.abbr ?: ""
        holder.bind(vacancyList[position], onClickListener, currencySymbol)
    }

    interface OnClickListener {
        fun onClick(vacancy: Vacancy)
    }
}
