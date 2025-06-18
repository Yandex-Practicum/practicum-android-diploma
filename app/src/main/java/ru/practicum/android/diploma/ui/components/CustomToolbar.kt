package ru.practicum.android.diploma.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.CustomToolbarBinding

/**
 * CustomToolbar представляет собой расширение,которое предоставляет
 *  дополнительные возможности для настройки внешнего вида и поведения toolBar
 *
 * @annotation JvmOverloads генерация перегруженных конструкторов для Java
 * @param context контекст приложения
 * @param attrs набор атрибутов, определённых в XML-макете
 * @param defStyleAttr стиль по умолчанию
 */

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // флаг активн/не активн кн фильтрации
    private var isFilterActive = false

    private val binding: CustomToolbarBinding = CustomToolbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setToolbarTitle(title: String) {
        binding.headerText.text = title
    }

    // Настройка видимости элементов для экрана фильтрации
    fun setupToolbarForFilterScreen() {
        with(binding) {
            backBtn.isVisible = true
            headerText.isVisible = true
            shareBtn.isVisible = false
            favoriteBtn.isVisible = false
            settingsFilterBtn.isVisible = false
        }
    }

    // для экрана детали вакансии
    fun setupToolbarForVacancyDetailScreen() {
        with(binding) {
            backBtn.isVisible = true
            headerText.isVisible = true
            shareBtn.isVisible = true
            favoriteBtn.isVisible = true
            settingsFilterBtn.isVisible = false
        }
    }

    // для экрана поиск вакансий
    fun setupToolbarForSearchScreen() {
        with(binding) {
            backBtn.isVisible = false
            headerText.isVisible = true
            shareBtn.isVisible = false
            favoriteBtn.isVisible = false
            settingsFilterBtn.isVisible = true
        }
    }

    // обработка кнопки назад
    fun setupToolbarBackButton(fragment: Fragment) {
        binding.backBtn.setOnClickListener {
            fragment.findNavController().popBackStack()
        }
    }

    fun setupToolbarBackButtonCustom(listener: () -> Unit) {
        binding.backBtn.setOnClickListener { listener() }
    }

    // обработка кнопки поделиться
    fun setOnToolbarShareClickListener(listener: () -> Unit) {
        binding.shareBtn.setOnClickListener { listener() }
    }

    // Обработка кнопки Избранное
    fun setOnToolbarFavoriteClickListener(listener: () -> Unit) {
        binding.favoriteBtn.setOnClickListener { listener() }
    }

    // Обработка кнопки настройки фильтрации
    fun setOnToolbarFilterClickListener(listener: () -> Unit) {
        binding.settingsFilterBtn.setOnClickListener { listener() }
    }

    // установка флага при завершении фильтрации
    fun setFilterState(active: Boolean) {
        isFilterActive = active
        updateFilterIcon()
    }

    // смена иконки кн фильтрации
    private fun updateFilterIcon() {
        binding.settingsFilterBtn.setImageResource(
            if (isFilterActive) {
                R.drawable.filter_on__24px
            } else {
                R.drawable.filter_off__24px
            }
        )
    }

    fun setFavoriteIcon(resource: Int) {
        binding.favoriteBtn.setImageResource(resource)
    }
}
