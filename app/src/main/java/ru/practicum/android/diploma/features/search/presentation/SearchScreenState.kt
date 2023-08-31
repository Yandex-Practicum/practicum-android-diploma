package ru.practicum.android.diploma.features.search.presentation

enum class SearchScreenState (
    val isChip: Boolean,
    val isFeed: Boolean,
    val isProgressBar: Boolean,
    val isPlaceholder: Boolean = false
) {
    NEWBORN(
        isChip = false,
        isFeed = false,
        isProgressBar = false,
        isPlaceholder = true
    ),

    SEARCHING(
        isChip = false,
        isFeed = false,
        isProgressBar = false
    ),

    EMPTY_RESULT(
        isChip = true,
        isFeed = false,
        isProgressBar = false
    ),

    SOMETHING_WENT_WRONG(
        isChip = true,
        isFeed = false,
        isProgressBar = false
    ),

    RESPONSE_RESULTS(
        isChip = true,
        isFeed = true,
        isProgressBar = false
    )
}