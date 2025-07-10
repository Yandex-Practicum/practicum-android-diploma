package ru.practicum.android.diploma.domain.models.salary

sealed class Salary {
    object NotSpecifies : Salary()
    data class From(val amount: Int, val currency: String) : Salary()
    data class Range(val from: Int, val to: Int, val currency: String) : Salary()
    data class Fixed(val amount: Int, val currency: String) : Salary()
}
