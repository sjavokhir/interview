package uz.javokhirdev.interview.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import uz.javokhirdev.interview.data.ContentModel

data class ContentDetailScreen(
    val content: ContentModel
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        content.markdown?.let { markdown ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Markdown(
                        content = markdown,
                        colors = markdownColor(),
                        typography = markdownTypography(),
                    )
                }
            }
        }
    }
}
