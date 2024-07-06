package ru.practicum.android.diploma.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.utils.UtilForPlug.EMPTY_FAVORITES
import ru.practicum.android.diploma.utils.UtilForPlug.NO_INTERNET
import ru.practicum.android.diploma.utils.UtilForPlug.NO_REGION
import ru.practicum.android.diploma.utils.UtilForPlug.NO_RESULTS_CARPET
import ru.practicum.android.diploma.utils.UtilForPlug.NO_RESULTS_CAT
import ru.practicum.android.diploma.utils.UtilForPlug.SEARCH
import ru.practicum.android.diploma.utils.UtilForPlug.SERVER_ERROR_CAT
import ru.practicum.android.diploma.utils.UtilForPlug.SERVER_ERROR_TOWEL

/** заглушки на ошибки или остутствии результата поиска
 *
 * пример вызова из фрагмента:
 * showPlug(requireContext(), NO_INTERNET)
 *
 * в макете:
 *     <include
 *         android:id="@+id/util_plug"
 *         layout="@layout/util_plug"
 *         android:visibility="gone" />
 * */

object UtilForPlug {

    const val SEARCH = "поиск вакансии"
    const val NO_INTERNET = "проблемы с подключением к интернету"
    const val NO_RESULTS_CAT = "Не удалось получить список вакансий"
    const val NO_REGION = "Такого региона нет"
    const val NO_RESULTS_CARPET = "Не удалось получить список"
    const val SERVER_ERROR_TOWEL = "Ошибка сервера"
    const val SERVER_ERROR_CAT = "Ошибка сервера"
    const val EMPTY_FAVORITES = "Список пуст"
}

fun Fragment.showPlug(
    context: Context,
    problemTip: String
) {
    val utilPlugBox = view?.findViewById<LinearLayout>(R.id.util_plug)
    val plugText = view?.findViewById<TextView>(R.id.plug_text)
    val plugIcon = view?.findViewById<ImageView>(R.id.plug_icon)
    utilPlugBox?.visibility = View.VISIBLE

    when (problemTip) {
        SEARCH -> {
            plugText?.visibility = View.GONE
            plugIcon?.setImageResource(R.drawable.placeholder_search)
        }

        NO_INTERNET -> {
            plugText?.text = context.resources.getString(R.string.search_no_internet)
            plugIcon?.setImageResource(R.drawable.placeholder_no_internet)
        }

        NO_RESULTS_CAT -> {
            plugText?.text = context.resources.getString(R.string.search_no_results)
            plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
        }

        NO_REGION -> {
            plugText?.text = context.resources.getString(R.string.region_no_region)
            plugIcon?.setImageResource(R.drawable.placeholder_no_results_cat)
        }

        NO_RESULTS_CARPET -> {
            plugText?.text = context.resources.getString(R.string.region_error)
            plugIcon?.setImageResource(R.drawable.placeholder_no_results_carpet)
        }

        SERVER_ERROR_TOWEL -> {
            plugText?.text = context.resources.getString(R.string.search_server_error)
            plugIcon?.setImageResource(R.drawable.placeholder_server_error)
        }

        SERVER_ERROR_CAT -> {
            plugText?.text = context.resources.getString(R.string.vacancy_server_error)
            plugIcon?.setImageResource(R.drawable.placeholder_server_error_cat)
        }

        EMPTY_FAVORITES -> {
            plugText?.text = context.resources.getString(R.string.favorites_empty)
            plugIcon?.setImageResource(R.drawable.placeholder_empty_favorites)
        }

        else -> {
            utilPlugBox?.visibility = View.GONE
        }
    }
}

// показать случайную заглушку (тест, потом удалим)

val plugOptions = listOf(
    SEARCH,
    NO_INTERNET,
    NO_RESULTS_CAT,
    NO_REGION,
    NO_RESULTS_CARPET,
    SERVER_ERROR_TOWEL,
    SERVER_ERROR_CAT,
    EMPTY_FAVORITES
)

fun Fragment.showRandomPlug(context: Context) {
    showPlug(context, plugOptions.random())
}


