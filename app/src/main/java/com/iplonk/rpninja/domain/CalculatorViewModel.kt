package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iplonk.rpninja.ui.CalculatorUiState
import com.iplonk.rpninja.ui.CalculatorError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

	private val observableStack = ObservableRpnStack()
	private val _workingNumber = MutableStateFlow("")
	private val _calculatorError = MutableStateFlow<CalculatorError?>(null)

	val uiState: StateFlow<CalculatorUiState> =
		combine(
			_workingNumber,
			observableStack.stackSnapshot,
			_calculatorError
		) { workingNumber, stackSnapshot, calculatorError ->
			CalculatorUiState(
				workingNumber = workingNumber,
				stackSnapshot = stackSnapshot,
				calculatorError = calculatorError
			)
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(),
			initialValue = CalculatorUiState()
		)

	fun onAction(action: CalculatorAction) {
		// When the user presses any key after receiving an error message, we will
		// clear it out, so they can try again.
		_calculatorError.value = null

		when (action) {
			is CalculatorAction.Number -> onNumber(action.number)
			is CalculatorAction.Operation -> onOperation(action.operator)
			CalculatorAction.Decimal -> onDecimal()
			CalculatorAction.AllClear -> onClear()
			CalculatorAction.Enter -> onEnter()
			CalculatorAction.DeleteLastCharacter -> onDelete()
		}
	}

	private fun onDecimal() {
		val currentWorkingNumber = _workingNumber.value
		if (!currentWorkingNumber.contains(CalculatorAction.Decimal.symbol)) {
			_workingNumber.value = currentWorkingNumber + CalculatorAction.Decimal.symbol
		}
	}

	private fun onDelete() {
		_workingNumber.value = _workingNumber.value.dropLast(1)
	}

	private fun onEnter() {
		val currentWorkingNumber = _workingNumber.value.toDoubleOrNull()
		if (currentWorkingNumber != null) {
			observableStack.add(currentWorkingNumber)
			_workingNumber.value = ""
		} else {
			_calculatorError.value = CalculatorError.UnknownOperation
		}
	}

	private fun onOperation(operator: Operator) {
		if (observableStack.size < 2) {
			_calculatorError.value = CalculatorError.InsufficientOperands
			return
		}
		val operand2 = observableStack.remove()
		val operand1 = observableStack.remove()

		val result = operator.apply(operand1, operand2)
		result?.let {
			observableStack.add(it)
		} ?: run {
			_calculatorError.value = when (operator) {
				Operator.Divide -> CalculatorError.DivideByZero
				else -> CalculatorError.UnknownOperation
			}
		}
	}

	private fun onNumber(number: Int) {
		_workingNumber.value += number
	}

	private fun onClear() {
		_calculatorError.value = null
		_workingNumber.value = ""
		observableStack.clear()
	}
}