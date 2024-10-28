package uz.javokhirdev.interview.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import uz.javokhirdev.interview.data.CategoryMode
import uz.javokhirdev.interview.data.ContentModel
import uz.javokhirdev.interview.domain.ContentEvent
import uz.javokhirdev.interview.domain.ContentScreenModel

data class ContentsScreen(
    val category: CategoryMode
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @OptIn(
        ExperimentalVoyagerApi::class,
        ExperimentalMaterial3Api::class
    )
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val screenModel = rememberScreenModel { ContentScreenModel() }
        val state by screenModel.state.collectAsState()

        LifecycleEffectOnce {
            screenModel.onEvent(ContentEvent.FetchContents(category))
        }

        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigator::pop) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                        )
                    }
                }
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .5f)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(state.contents) {
                    ContentItem(it) {
                        navigator.push(ContentDetailScreen(it))
                    }
                }
                item { Spacer(Modifier.navigationBarsPadding()) }
            }
        }
    }

    @Composable
    private fun ContentItem(
        content: ContentModel,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = MaterialTheme.shapes.medium,
                )
                .background(MaterialTheme.colorScheme.surface)
                .clickable(onClick = onClick)
                .padding(16.dp),
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
