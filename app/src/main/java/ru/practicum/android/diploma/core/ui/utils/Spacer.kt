package ru.practicum.android.diploma.core.ui.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Spacer(
    width: Dp? = null,
    height: Dp? = null
) {
    Spacer(
        modifier = Modifier
            .then(if (height != null) Modifier.height(height) else Modifier)
            .then(if (width != null) Modifier.width(width) else Modifier)
    )
}
