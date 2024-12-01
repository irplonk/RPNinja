package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import com.iplonk.rpninja.ui.CalculatorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(private val calculator: RpnCalculator) : ViewModel() {

	private val _uiState: MutableStateFlow<CalculatorUiState> = MutableStateFlow(CalculatorUiState())
	val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

	fun onAction(action: CalculatorAction) {
		when (action) {
			is CalculatorAction.Number -> onNumber(action.number)
			is CalculatorAction.Operation -> onOperation(action.operator)
			CalculatorAction.Decimal -> onDecimal()
			CalculatorAction.Clear -> onClear()
			CalculatorAction.Space -> onSpace()
			CalculatorAction.Calculate -> onCalculate()
			CalculatorAction.Delete -> onDelete()
		}
	}

	private fun onDecimal() {
		val currentExpression = _uiState.value.currentExpression
		val lastCharacter = currentExpression.lastOrNull()
		if (lastCharacter != ',') {
			_uiState.value = _uiState.value.copy(currentExpression = "$currentExpression,")
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
			calculator.calculate(expression = currentExpression, delimiter = SINGLE_SPACE)
		_uiState.value = _uiState.value.copy(result = expressionResult)
	}

	private fun onSpace() {
		val currentExpression = _uiState.value.currentExpression
		val lastCharacter = currentExpression.lastOrNull()
		// We only allow the user to add whitespace between operands.
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