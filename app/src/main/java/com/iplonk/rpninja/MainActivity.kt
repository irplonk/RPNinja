package com.iplonk.rpninja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.iplonk.rpninja.ui.CalculatorApp
import com.iplonk.rpninja.ui.theme.RPNinjaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RPNinjaTheme {
				CalculatorApp()
			}
		}
	}
}
