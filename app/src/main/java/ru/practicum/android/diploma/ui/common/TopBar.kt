package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    navIconVisible: Boolean,
    endFirstIconVisible: Boolean,
    endFirstIconId: Int = R.drawable.ic_share,
    endSecondIconVisible: Boolean,
    endSecondIconId: Int = R.drawable.ic_like_empty
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(navIconVisible) {
            IconImage(R.drawable.ic_back)
        } else {
            Spacer(modifier = Modifier.width(16.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 12.dp),
        )
        if (endFirstIconVisible || endSecondIconVisible) {
            Spacer(modifier = Modifier.weight(1f))
        }
        if (endFirstIconVisible) {
            IconImage(resId = endFirstIconId)
        }
        if (endSecondIconVisible) {
            IconImage(resId = endSecondIconId)
        }
    }
}

@Composable
fun IconImage(
    resId: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .width(48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
        )
    }
}
