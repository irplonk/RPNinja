package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import com.iplonk.rpninja.ui.CalculatorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel(val rpnCalculator: RpnCalculator = RpnCalculator()) : ViewModel() {

	val _uiState: MutableStateFlow<CalculatorUiState> = MutableStateFlow(CalculatorUiState())
	val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

	fun onAction(action: CalculatorAction) {
		when (action) {
			is CalculatorAction.Number -> onNumber(action.number)
			is CalculatorAction.Operation -> onOperation(action.operator)
			CalculatorAction.Clear -> onClear()
			CalculatorAction.Space -> onSpace()
			CalculatorAction.Calculate -> onCalculate()
			CalculatorAction.Delete -> onDelete()
		}
	}

	private fun onDelete() {
		val currentExpression = _uiState.value.currentExpression
		if (currentExpression.isNotEmpty()) {
			_uiState.value = CalculatorUiState(currentExpression = currentExpression.dropLast(1))
		}
	}

	private fun onCalculate() {
		val currentExpression = _uiState.value.currentExpression
		val expressionResult =
			rpnCalculator.calculate(expression = currentExpression, delimiter = SINGLE_SPACE)
		_uiState.value = _uiState.value.copy(result = expressionResult)
	}

	private fun onSpace() {
		val currentExpression = _uiState.value.currentExpression
		val lastCharacter = currentExpression.lastOrNull()
		// We don't allow the user to keep adding whitespace
		if (lastCharacter?.isWhitespace() == false) {
			_uiState.value = _uiState.value.copy(currentExpression = currentExpression + SINGLE_SPACE)
		}
	}

	private fun onOperation(operator: Operator) {
		val currentExpression = _uiState.value.currentExpression
		_uiState.value = CalculatorUiState(currentExpression = currentExpression + operator.symbol)
	}

	private fun onClear() {
		_uiState.value = CalculatorUiState()
	}

	private fun onNumber(number: Int) {
		val currentExpression = _uiState.value.currentExpression
		_uiState.value = _uiState.value.copy(currentExpression = currentExpression + number)
	}

	companion object {
		private const val SINGLE_SPACE = ' '
	}
}