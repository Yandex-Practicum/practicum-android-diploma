package ru.practicum.android.diploma.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

data class ConfirmationDialogParams(
    val title: String,
    val positiveButtonText: String,
    val negativeButtonText: String,
    val positiveAction: () -> Unit,
    val negativeAction: () -> Unit
)

fun createConfirmationDialog(
    context: Context,
    params: ConfirmationDialogParams
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(params.title)
        .setNegativeButton(params.negativeButtonText) { _, _ ->
            params.negativeAction.invoke()
        }
        .setPositiveButton(params.positiveButtonText) { _, _ ->
            params.positiveAction.invoke()
        }
        .show()
}
