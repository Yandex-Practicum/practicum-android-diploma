package ru.practicum.android.diploma.commonutils

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.ui.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {
    fun doToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    fun visibilityView(views: Array<View>? = emptyArray(), v: View) {
        if (views != null) {
            for (view in views) view.visibility = View.GONE
        }
        v.visibility = View.VISIBLE
    }

    fun outputStackTrace(tag: String, e: Throwable) {
        e.stackTrace.forEach { element ->
            Log.e(
                tag,
                "Class: ${element.className}, Method: ${element.methodName}, Line: ${element.lineNumber}"
            )
        }
    }

    private fun Context.getCurrencySymbol(currencyCode: String): String {
        return when (currencyCode) {
            "RUR", "RUB" -> getString(R.string.currency_rur)
            "BYN" -> getString(R.string.currency_br_rur)
            "USD" -> getString(R.string.currency_usd)
            "EUR" -> getString(R.string.currency_eur)
            "KZT" -> getString(R.string.currency_kzt)
            "UAH" -> getString(R.string.currency_uah)
            "AZN" -> getString(R.string.currency_azn)
            "UZS" -> getString(R.string.currency_uzs)
            "GEL" -> getString(R.string.currency_gel)
            "KGS" -> getString(R.string.currency_kzt)
            "AMD" -> getString(R.string.currency_amd)
            else -> currencyCode
        }
    }

    private fun formatNumberDividers(number: Int): String {
        return String.format(Locale.US, "%,d", number).replace(",", " ")
    }

    fun Context.formatSalary(salaryFrom: Int?, salaryTo: Int?, salaryCurrency: String?): String {
        return when {
            salaryFrom == null && salaryTo != null && salaryCurrency != null -> {
                getString(R.string.salary_to, formatNumberDividers(salaryTo), getCurrencySymbol(salaryCurrency))
            }

            salaryFrom != null && salaryTo == null && salaryCurrency != null -> {
                getString(R.string.salary_from, formatNumberDividers(salaryFrom), getCurrencySymbol(salaryCurrency))
            }

            salaryFrom != null && salaryTo != null && salaryCurrency != null -> {
                getString(
                    R.string.salary_from_to,
                    formatNumberDividers(salaryFrom),
                    formatNumberDividers(salaryTo),
                    getCurrencySymbol(salaryCurrency)
                )
            }

            else -> getString(R.string.no_salary)
        }
    }

    fun convertTimeToMilliseconds(timeString: String): Long {
        return runCatching {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = format.parse(timeString) ?: throw IllegalArgumentException("Invalid date format")
            date.time
        }.getOrElse {
            -1L
        }
    }

    fun <T> convertObjectWithStringToString(namedItems: List<T>, nameExtractor: (T) -> String): String {
        return "<ul> <li>${namedItems.joinToString(separator = "</li> <li>") { nameExtractor(it) }}</li> </ul>"
    }

    fun <T> executeQueryDb(
        query: suspend () -> T,
        tag: String
    ): Flow<Resource<T>> = flow {
        runCatching { query() }
            .fold(
                onSuccess = { result ->
                    emit(Resource.Success(result))
                },
                onFailure = { e ->
                    outputStackTrace(tag, e)
                    emit(Resource.Error(e.message.toString()))
                }
            )
    }

    fun Context.closeKeyBoard(view: View) {
        val closeKeyBoard = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        closeKeyBoard?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
