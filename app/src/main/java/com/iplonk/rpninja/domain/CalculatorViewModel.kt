package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import com.iplonk.rpninja.ui.CalculatorUiState
import com.iplonk.rpninja.ui.ExpressionResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {

	private val numberStack: ArrayDeque<Int> = ArrayDeque()

	val _uiState: MutableStateFlow<CalculatorUiState> = MutableStateFlow(CalculatorUiState())
	val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

	fun onAction(action: CalculatorAction) {
		when (action) {
			is CalculatorAction.Number -> onNumber(action.number)
			is CalculatorAction.Operation -> onOperation(action.operator)
			CalculatorAction.Clear -> onClear()
			CalculatorAction.Calculate -> onCalculate()
		}
	}

	private fun onCalculate() {
		val result = numberStack.removeLastOrNull()
		if (result != null) {
			_uiState.value = _uiState.value.copy(result = ExpressionResult.Number(result.toString()))
		}
	}

	private fun onOperation(operator: Operator) {
		val number1 = numberStack.removeLastOrNull()
		val number2 = numberStack.removeLastOrNull()
		if (number1 != null && number2 != null) {
			when (operator) {
				Operator.SUBTRACT -> numberStack.add(number1 - number2)
				Operator.DIVIDE -> {
					if (number2 == 0) {
						_uiState.value.copy(result = ExpressionResult.DivideByZeroError)
					} else {
						numberStack.add(number1 / number2)
					}
				}
				Operator.ADD -> numberStack.add(number1 + number2)
				Operator.MULTIPLY -> numberStack.add(number1 * number2)
			}
		}
	}

	private fun onClear() {
		numberStack.clear()
		_uiState.value = CalculatorUiState()
	}

	private fun onNumber(number: Int) {
		numberStack.add(number)
		val currentExpression = _uiState.value.currentExpression
		_uiState.value = _uiState.value.copy(currentExpression = "$currentExpression $number")
	}
}