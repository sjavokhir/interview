import androidx.compose.ui.window.ComposeUIViewController
import uz.javokhirdev.interview.presentation.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
