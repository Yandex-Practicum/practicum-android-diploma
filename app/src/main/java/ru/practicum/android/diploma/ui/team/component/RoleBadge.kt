package ru.practicum.android.diploma.ui.team.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.team.theme.TeamTypography

@Composable
fun RoleBadge(
    role: String,
    roleIconResId: Int,
    roleColor: Color,
    roleBackgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(roleBackgroundColor)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(roleIconResId),
            contentDescription = null,
            tint = roleColor,
        )
        Text(
            text = role,
            style = TeamTypography.Role,
            color = roleColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
