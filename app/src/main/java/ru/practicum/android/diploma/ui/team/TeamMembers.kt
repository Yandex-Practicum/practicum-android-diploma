package ru.practicum.android.diploma.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.team.model.TeamMember
import ru.practicum.android.diploma.ui.team.theme.TeamColors

@Composable
fun rememberTeamMembers(): List<TeamMember> = listOf(
    TeamMember(
        name = stringResource(R.string.team_member_1_name),
        avatarResId = R.drawable.team_avatar_inna,
        role = stringResource(R.string.team_member_1_role),
        roleIconResId = R.drawable.ic_role_captain,
        roleColor = TeamColors.CaptainRole,
        roleBackgroundColor = TeamColors.CaptainRoleBackground,
    ),
    TeamMember(
        name = stringResource(R.string.team_member_2_name),
        avatarResId = R.drawable.team_avatar_maria,
        role = stringResource(R.string.team_member_2_role),
        roleIconResId = R.drawable.ic_role_mate,
        roleColor = TeamColors.MateRole,
        roleBackgroundColor = TeamColors.MateRoleBackground,
        avatarContentScale = ContentScale.Fit,
    ),
    TeamMember(
        name = stringResource(R.string.team_member_3_name),
        avatarResId = R.drawable.team_avatar_denis,
        role = stringResource(R.string.team_member_3_role),
        roleIconResId = R.drawable.ic_role_boatswain,
        roleColor = TeamColors.BoatswainRole,
        roleBackgroundColor = TeamColors.BoatswainRoleBackground,
    ),
    TeamMember(
        name = stringResource(R.string.team_member_4_name),
        avatarResId = R.drawable.team_avatar_timofey,
        role = stringResource(R.string.team_member_4_role),
        roleIconResId = R.drawable.ic_role_cook,
        roleColor = TeamColors.CookRole,
        roleBackgroundColor = TeamColors.CookRoleBackground,
    ),
)
