package com.iplonk.rpninja.domain

import com.iplonk.rpninja.ui.CalculatorUiState
import com.iplonk.rpninja.ui.ExpressionResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculatorViewModelTest {
    private val rpnCalculator = mockk<RpnCalculator>()
    private val viewModel = CalculatorViewModel(rpnCalculator)

    @Test
    fun init_uiStateIsEmpty() {
        assertEquals(CalculatorUiState(currentExpression = "", result = null), viewModel.uiState.value)
    }

    @Test
    fun onCalculate_operation_addsOperator() {
        viewModel.onAction(CalculatorAction.Operation(Operator.ADD))
        assertEquals(
            CalculatorUiState(currentExpression = "+", result = null),
            viewModel.uiState.value,
        )
    }

    @Test
    fun onCalculate_number_addsNumber() {
        viewModel.onAction(CalculatorAction.Number(0))
        assertEquals(
            CalculatorUiState(currentExpression = "0", result = null),
            viewModel.uiState.value,
        )
    }

    @Test
    fun onCalculate_space_addsSpaceOnlyBetweenOperands() {
        // When there are no other characters, we don't add a space.
        viewModel.onAction(CalculatorAction.Space)
        assertEquals(
            CalculatorUiState(currentExpression = "", result = null),
            viewModel.uiState.value,
        )

        // When there are other characters, we can add a space.
        setUpExpression(operand1 = 1, operand2 = 1, operator = Operator.SUBTRACT)

        viewModel.onAction(CalculatorAction.Space)
        assertEquals(
            CalculatorUiState(currentExpression = "1 1 -", result = null),
            viewModel.uiState.value,
        )

        // When we already added a space, we cannot add another.
        viewModel.onAction(CalculatorAction.Space)
        assertEquals(
            CalculatorUiState(currentExpression = "1 1 -", result = null),
            viewModel.uiState.value,
        )
    }

    @Test
    fun onAction_delete_lastCharacterIsDeleted() {
        setUpExpression(operand1 = 3, operand2 = 4, operator = Operator.ADD)
        viewModel.onAction(CalculatorAction.AllClear)
        assertEquals(
            CalculatorUiState(currentExpression = "3 4 ", result = null),
            viewModel.uiState.value,
        )
    }

    @Test
    fun onAction_calculate_callsRpnCalculatorAndUpdatesState() {
        val expectedExpression = "4 5 -"
        val expectedExpressionResult = ExpressionResult.Number(-1.0)
        every { rpnCalculator.calculate(expectedExpression, ' ') } returns expectedExpressionResult
        setUpExpression(operand1 = 4, operand2 = 5, operator = Operator.SUBTRACT)

        viewModel.onAction(CalculatorAction.Enter)

        verify { rpnCalculator.calculate(expectedExpression, ' ') }
        assertEquals(
            CalculatorUiState(expectedExpression, expectedExpressionResult),
            viewModel.uiState.value,
        )
    }

    @Test
    fun onAction_clear_uiStateIsEmpty() {
        setUpExpression(operand1 = 3, operand2 = 4, operator = Operator.ADD)
        viewModel.onAction(CalculatorAction.AllClear)
        assertEquals(CalculatorUiState(currentExpression = "", result = null), viewModel.uiState.value)
    }

    private fun setUpExpression(
        operand1: Int,
        operand2: Int? = null,
        operator: Operator? = null,
    ) {
        var expression = operand1.toString()
        viewModel.onAction(CalculatorAction.Number(operand1))

        if (operand2 != null) {
            viewModel.onAction(CalculatorAction.Space)
            viewModel.onAction(CalculatorAction.Number(operand2))
            expression += " $operand2"
        }

        if (operator != null) {
            viewModel.onAction(CalculatorAction.Space)
            viewModel.onAction(CalculatorAction.Operation(operator))
            expression += " ${operator.symbol}"
        }

        val uiState = viewModel.uiState.value
        assertEquals(expression, uiState.currentExpression)
    }
}
