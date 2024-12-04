package com.iplonk.rpninja.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iplonk.rpninja.domain.Operator.BinaryOperator
import com.iplonk.rpninja.domain.Operator.Divide
import com.iplonk.rpninja.domain.Operator.Negate
import com.iplonk.rpninja.domain.Operator.UnaryOperator
import com.iplonk.rpninja.ui.CalculatorError
import com.iplonk.rpninja.ui.CalculatorUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Handles the business logic for the main calculator view
 *
 * @property uiState Represents the current state of the calculator UI
 */
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
		// If the number already has a decimal point, we will prevent the user from adding another.
		if (currentWorkingNumber.contains('.')) {
			_workingNumber.value = "$currentWorkingNumber."
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
			// We shouldn't ever get here since we control what the user can enter through the keypad UI,
			// but we will add this here to be safe. In a production app, we would want to send a remote log here.
			handleError(CalculatorError.InvalidNumber)
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

	private fun onOperation(operator: Operator) {
		when (operator) {
			is UnaryOperator -> handleUnaryOperation(operator)
			is BinaryOperator -> handleBinaryOperation(operator)
		}
	}

	private fun handleUnaryOperation(operator: UnaryOperator) {
		val currentWorkingNumber = _workingNumber.value
		if (operator is Negate && currentWorkingNumber.isNotBlank()) {
			toggleSign()
		} else if (observableStack.isEmpty()) {
			handleError(CalculatorError.InsufficientOperands)
		} else {
			val operand = observableStack.remove()
			val result = operator.apply(operand)
			observableStack.add(result)
		}
	}

	private fun handleBinaryOperation(operator: BinaryOperator) {
		if (observableStack.size < 2) {
			handleError(CalculatorError.InsufficientOperands)
			return
		}

		val operand2 = observableStack.remove()
		val operand1 = observableStack.remove()
		val result = operator.apply(operand1, operand2)

		if (result != null) {
			observableStack.add(result)
		} else {
			val error = when (operator) {
				is Divide -> CalculatorError.DivideByZero
				else -> CalculatorError.UnknownOperation
			}
			handleError(error)
		}
	}

	private fun toggleSign() {
		val currentWorkingNumber = _workingNumber.value
		_workingNumber.value = if (currentWorkingNumber.startsWith("-")) {
			currentWorkingNumber.drop(1)
		} else {
			"-$currentWorkingNumber"
		}
	}

	private fun handleError(error: CalculatorError) {
		_calculatorError.value = error
	}
}