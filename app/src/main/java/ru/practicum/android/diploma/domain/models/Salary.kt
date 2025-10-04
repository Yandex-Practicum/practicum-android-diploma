package ru.practicum.android.diploma.domain.models

data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: String?
) {
    fun getFormattedSalary(): String {
        val currencySymbol = when (currency?.uppercase()) {
            "RUR", "RUB" -> "₽"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "BYR", "BYN" -> "Br"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "KGS" -> "с"
            "GEL" -> "₾"
            else -> currency ?: ""
        }

        return when {
            from != null && to != null -> "от ${formatNumber(from)} до ${formatNumber(to)} $currencySymbol"
            from != null -> "от ${formatNumber(from)} $currencySymbol"
            to != null -> "до ${formatNumber(to)} $currencySymbol"
            else -> "Зарплата не указана"
        }.trim()
    }

    private fun formatNumber(number: Int): String {
        return String.format("%,d", number).replace(',', ' ')
    }
}
