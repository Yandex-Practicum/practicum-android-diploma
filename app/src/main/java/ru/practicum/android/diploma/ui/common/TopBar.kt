package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R

@Composable
fun TopBar(
    text: String,
) {
    Row(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dp)
    ) {
        Image(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 12.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(
                    fonts = listOf(Font(R.font.ys_display_bold))
                ),
                fontSize = 22.sp,
                fontWeight = FontWeight(500)
            ),
            modifier = Modifier.align(Alignment.CenterVertically).padding(start = 12.dp),
        )
    }
}
