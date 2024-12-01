package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.iplonk.rpninja.R
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.Operator
import com.iplonk.rpninja.ui.theme.LightGray
import com.iplonk.rpninja.ui.theme.Orange

sealed interface CalculatorUiAction {
	val action: CalculatorAction
	val backgroundColor: Color

	data class NumberPadUiAction(
		@StringRes val text: Int? = null,
		override val action: CalculatorAction,
		val content: @Composable () -> Unit = {},
	) : CalculatorUiAction {
		override val backgroundColor: Color = LightGray
	}

	data class ArithmeticOperatorAction(
		@StringRes val text: Int,
		override val action: CalculatorAction.Operation,
	) : CalculatorUiAction {
		override val backgroundColor: Color = Orange
	}
}

val arithmeticOperationActions = listOf(
	CalculatorUiAction.ArithmeticOperatorAction(
		text = R.string.divide,
		action = CalculatorAction.Operation(Operator.DIVIDE),
	),
	CalculatorUiAction.ArithmeticOperatorAction(
		text = R.string.multiple,
		action = CalculatorAction.Operation(Operator.MULTIPLY),
	),
	CalculatorUiAction.ArithmeticOperatorAction(
		text = R.string.subtract,
		action = CalculatorAction.Operation(Operator.SUBTRACT),
	),
	CalculatorUiAction.ArithmeticOperatorAction(
		text = R.string.add,
		action = CalculatorAction.Operation(Operator.ADD),
	)
)

val remainingActions = listOf(

)


val numberUiActions = listOf(
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.seven,
		action = CalculatorAction.Number(7),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.eight,
		action = CalculatorAction.Number(8),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.nine,
		action = CalculatorAction.Number(9),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.four,
		action = CalculatorAction.Number(4),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.five,
		action = CalculatorAction.Number(5),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.six,
		action = CalculatorAction.Number(6),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.one,
		action = CalculatorAction.Number(1),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.two,
		action = CalculatorAction.Number(2),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.three,
		action = CalculatorAction.Number(3),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.zero,
		action = CalculatorAction.Number(0),
	),
	CalculatorUiAction.NumberPadUiAction(
		text = R.string.decimal,
		action = CalculatorAction.Decimal,
	),
	CalculatorUiAction.NumberPadUiAction(
		text = null,
		action = CalculatorAction.Delete,
		content = {
			Icon(
				painter = painterResource(R.drawable.baseline_backspace_24),
				contentDescription = stringResource(R.string.delete_content_description),
			)
		}
	),
)