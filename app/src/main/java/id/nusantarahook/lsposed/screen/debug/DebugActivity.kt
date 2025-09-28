package id.nusantarahook.lsposed.screen.debug

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.nusantarahook.lsposed.ui.theme.NusantaraHookLsposedTheme

class DebugActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val errorMessage = intent.getStringExtra("error") ?: "No error available."

        setContent {
            NusantaraHookLsposedTheme {
                CrashScreen(
                    errorMessage = errorMessage,
                    onBack = { onBackPressed() },
                    onShare = {
                        val intent = Intent(Intent.ACTION_SEND)
                            .setType("text/plain")
                            .putExtra(Intent.EXTRA_TEXT, errorMessage)
                        startActivity(Intent.createChooser(intent, "Share crash log"))
                    }
                )
            }
        }
    }
}