package ru.practicum.android.diploma.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R

enum class Placeholder {
    SEARCH, // поиск вакансии (пикча без текста с биноклем)
    NO_INTERNET, // проблемы с подключением к интернету
    NO_RESULTS_CAT, // Не удалось получить список вакансий
    NO_REGION, // Такого региона нет
    NO_RESULTS_CARPET, // Не удалось получить список
    SERVER_ERROR_TOWEL, // Ошибка сервера
    SERVER_ERROR_CAT, // Ошибка сервера
    EMPTY_FAVORITES, // Список пуст
    HIDE // Скрыть заглушку
}

fun Fragment.showPlaceholder(
    context: Context,
    placeholder: Placeholder
) {
    //val utilPlugBox = view?.findViewById<LinearLayout>(R.id.util_plug)
    val plugText = view?.findViewById<TextView>(R.id.plug_text)
    val plugIcon = view?.findViewById<ImageView>(R.id.plug_icon)
    //utilPlugBox?.visibility = View.VISIBLE
    plugText?.visibility = View.VISIBLE

    when (placeholder) {
        Placeholder.SEARCH -> showSearchPlug(plugText, plugIcon)
        Placeholder.NO_INTERNET -> showNoInternetPlug(context, plugText, plugIcon)
        Placeholder.NO_RESULTS_CAT -> showNoResultsCatPlug(context, plugText, plugIcon)
        Placeholder.NO_REGION -> showNoRegionPlug(context, plugText, plugIcon)
        Placeholder.NO_RESULTS_CARPET -> showNoResultsCarpetPlug(context, plugText, plugIcon)
        Placeholder.SERVER_ERROR_TOWEL -> showServerErrorTowelPlug(context, plugText, plugIcon)
        Placeholder.SERVER_ERROR_CAT -> showServerErrorCatPlug(context, plugText, plugIcon)
        Placeholder.EMPTY_FAVORITES -> showEmptyFavoritesPlug(context, plugText, plugIcon)
        //Placeholder.HIDE -> dontShow(context, utilPlugBox, plugText, plugIcon)
        else -> {}
    }
}

private fun showSearchPlug(plugText: TextView?, plugIcon: ImageView?) {
    plugText?.visibility = View.GONE
    plugIcon?.setImageResource(R.drawable.placeholder_search)
}

private fun showNoInternetPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_no_internet)
    plugIcon?.setImageResource(R.drawable.placeholder_no_internet)
}

private fun showNoResultsCatPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_no_results)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
}

private fun showNoRegionPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.region_no_region)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
}

private fun showNoResultsCarpetPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.region_error)
    plugIcon?.setImageResource(R.drawable.placeholder_no_results_carpet)
}

private fun showServerErrorTowelPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.search_server_error)
    plugIcon?.setImageResource(R.drawable.placeholder_server_error)
}

private fun showServerErrorCatPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.vacancy_server_error)
    plugIcon?.setImageResource(R.drawable.placeholder_server_error_cat)
}

private fun showEmptyFavoritesPlug(context: Context, plugText: TextView?, plugIcon: ImageView?) {
    plugText?.text = context.resources.getString(R.string.favorites_empty)
    plugIcon?.setImageResource(R.drawable.placeholder_empty_favorites)
}

private fun dontShow(context: Context, utilPlugBox: LinearLayout?, plugText: TextView?, plugIcon: ImageView?) {
    utilPlugBox?.visibility = View.GONE
}
