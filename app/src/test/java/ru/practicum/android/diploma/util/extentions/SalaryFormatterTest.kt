package ru.practicum.android.diploma.util.extentions

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.practicum.android.diploma.domain.models.Salary

class SalaryFormatterTest {

    private val labels = SalaryFormatLabels(
        fromLabel = "от",
        toLabel = "до",
        emptyText = "Уровень зарплаты не указан",
    )

    @Test
    fun `formatSalary returns emptyText when salary is null`() {
        val result = null.formatSalary(labels)

        assertEquals("Уровень зарплаты не указан", result)
    }

    @Test
    fun `formatSalary returns emptyText when from and to are null`() {
        val result = Salary(from = null, to = null, currency = "RUB").formatSalary(labels)

        assertEquals("Уровень зарплаты не указан", result)
    }

    @Test
    fun `formatSalary returns from range with currency symbol`() {
        val result = Salary(from = 1_000_000, to = null, currency = "RUB").formatSalary(labels)

        assertEquals("от 1 000 000 ₽", result)
    }

    @Test
    fun `formatSalary returns to range with currency symbol`() {
        val result = Salary(from = null, to = 12_345_678, currency = "USD").formatSalary(labels)

        assertEquals("до 12 345 678 $", result)
    }

    @Test
    fun `formatSalary returns full range with currency symbol`() {
        val result = Salary(from = 100_000, to = 150_000, currency = "EUR").formatSalary(labels)

        assertEquals("от 100 000 до 150 000 €", result)
    }
}
