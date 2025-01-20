package ru.practicum.android.diploma.search.domain.model

data class Salary(
    val from: Int? = null,
    val to: Int? = null,
    val currency: String
) {
    fun getSalaryToString(salary: Salary?): String {
        return if (salary == null) {
            "Зарплата не указана"
        } else
            if (salary.from != null && salary.to != null) {
                "от ${salary.from} до ${salary.to} " + salary.currency
            } else
                if (salary.from != null){
                    "от ${salary.from} " + salary.currency
                } else {
                    "до ${salary.to} " + salary.currency
                }
    }
}
