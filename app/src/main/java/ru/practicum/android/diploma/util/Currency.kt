package ru.practicum.android.diploma.util

enum class Currency(value: Pair<String, String>) {
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
    KGT(Pair("KGT", "KGT"))
}
