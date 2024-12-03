package com.iplonk.rpninja.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.iplonk.rpninja.R
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.Operator
import com.iplonk.rpninja.ui.theme.Green
import com.iplonk.rpninja.ui.theme.LightGray
import com.iplonk.rpninja.ui.theme.Orange
import com.iplonk.rpninja.ui.theme.Red

data class CalculatorButton(
	val action: CalculatorAction,
	val backgroundColor: Color,
	val content: @Composable () -> Unit = {
		Text(
			text = action.symbol,
			fontSize = 36.sp,
		)
	},
)

val calculatorButtons = listOf(
	CalculatorButton(
		action = CalculatorAction.Number(7),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(8),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(9),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Operation(Operator.DIVIDE),
		backgroundColor = Orange
	),
	CalculatorButton(
		action = CalculatorAction.Number(4),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(5),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(6),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Operation(Operator.MULTIPLY),
		backgroundColor = Orange,
	),
	CalculatorButton(
		action = CalculatorAction.Number(1),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(2),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Number(3),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Operation(Operator.SUBTRACT),
		backgroundColor = Orange,
	),
	CalculatorButton(
		action = CalculatorAction.Number(0),
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.Decimal,
		backgroundColor = LightGray,
	),
	CalculatorButton(
		action = CalculatorAction.DeleteLastCharacter,
		backgroundColor = LightGray,
		content = {
			Icon(
				painter = painterResource(R.drawable.baseline_backspace_24),
				contentDescription = stringResource(R.string.delete_content_description),
			)
		}
	),
	CalculatorButton(
		action = CalculatorAction.Operation(Operator.ADD),
		backgroundColor = Orange,
	),
	CalculatorButton(
		action = CalculatorAction.AllClear,
		backgroundColor = Red,
	),
	CalculatorButton(
		action = CalculatorAction.Enter,
		backgroundColor = Green,
		content = {
			Icon(
				painter = painterResource(R.drawable.baseline_keyboard_return_24),
				contentDescription = stringResource(R.string.enter_content_description),
			)
		}
	),
)