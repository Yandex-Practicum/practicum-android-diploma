package ru.practicum.android.diploma.util

import android.content.Context
import android.util.TypedValue
import ru.practicum.android.diploma.R

fun pxToDp(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

internal fun getCurrSymbol(context: Context, codeSymbol: String): String {
    return with(context) {
        when (codeSymbol.uppercase()) {
            "RUB", "RUR" -> getString(R.string.currency_rub)
            "BYR" -> getString(R.string.currency_byr)
            "USD" -> getString(R.string.currency_usd)
            "EUR" -> getString(R.string.currency_eur)
            "KZT" -> getString(R.string.currency_kzt)
            "UAH" -> getString(R.string.currency_uah)
            "AZN" -> getString(R.string.currency_azn)
            "UZS" -> getString(R.string.currency_uzs)
            "GEL" -> getString(R.string.currency_gel)
            "KGT" -> getString(R.string.currency_kgt)
            else -> codeSymbol
        }
    }
}
