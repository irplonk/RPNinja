package com.iplonk.rpninja.ui

import androidx.compose.runtime.Composable
import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.iplonk.rpninja.ui.theme.RPNinjaTheme
import com.iplonk.rpninja.utils.snapshot.Device
import com.iplonk.rpninja.utils.snapshot.Theme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CalculatorScreenSnapshotTest(
	@TestParameter val device: Device,
	@TestParameter val theme: Theme,
) {

	@get:Rule
	val paparazzi = Paparazzi(
		deviceConfig = device.deviceConfig,
	)

	@Test
	fun initialCalculatorScreen() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState()
			)
		}
	}

	@Test
	fun oneItemInStack() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(stackSnapshot = listOf(1.0))
			)
		}
	}

	@Test
	fun twoItemsInStack() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(stackSnapshot = listOf(1.0, 2.0))
			)
		}
	}

	@Test
	fun threeItemsInStack() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(stackSnapshot = listOf(1.0, 2.0, 3.0))
			)
		}
	}

	@Test
	fun userInput() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(workingNumber = "123.45")
			)
		}
	}

	@Test
	fun divideByZeroCalculatorError() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(calculatorError = CalculatorError.DivideByZero)
			)
		}
	}

	@Test
	fun invalidNumberCalculatorError() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(calculatorError = CalculatorError.InvalidNumber)
			)
		}
	}

	@Test
	fun insufficientOperandsCalculatorError() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(calculatorError = CalculatorError.InsufficientOperands)
			)
		}
	}

	@Test
	fun unknownOperationCalculatorError() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(calculatorError = CalculatorError.UnknownOperation)
			)
		}
	}

	@Test
	fun fullCalculatorScreen() {
		paparazzi.snapshot {
			TestCalculatorScreen(
				uiState = CalculatorUiState(
					stackSnapshot = listOf(1.0, 2.0, 3.0),
					workingNumber = "123456789",
					calculatorError = CalculatorError.UnknownOperation
				)
			)
		}
	}

	@Composable
	private fun TestCalculatorScreen(
		uiState: CalculatorUiState
	) {
		RPNinjaTheme(darkTheme = theme.isDarkMode) {
			CalculatorScreen(
				uiState = uiState,
				onCalculatorButtonClick = {},
			)
		}
	}
}