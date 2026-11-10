package com.remonilo.foodorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.remonilo.foodorder.ui.FoodOrderApp
import com.remonilo.foodorder.ui.theme.FoodOrderTheme
import com.remonilo.foodorder.viewmodel.FoodOrderViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: FoodOrderViewModel = viewModel()
            val isDark = viewModel.darkMode ?: isSystemInDarkTheme()
            FoodOrderTheme(darkTheme = isDark) {
                FoodOrderApp(viewModel, isDark)
            }
        }
    }
}
