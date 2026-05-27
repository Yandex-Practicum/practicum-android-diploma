package ru.practicum.android.diploma.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Dimens

class TeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                AppTheme {
                    TeamScreen()
                }
            }
        }
    }
}

@Composable
fun TeamScreen() {
    TeamContent(
        title = stringResource(R.string.team_title),
        membersLabel = stringResource(R.string.team_members_label),
        members = stringArrayResource(R.array.team_members).toList(),
    )
}

@Composable
fun TeamContent(
    title: String,
    membersLabel: String,
    members: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.paddingDefault, Dimens.paddingTopLarge),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.paddingMedium),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )

        Spacer(modifier = Modifier.height(Dimens.paddingDefault))

        Text(
            text = membersLabel,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            ),
        )

        Spacer(modifier = Modifier.height(Dimens.paddingTopLarge))

        members.forEachIndexed { index, name ->
            Text(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            )
            if (index < members.lastIndex) {
                Spacer(modifier = Modifier.height(Dimens.paddingDefault))
            }
        }
    }
}

@Preview(
    name = "Team screen",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun TeamScreenPreview() {
    AppTheme {
        TeamContent(
            title = "Команда",
            membersLabel = "Над приложением работали",
            members = listOf(
                "Павел Мичка",
                "Сергей Иванов",
                "Сергей Гнедовский",
                "Даниил Квасников",
            ),
        )
    }
}
