import androidx.compose.ui.window.ComposeUIViewController
import lod.gamedb.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
