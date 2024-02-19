package ru.practicum.android.diploma.util

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun createConfirmationDialog(
    context: Context,
    title: String,
    positiveButtonText: String,
    negativeButtonText: String,
    positiveAction: () -> Unit,
    negativeAction: () -> Unit
) {
    MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setNegativeButton(negativeButtonText) { _, _ ->
            negativeAction.invoke()
        }
        .setPositiveButton(positiveButtonText) { _, _ ->
            positiveAction.invoke()
        }
        .show()
}
