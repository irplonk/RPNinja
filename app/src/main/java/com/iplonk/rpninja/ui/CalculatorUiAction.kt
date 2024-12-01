package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.Operator
import com.iplonk.rpninja.ui.theme.DarkRed
import com.iplonk.rpninja.ui.theme.Green
import com.iplonk.rpninja.ui.theme.LightBlue
import com.iplonk.rpninja.ui.theme.LightGray
import com.iplonk.rpninja.ui.theme.Orange
import com.iplonk.rpninja.ui.theme.Red
import com.iplonk.rpninja.R

data class CalculatorUiAction(
	@StringRes val symbol: Int,
	val action: CalculatorAction,
	val backgroundColor: Color,
)

val calculatorUiActions = listOf(
	CalculatorUiAction(
		symbol = R.string.seven,
		action = CalculatorAction.Number(7),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.eight,
		action = CalculatorAction.Number(8),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.nine,
		action = CalculatorAction.Number(9),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.divide_symbol,
		action = CalculatorAction.Operation(Operator.DIVIDE),
		backgroundColor = Orange
	),
	CalculatorUiAction(
		symbol = R.string.four,
		action = CalculatorAction.Number(4),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.five,
		action = CalculatorAction.Number(5),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.six,
		action = CalculatorAction.Number(6),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.multiple_symbol,
		action = CalculatorAction.Operation(Operator.MULTIPLY),
		backgroundColor = Orange,
	),
	CalculatorUiAction(
		symbol = R.string.one,
		action = CalculatorAction.Number(1),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.two,
		action = CalculatorAction.Number(2),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.three,
		action = CalculatorAction.Number(3),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.divide_symbol,
		action = CalculatorAction.Operation(Operator.SUBTRACT),
		backgroundColor = Orange,
	),
	CalculatorUiAction(
		symbol = R.string.zero,
		action = CalculatorAction.Number(0),
		backgroundColor = LightGray,
	),
	CalculatorUiAction(
		symbol = R.string.space_symbol,
		action = CalculatorAction.Space,
		backgroundColor = LightBlue,
	),
	CalculatorUiAction(
		symbol = R.string.calculate_symbol,
		action = CalculatorAction.Calculate,
		backgroundColor = Green,
	),
	CalculatorUiAction(
		symbol = R.string.add_symbol,
		action = CalculatorAction.Operation(Operator.ADD),
		backgroundColor = Orange,
	),
	CalculatorUiAction(
		symbol = R.string.clear_symbol,
		action = CalculatorAction.Clear,
		backgroundColor = Red,
	),
	CalculatorUiAction(
		symbol = R.string.delete_symbol,
		action = CalculatorAction.Delete,
		backgroundColor = DarkRed,
	),
)