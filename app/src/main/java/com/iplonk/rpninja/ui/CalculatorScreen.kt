package com.iplonk.rpninja.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.CalculatorViewModel
import com.iplonk.rpninja.ui.CalculatorPadState.BUTTONS_PER_ROW
import com.iplonk.rpninja.ui.CalculatorPadState.MAX_STACK_ELEMENTS

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
	val uiState by viewModel.uiState.collectAsState()
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 8.dp, vertical = 16.dp)
	) {
		Column(modifier = Modifier.align(Alignment.BottomCenter)) {
			val placeholderStackItems = List(MAX_STACK_ELEMENTS - uiState.stackSnapshot.size) { 0.0 }
			Column {
				(placeholderStackItems + uiState.stackSnapshot).forEach { item ->
					Text(text = item.toString())
				}
			}
			uiState.error?.let { error ->
				Text(
					modifier = Modifier.fillMaxWidth(),
					text = stringResource(error.message),
					fontSize = 32.sp,
					color = Color.Red
				)
			} ?: CalculatorDisplay(uiState.workingNumber)
			CalculatorButtonGrid(
				buttons = calculatorButtons,
				onButtonClick = viewModel::onAction
			)
		}
	}
}

@Composable
private fun CalculatorButtonGrid(
	buttons: List<CalculatorButton>,
	onButtonClick: (CalculatorAction) -> Unit
) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(count = BUTTONS_PER_ROW),
		userScrollEnabled = false,
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalArrangement = Arrangement.spacedBy(4.dp)
	) {
		items(buttons) { button ->
			Button(
				modifier = Modifier
					.aspectRatio(1f),
				colors = ButtonDefaults.buttonColors(containerColor = button.backgroundColor),
				onClick = { onButtonClick(button.action) },
			) {
				button.content()
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
			fontSize = 80.sp,
			color = MaterialTheme.colorScheme.onPrimary,
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
}
