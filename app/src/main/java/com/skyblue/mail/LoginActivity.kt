package com.skyblue.mail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.skyblue.mail.interfaces.LoginForm
import com.skyblue.mail.ui.theme.SkyblueMailAndroidTheme

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyblueMailAndroidTheme  {
                Screen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Screen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )

    LoginForm()
}
