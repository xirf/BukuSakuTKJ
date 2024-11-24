package id.my.andka.bstkj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import id.my.andka.bstkj.ui.theme.BsTKJTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BsTKJTheme() {
                BsTKJContent(modifier = Modifier)
            }
        }
    }
}