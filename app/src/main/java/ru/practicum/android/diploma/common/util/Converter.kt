package ru.practicum.android.diploma.common.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object Converter {
    fun convertSalaryToString(from: Int?, to: Int?, currency: String?): String {
        return when {
            from != null && to != null -> String.format(
                Locale.getDefault(),
                "от %s до %s %s",
                applyDigitSeparation(from),
                applyDigitSeparation(to),
                addCurrencySymbol(currency)
            )
            from != null -> String.format(
                Locale.getDefault(),
                "от %s %s",
                applyDigitSeparation(from),
                addCurrencySymbol(currency)
            )
            to != null -> String.format(
                Locale.getDefault(),
                "до %s %s",
                applyDigitSeparation(to),
                addCurrencySymbol(currency)
            )
            else -> "Зарплата не указана"
        }
    }

    fun applyDigitSeparation(number: Int?): String {
        if (number == null) return ""

        val dfs = DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }
        val df = DecimalFormat("###,###", dfs)
        return df.format(number)
    }

    fun buildResultingSentence(count: Int, noun: String): String {
        val lastDigit = count % LAST_DIGIT_MODULO
        val lastTwoDigits = count % LAST_TWO_DIGITS_MODULO

        val nounForm: String = when {
            lastTwoDigits in LAST_DIGIT_ELEVEN..LAST_DIGIT_FOURTEEN -> noun
            lastDigit == LAST_DIGIT_ONE -> noun.substring(0, noun.length - 1) + "а"
            lastDigit in LAST_DIGIT_TWO..LAST_DIGIT_FOUR -> noun.substring(0, noun.length - 1) + "о"
            else -> noun
        }

        val appliedSentenceEnding: String = when {
            lastTwoDigits in LAST_DIGIT_ELEVEN..LAST_DIGIT_FOURTEEN -> VACANCIY
            lastDigit == LAST_DIGIT_ONE -> VACANCIYA
            lastDigit in LAST_DIGIT_TWO..LAST_DIGIT_FOUR -> VACANCYY
            else -> VACANCIY
        }

        return "$nounForm ${applyDigitSeparation(count)} $appliedSentenceEnding"
    }

    // java.util.Currency не поддерживает большинство символов, поэтому метод написан вручную
    private fun addCurrencySymbol(abbr: String?): String {
        if (abbr == null) return ""
        return when (abbr) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "С"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "UZS"
            else -> abbr
        }
    }

    private const val VACANCIYA = "вакансия"
    private const val VACANCIY = "вакансий"
    private const val VACANCYY = "вакансии"
    private const val LAST_DIGIT_MODULO = 10
    private const val LAST_TWO_DIGITS_MODULO = 100
    private const val LAST_DIGIT_ELEVEN = 11
    private const val LAST_DIGIT_FOURTEEN = 14
    private const val LAST_DIGIT_ONE = 1
    private const val LAST_DIGIT_TWO = 2
    private const val LAST_DIGIT_FOUR = 4
}
