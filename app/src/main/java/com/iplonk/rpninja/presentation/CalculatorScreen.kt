package com.iplonk.rpninja.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.presentation.CalculatorPadState.backgroundColor
import com.iplonk.rpninja.presentation.CalculatorPadState.contentColor
import kotlin.math.exp

/**
 * The UI for the entire calculator screen, responsible for rendering the stack, calculator display,
 * and keypad as well as any error messages
 */
@Composable
internal fun CalculatorScreen(
	uiState: CalculatorUiState,
	onCalculatorButtonClick: (CalculatorAction) -> Unit,
) {
	Surface(
		modifier = Modifier
			.background(color = MaterialTheme.colorScheme.background)
			.fillMaxSize()
			.padding(8.dp)
	) {
		Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
			Column {
				CalculatorStack(uiState.stackSnapshot)
				HorizontalDivider(
					modifier = Modifier.padding(vertical = 4.dp),
					thickness = 1.dp,
					color = MaterialTheme.colorScheme.onSurface
				)
				uiState.calculatorError?.let { error ->
					CalculatorErrorMessage(error)
				}
			}
			if (uiState.workingNumber.isNotBlank()) {
				CalculatorDisplay(modifier = Modifier.weight(1f), expression = uiState.workingNumber)
			}
			CalculatorButtonGrid(onCalculatorButtonClick = onCalculatorButtonClick)
		}
	}
}

/**
 * Represents an error message that is displayed to the user
 */
@Composable
private fun CalculatorErrorMessage(error: CalculatorError) {
	Text(
		modifier = Modifier.fillMaxWidth(),
		text = stringResource(error.message),
		fontSize = 16.sp,
		color = MaterialTheme.colorScheme.error,
		fontWeight = FontWeight.Bold,
		lineHeight = 16.sp,
	)
}

/**
 * Represents the last [CalculatorPadState.MAX_STACK_SIZE] stack of numbers that the user has entered
 * Displays them from least recently entered (top) to most recently entered (bottom) with placeholders
 * if the stack is not full.
 */
@Composable
private fun CalculatorStack(stackSnapshot: List<Double>) {
	val placeholderStackItems = List(CalculatorPadState.MAX_STACK_SIZE - stackSnapshot.size) { 0.0 }
	(placeholderStackItems + stackSnapshot).forEachIndexed { index, item ->
		Text(
			text = item.toString(),
			fontSize = 28.sp,
			color = MaterialTheme.colorScheme.onSurface,
			fontWeight = if (index == CalculatorPadState.MAX_STACK_SIZE - 1) FontWeight.SemiBold else null
		)
	}
}

/**
 * Represents the keypad of the calculator
 */
@Composable
private fun CalculatorButtonGrid(onCalculatorButtonClick: (CalculatorAction) -> Unit) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(count = CalculatorPadState.BUTTONS_PER_ROW),
		userScrollEnabled = false,
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalArrangement = Arrangement.spacedBy(4.dp)
	) {
		items(calculatorButtons) { button ->
			Button(
				modifier = Modifier.aspectRatio(1.25f),
				colors = ButtonDefaults.buttonColors(
					containerColor = button.backgroundColor,
					contentColor = button.contentColor
				),
				onClick = { onCalculatorButtonClick(button.action) },
			) {
				button.text?.let { text ->
					Text(text = stringResource(text), fontSize = 32.sp)
				} ?: run {
					button.content()
				}
			}
		}
	}
}

/**
 * Represents the user input. Scrollable to handle large numbers.
 */
@Composable
internal fun CalculatorDisplay(modifier: Modifier = Modifier, expression: String) {
	val scrollState = rememberScrollState()

	LaunchedEffect(expression) {
		scrollState.animateScrollTo(scrollState.maxValue) // Scroll to the right-most position
	}

	Row(
		modifier = Modifier
			.horizontalScroll(scrollState)
			.then(modifier),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.End
	) {
		BasicTextField(
			value = expression,
			onValueChange = { },
			textStyle = TextStyle(
				fontSize = 64.sp,
				color = MaterialTheme.colorScheme.onSurface,
				textAlign = TextAlign.End,
			),
			maxLines = 1,
			readOnly = true,
		)
	}
}

@Stable
object CalculatorPadState {
	const val BUTTONS_PER_ROW = 4
	const val MAX_STACK_SIZE = 3

	val CalculatorButton.backgroundColor
		@Composable
		get() = when (action) {
			is CalculatorAction.Operation -> MaterialTheme.colorScheme.tertiary
			is CalculatorAction.Number,
			CalculatorAction.Decimal,
			CalculatorAction.DeleteLastCharacter -> MaterialTheme.colorScheme.primary

			is CalculatorAction.Enter -> MaterialTheme.colorScheme.secondary
			is CalculatorAction.AllClear -> MaterialTheme.colorScheme.error
		}

	val CalculatorButton.contentColor
		@Composable
		get() = when (action) {
			is CalculatorAction.Operation -> MaterialTheme.colorScheme.onTertiary
			is CalculatorAction.Number,
			CalculatorAction.Decimal,
			CalculatorAction.DeleteLastCharacter -> MaterialTheme.colorScheme.onPrimary

			is CalculatorAction.Enter -> MaterialTheme.colorScheme.onSecondary
			is CalculatorAction.AllClear -> MaterialTheme.colorScheme.onError
		}
}
