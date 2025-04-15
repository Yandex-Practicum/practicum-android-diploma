package ru.practicum.android.diploma.util

enum class Currency(val value: Pair<String, String>) {
    RUR(Pair("RUR", "₽")),
    RUB(Pair("RUB", "₽")),
    BYR(Pair("BYR", "₽(BYR)")),
    USD(Pair("USD", "$")),
    EUR(Pair("EUR", "€")),
    KZT(Pair("KZT", "₸")),
    UAH(Pair("UAH", "₴")),
    AZN(Pair("AZN", "₼")),
    UZS(Pair("UZS", "UZS")),
    GEL(Pair("GEL", "₾")),
    KGT(Pair("KGT", "KGT"));

    companion object {
        fun getCurrencySymbol(vacancyCurrency: String): String {
            for (currency in entries) {
                if (currency.value.first == vacancyCurrency) return currency.value.second
            }

            return ""
        }
    }
}
