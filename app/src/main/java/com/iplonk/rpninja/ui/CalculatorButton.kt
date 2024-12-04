package com.iplonk.rpninja.ui

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.iplonk.rpninja.R
import com.iplonk.rpninja.domain.Operator.Add
import com.iplonk.rpninja.domain.CalculatorAction
import com.iplonk.rpninja.domain.Operator.Divide
import com.iplonk.rpninja.domain.Operator.Exponentiate
import com.iplonk.rpninja.domain.Operator.Multiply
import com.iplonk.rpninja.domain.Operator.Negate
import com.iplonk.rpninja.domain.Operator.Subtract

/**
 * Represents the information needs to display a calculator button on the main calculator keypad
 *
 * @property text If exists, the button will contain this text
 * @property action The action that is taken when the user clicks on the button
 * @property content Used for displaying icons instead of text within the button
 */
data class CalculatorButton(
	@StringRes val text: Int? = null,
	val action: CalculatorAction,
	val content: @Composable () -> Unit = {},
)

/**
 * List of the calculator buttons that appear on the screen. Order in the list represents the order
 * in which they are displayed from left to right, top to bottom.
 */
val calculatorButtons = listOf(
	CalculatorButton(
		text = R.string.seven,
		action = CalculatorAction.Number(7),
	),
	CalculatorButton(
		text = R.string.eight,
		action = CalculatorAction.Number(8),
	),
	CalculatorButton(
		text = R.string.nine,
		action = CalculatorAction.Number(9),
	),
	CalculatorButton(
		text = R.string.divide,
		action = CalculatorAction.Operation(Divide),
	),
	CalculatorButton(
		text = R.string.four,
		action = CalculatorAction.Number(4),
	),
	CalculatorButton(
		text = R.string.five,
		action = CalculatorAction.Number(5),
	),
	CalculatorButton(
		text = R.string.six,
		action = CalculatorAction.Number(6),
	),
	CalculatorButton(
		text = R.string.multiply,
		action = CalculatorAction.Operation(Multiply),
	),
	CalculatorButton(
		text = R.string.one,
		action = CalculatorAction.Number(1),
	),
	CalculatorButton(
		text = R.string.two,
		action = CalculatorAction.Number(2),
	),
	CalculatorButton(
		text = R.string.three,
		action = CalculatorAction.Number(3),
	),
	CalculatorButton(
		text = R.string.subtract,
		action = CalculatorAction.Operation(Subtract),
	),
	CalculatorButton(
		text = R.string.zero,
		action = CalculatorAction.Number(0),
	),
	CalculatorButton(
		R.string.decimal,
		action = CalculatorAction.Decimal,
	),
	CalculatorButton(
		action = CalculatorAction.DeleteLastCharacter,
		content = {
			Icon(
				painter = painterResource(R.drawable.baseline_backspace_24),
				contentDescription = stringResource(R.string.delete_content_description),
			)
		}
	),
	CalculatorButton(
		text = R.string.add,
		action = CalculatorAction.Operation(Add),
	),
	CalculatorButton(
		text = R.string.all_clear,
		action = CalculatorAction.AllClear,
	),
	CalculatorButton(
		action = CalculatorAction.Enter,
		content = {
			Icon(
				painter = painterResource(R.drawable.baseline_keyboard_return_24),
				contentDescription = stringResource(R.string.enter_content_description),
			)
		}
	),
	CalculatorButton(
		text = R.string.exponentiate,
		action = CalculatorAction.Operation(Exponentiate),
	),
	CalculatorButton(
		text = R.string.negate,
		action = CalculatorAction.Operation(Negate),
	)
)