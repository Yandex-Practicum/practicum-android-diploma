package ru.practicum.android.diploma.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.presentation.models.VacancyUi

class SearchItemAdapter(
    private val onClick: (vacancyId: String) -> Unit
) : Adapter<SearchItemAdapter.SearchItemViewHolder>() {
    private var vacancies = listOf<VacancyUi>()

    fun update(newVacancies: List<VacancyUi>) {
        vacancies = newVacancies
        notifyDataSetChanged()
    }

    class SearchItemViewHolder(rootView: View) : ViewHolder(rootView) {
        private val name: TextView
        private val employerName: TextView
        private val salary: TextView
        private val logo: ImageView
        private val radiusInPx =
            rootView.context.resources.getDimension(R.dimen.corner_radius_search_item_img).toInt()

        init {
            name = rootView.findViewById(R.id.tvHeader)
            employerName = rootView.findViewById(R.id.tvDescription)
            salary = rootView.findViewById(R.id.tvPrice)
            logo = rootView.findViewById(R.id.ivVacancy)
        }

        fun bind(vacancy: VacancyUi) {
            name.text = vacancy.name
            employerName.text = vacancy.employerName
            salary.text = vacancy.salary

            Glide.with(logo).load(vacancy.logoUrl).centerCrop().transform(
                RoundedCorners(radiusInPx)
            )
                .placeholder(R.drawable.ic_employer_placeholder).into(logo)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return SearchItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(vacancies[position])
        holder.itemView.setOnClickListener {
            onClick(vacancies[position].id)
        }
    }

    override fun getItemCount() = vacancies.size

}
