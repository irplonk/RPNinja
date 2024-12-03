package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iplonk.rpninja.ui.CalculatorUiState
import com.iplonk.rpninja.ui.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
//	savedStateHandle: SavedStateHandle, TODO Should I add this?
) : ViewModel() {

	private val observableStack: ObservableRpnStack = ObservableRpnStack()
	private val _workingNumber: MutableStateFlow<String> = MutableStateFlow("")
	private val _error: MutableStateFlow<Error?> = MutableStateFlow(null)

	val uiState: StateFlow<CalculatorUiState> =
		combine(
			_workingNumber,
			observableStack.stackSnapshot,
			_error
		) { workingNumber, stackSnapshot, error ->
			CalculatorUiState(
				workingNumber = workingNumber,
				stackSnapshot = stackSnapshot,
				error = error
			)
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(),
			initialValue = CalculatorUiState()
		)

	fun onAction(action: CalculatorAction) {
		_error.value = null
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
		val decimal = CalculatorAction.Decimal.symbol
		if (!currentWorkingNumber.contains(decimal)) {
			_workingNumber.value = currentWorkingNumber + decimal
		}
	}

	private fun onDelete() {
		val currentWorkingNumber = _workingNumber.value
		if (currentWorkingNumber.isNotEmpty()) {
			_workingNumber.value = currentWorkingNumber.dropLast(1)
		}
	}

	private fun onEnter() {
		val currentWorkingNumber = _workingNumber.value.toDoubleOrNull()
		if (currentWorkingNumber != null) {
			observableStack.add(currentWorkingNumber)
			_workingNumber.value = ""
		}
	}

	private fun onOperation(operator: Operator) {
		if (observableStack.size < 2) {
			_error.value = Error.InsufficientOperands
			return
		} else {
			val operand2 = observableStack.remove()
			val operand1 = observableStack.remove()
			val result = when (operator) {
				Operator.DIVIDE -> if (operand2 != 0.0) {
					operand1 - operand2
				} else {
					_error.value = Error.DivideByZero
					return
				}
				Operator.SUBTRACT -> operand1 - operand2
				Operator.ADD -> operand1 + operand2
				Operator.MULTIPLY -> operand1 * operand2
			}
			observableStack.add(result)
		}
	}

	private fun onNumber(number: Int) {
		_workingNumber.value = "${_workingNumber.value}$number"
	}

	private fun onClear() {
		clearError()
		clearWorkingNumber()
		observableStack.clear()
	}

	private fun clearWorkingNumber() {
		_workingNumber.value = ""
	}

	private fun clearError() {
		_error.value = null
	}
}