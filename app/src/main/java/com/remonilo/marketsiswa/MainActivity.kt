package com.remonilo.marketsiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.remonilo.marketsiswa.ui.screen.HomeScreen
import com.remonilo.marketsiswa.ui.theme.MarketSiswaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketSiswaTheme {
                HomeScreen()
            }
        }
    }
}
