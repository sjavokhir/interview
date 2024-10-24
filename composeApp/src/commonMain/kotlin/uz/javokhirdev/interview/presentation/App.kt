package uz.javokhirdev.interview.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import uz.javokhirdev.interview.theme.AppTheme

@Composable
internal fun App() {
    AppTheme {
        Navigator(CategoriesScreen) { navigator ->
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                content = {
                    navigator.lastItemOrNull?.Content()
                },
            )
        }
    }
}
