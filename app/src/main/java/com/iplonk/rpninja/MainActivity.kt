package com.iplonk.rpninja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.iplonk.rpninja.ui.CalculatorScreen
import com.iplonk.rpninja.ui.theme.RPNinjaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPNinjaTheme {
                CalculatorScreen()
            }
        }
    }
}
