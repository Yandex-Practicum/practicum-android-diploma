/**
 * Функции для работы с пагинацией
 */
package ru.practicum.android.diploma.util

object PaginationUtils {
    // Метод для проверки, нужно ли загрузить следующую страницу при страничной загрузке
    fun shouldLoadNextPageByPage(currentPage: Int, totalItems: Int, threshold: Int = 10): Boolean {
        return totalItems - currentPage * threshold <= threshold
    }

    // Метод для проверки, нужно ли загрузить следующие элементы при загрузке без страниц
    fun shouldLoadNextPageByPosition(currentItemPosition: Int, totalItemCount: Int, threshold: Int = 5): Boolean {
        return totalItemCount - currentItemPosition <= threshold
    }

}
