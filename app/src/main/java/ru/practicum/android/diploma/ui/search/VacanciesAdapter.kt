package ru.practicum.android.diploma.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesAdapter(
    private val onItemClick: (Vacancy) -> Unit
) : ListAdapter<VacanciesAdapter.VacancyItem, RecyclerView.ViewHolder>(VacancyDiffCallback) {

    sealed class VacancyItem {
        data class VacancyData(val vacancy: Vacancy) : VacancyItem()
        object LoadingItem : VacancyItem()
    }

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_LOADING = 1
        private const val LOGO_SIZE = 48 // dp
        private const val CORNER_RADIUS = 12 // dp
    }

    private var isLoading = false
    private var hasMore = true

    fun setLoading(loading: Boolean) {
        if (isLoading != loading) {
            isLoading = loading
            updateList()
        }
    }

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        if (!hasMore && isLoading) {
            setLoading(false)
        }
    }

    private fun updateList() {
        val currentVacancies = currentList.filterIsInstance<VacancyItem.VacancyData>()
        val vacancyList = currentVacancies.map { it.vacancy }
        submitVacanciesInternal(vacancyList)
    }

    // Основной метод для установки вакансий
    fun submitVacancies(vacancies: List<Vacancy>) {
        submitVacanciesInternal(vacancies)
    }

    private fun submitVacanciesInternal(vacancies: List<Vacancy>) {
        val vacancyItems = vacancies.map { VacancyItem.VacancyData(it) }.toMutableList<VacancyItem>()

        // Добавляем loading item если нужно
        if (isLoading && hasMore) {
            vacancyItems.add(VacancyItem.LoadingItem)
        }

        submitList(vacancyItems)
    }

    // Метод для получения только вакансий (без loading item)
    fun getVacancies(): List<Vacancy> {
        return currentList.mapNotNull {
            when (it) {
                is VacancyItem.VacancyData -> it.vacancy
                else -> null
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is VacancyItem.VacancyData -> TYPE_ITEM
            is VacancyItem.LoadingItem -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> VacancyViewHolder(
                ItemVacancyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClick
            )
            else -> LoadingViewHolder(
                ItemVacancyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VacancyViewHolder -> {
                val item = getItem(position)
                if (item is VacancyItem.VacancyData) {
                    holder.bind(item.vacancy)
                }
            }
            is LoadingViewHolder -> holder.bind()
        }
    }

    class VacancyViewHolder(
        private val binding: ItemVacancyBinding,
        private val onItemClick: (Vacancy) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(vacancy: Vacancy) {
            val city = vacancy.area?.name ?: ""
            val titleWithCity = if (city.isNotEmpty()) {
                "${vacancy.title}, $city"
            } else {
                vacancy.title
            }
            binding.vacancyTitle.text = titleWithCity

            val companyName = vacancy.employer?.name
            binding.companyName.text = companyName ?: ""
            binding.companyName.isVisible = !companyName.isNullOrEmpty()

            val salaryText = vacancy.salary?.getFormattedSalary() ?: "Зарплата не указана"
            binding.salaryText.text = salaryText

            val logoUrl = vacancy.employer?.logoUrl
            if (!logoUrl.isNullOrEmpty()) {
                val radiusPx = 12.dpToPx(binding.root.context)

                Glide.with(binding.root)
                    .load(logoUrl)
                    .transform(RoundedCorners(radiusPx))
                    .placeholder(R.drawable.ic_company_placeholder)
                    .error(R.drawable.ic_company_placeholder)
                    .into(binding.companyLogo)

            } else {
                binding.companyLogo.setImageResource(R.drawable.ic_company_placeholder)
            }

            binding.loadingProgress.isVisible = false
            binding.vacancyTitle.isVisible = true
            binding.companyName.isVisible = true
            binding.salaryText.isVisible = true
            binding.companyLogo.isVisible = true

            binding.root.setOnClickListener {
                onItemClick(vacancy)
            }
        }

        // Вспомогательная функция для преобразования dp в pixels
        private fun Int.dpToPx(context: Context): Int {
            return (this * context.resources.displayMetrics.density).toInt()
        }
    }

    class LoadingViewHolder(
        private val binding: ItemVacancyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.loadingProgress.isVisible = true
            binding.vacancyTitle.isVisible = false
            binding.companyName.isVisible = false
            binding.salaryText.isVisible = false
            binding.companyLogo.isVisible = false
        }
    }

    object VacancyDiffCallback : DiffUtil.ItemCallback<VacancyItem>() {
        override fun areItemsTheSame(oldItem: VacancyItem, newItem: VacancyItem): Boolean {
            return when {
                oldItem is VacancyItem.VacancyData && newItem is VacancyItem.VacancyData ->
                    oldItem.vacancy.id == newItem.vacancy.id
                oldItem is VacancyItem.LoadingItem && newItem is VacancyItem.LoadingItem -> true
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: VacancyItem, newItem: VacancyItem): Boolean {
            return when {
                oldItem is VacancyItem.VacancyData && newItem is VacancyItem.VacancyData ->
                    oldItem.vacancy == newItem.vacancy
                oldItem is VacancyItem.LoadingItem && newItem is VacancyItem.LoadingItem -> true
                else -> false
            }
        }
    }
}
