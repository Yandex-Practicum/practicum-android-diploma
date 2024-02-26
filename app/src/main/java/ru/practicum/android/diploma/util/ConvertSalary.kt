package ru.practicum.android.diploma.util

class ConvertSalary {
    fun formatSalary(salaryFrom: Int?, salaryTo: Int?): String {
        return when {
            salaryFrom != null && salaryTo == null || salaryTo == 0 -> "от $salaryFrom"
            salaryFrom == 0 || salaryFrom == null && salaryTo != null && salaryTo != 0 -> "до $salaryTo"
            salaryFrom != null -> "от $salaryFrom до $salaryTo"
            else -> {
                "Зарплата не указана"
            }
        }
    }

    fun formatSalaryWithCurrency(salaryFrom: Int?, salaryTo: Int?, currency: String?): String {
        val formattedSalary = formatSalary(salaryFrom, salaryTo)
        return when (currency) {
            "USD" -> "$formattedSalary €"
            "EUR" -> "$formattedSalary $"
            "RUR" -> "$formattedSalary ₽"
            "AZN" -> "$formattedSalary ₼"
            "BYR" -> "$formattedSalary Br"
            "GEL" -> "$formattedSalary ₾"
            "KGS" -> "$formattedSalary с"
            "KZT" -> "$formattedSalary ₸"
            "UAH" -> "$formattedSalary ₴"
            "UZS" -> "$formattedSalary Soʻm"
            else -> formattedSalary
        }
    }
}
