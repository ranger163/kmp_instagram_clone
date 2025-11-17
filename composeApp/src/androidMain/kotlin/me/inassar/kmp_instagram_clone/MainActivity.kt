package me.inassar.kmp_instagram_clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Primary Android activity that hosts the Compose application content.
 */
class MainActivity : ComponentActivity() {

    /**
     * Enables immersive mode and injects the shared [App] composable into the view hierarchy.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

/**
 * Compose tooling preview for Android Studio to quickly render the shared [App].
 */
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
