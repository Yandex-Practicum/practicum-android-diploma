package ru.practicum.android.diploma.features.search.presentation

enum class SearchScreenState (
    val isChip: Boolean,
    val isFeed: Boolean,
    val isProgressBar: Boolean = false,
    val isPlaceholder: Boolean = false
) {
    NEWBORN(
        isChip = false,
        isFeed = false,
        isPlaceholder = true
    ),

    SEARCHING(
        isChip = false,
        isFeed = false,
        isProgressBar = true
    ),

    EMPTY_RESULT(
        isChip = true,
        isFeed = false,
    ),

    SOMETHING_WENT_WRONG(
        isChip = true,
        isFeed = false,
    ),

    RESPONSE_RESULTS(
        isChip = true,
        isFeed = true,
    )
}