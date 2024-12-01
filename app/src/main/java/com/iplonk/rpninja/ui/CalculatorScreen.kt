package com.iplonk.rpninja.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.CalculatorViewModel
import com.iplonk.rpninja.R

@Composable
fun CalculatorScreen(
	viewModel: CalculatorViewModel = viewModel()
) {
	val uiState by viewModel.uiState.collectAsState()
	Box(modifier = Modifier.fillMaxSize()) {
		Column(modifier = Modifier.align(Alignment.BottomCenter)) {
			Text(
				modifier = Modifier.fillMaxWidth(),
				text = uiState.currentExpression,
				fontSize = 80.sp,
				textAlign = TextAlign.End
			)
			when (val result = uiState.result) {
				is ExpressionResult.Number -> Text(
					text = result.number,
					fontSize = 40.sp,
				)
				is ExpressionResult.InvalidExpression -> Text(
					text = stringResource(R.string.invalid_expression_message),
					fontSize = 40.sp,
				)
				is ExpressionResult.DivideByZeroError -> Text(
					text = stringResource(R.string.divide_by_zero_error_message),
					fontSize = 40.sp,
				)
				is ExpressionResult.EmptyExpression, null -> {}
			}
			CalculatorButtonGrid(
				uiActions = calculatorUiActions,
				onButtonClick = viewModel::onAction,
			)
		}
	}
}

@Composable
private fun CalculatorButtonGrid(
	uiActions: List<CalculatorUiAction>,
	onButtonClick: (CalculatorAction) -> Unit
) {
	LazyVerticalGrid(
		columns = GridCells.Fixed(count = BUTTONS_PER_ROW),
		userScrollEnabled = false,
		contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp, start = 8.dp, end = 8.dp),
		verticalArrangement = Arrangement.spacedBy(4.dp),
		horizontalArrangement = Arrangement.spacedBy(4.dp)
	) {
		items(uiActions) { uiAction ->
			CalculatorButton(
				backgroundColor = uiAction.backgroundColor,
				symbol = uiAction.symbol,
			) {
				onButtonClick(uiAction.action)
			}
		}
	}
}

@Composable
private fun CalculatorButton(
	backgroundColor: Color,
	symbol: String,
	onClick: () -> Unit
) {
	Button(
		modifier = Modifier
			.aspectRatio(1f),
		colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
		onClick = onClick
	) {
		Text(text = symbol, fontSize = 36.sp)
	}
}

const val BUTTONS_PER_ROW = 4