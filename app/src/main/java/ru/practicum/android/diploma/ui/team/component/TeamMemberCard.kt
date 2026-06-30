package ru.practicum.android.diploma.ui.team.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.team.model.TeamMember
import ru.practicum.android.diploma.ui.team.theme.TeamColors
import ru.practicum.android.diploma.ui.team.theme.TeamTypography

@Composable
fun TeamMemberCard(
    member: TeamMember,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(22.dp),
                spotColor = TeamColors.Accent.copy(alpha = 0.12f),
                ambientColor = TeamColors.Accent.copy(alpha = 0.08f),
            ),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = TeamColors.CardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TeamMemberAvatar(member = member)
            Text(
                modifier = Modifier.weight(1f),
                text = member.name,
                style = TeamTypography.MemberName,
                color = TeamColors.PrimaryText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            RoleBadge(
                role = member.role,
                roleIconResId = member.roleIconResId,
                roleColor = member.roleColor,
                roleBackgroundColor = member.roleBackgroundColor,
            )
        }
    }
}

@Composable
private fun TeamMemberAvatar(
    member: TeamMember,
    modifier: Modifier = Modifier,
) {
    val avatarPadding = if (member.avatarContentScale == ContentScale.Fit) 4.dp else 0.dp

    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(TeamColors.AvatarBackground),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(avatarPadding),
            painter = painterResource(member.avatarResId),
            contentDescription = member.name,
            contentScale = member.avatarContentScale,
        )
    }
}
