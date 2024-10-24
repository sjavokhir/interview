package uz.javokhirdev.interview.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun AppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        SystemAppearance(true)

        MaterialTheme(
            colorScheme = lightScheme,
            content = content,
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
