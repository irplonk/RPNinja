package com.iplonk.rpninja.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.ui.CalculatorPadState.BUTTONS_PER_ROW
import com.iplonk.rpninja.ui.CalculatorPadState.MAX_STACK_ELEMENTS
import com.iplonk.rpninja.ui.CalculatorPadState.backgroundColor
import com.iplonk.rpninja.ui.CalculatorPadState.contentColor


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
			val placeholderStackItems = List(MAX_STACK_ELEMENTS - uiState.stackSnapshot.size) { 0.0 }
			Column {
				(placeholderStackItems + uiState.stackSnapshot).forEachIndexed { index, item ->
					Text(
						text = item.toString(),
						fontSize = 32.sp,
						color = MaterialTheme.colorScheme.onSurface,
						fontWeight = if (index == MAX_STACK_ELEMENTS - 1) FontWeight.SemiBold else null
					)
				}
				HorizontalDivider(
					modifier = Modifier.padding(vertical = 4.dp),
					thickness = 1.dp,
					color = MaterialTheme.colorScheme.onSurface
				)
				uiState.calculatorError?.let { error ->
					Text(
						modifier = Modifier.fillMaxWidth(),
						text = stringResource(error.message),
						fontSize = 32.sp,
						color = MaterialTheme.colorScheme.error,
						fontWeight = FontWeight.Bold,
					)
				}
			}
			if (uiState.workingNumber.isNotBlank()) {
				CalculatorDisplay(uiState.workingNumber)
			}
			CalculatorButtonGrid(onCalculatorButtonClick = onCalculatorButtonClick)
		}
	}
}

@Composable
private fun CalculatorButtonGrid(onCalculatorButtonClick: (CalculatorAction) -> Unit) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(count = BUTTONS_PER_ROW),
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
					Text(text = stringResource(text), fontSize = 36.sp)
				} ?: run {
					button.content()
				}
			}
		}
	}
}

@Composable
private fun CalculatorDisplay(expression: String) {
	BasicTextField(
		modifier = Modifier.fillMaxWidth(),
		value = expression,
		onValueChange = {},
		textStyle = TextStyle(
			fontSize = 72.sp,
			color = MaterialTheme.colorScheme.onSurface,
			textAlign = TextAlign.End,
		),
		maxLines = 1,
		singleLine = true,
		readOnly = true,
	)
}

@Stable
object CalculatorPadState {
	const val BUTTONS_PER_ROW = 4
	const val MAX_STACK_ELEMENTS = 3

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
