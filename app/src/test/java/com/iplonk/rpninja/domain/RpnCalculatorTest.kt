package com.iplonk.rpninja.domain

import com.iplonk.rpninja.ui.ExpressionResult
import org.junit.Assert.assertEquals
import org.junit.Test

class RpnCalculatorTest {
    private val calculator = RpnCalculator()

    @Test
    fun simpleValidExpressions_returnsCorrectNumber() {
        assertEquals(ExpressionResult.Number(7.0), calculator.calculate("3 4 +", delimiter = ' '))
        assertEquals(ExpressionResult.Number(8.0), calculator.calculate("10 2 -", delimiter = ' '))
        assertEquals(ExpressionResult.Number(6.0), calculator.calculate("2 3 /", delimiter = ' '))
        assertEquals(ExpressionResult.Number(4.0), calculator.calculate("8 2 ÷", delimiter = ' '))
    }

    @Test
    fun complexValidExpressions_returnsCorrectNumber() {
        assertEquals(
            ExpressionResult.Number(14.0),
            calculator.calculate("5 1 2 + 4 x + 3 -", delimiter = ' '),
        )
        assertEquals(
            ExpressionResult.Number(12.5),
            calculator.calculate("2 3 + 5 x 6 4 - ÷", delimiter = ' '),
        )
    }

    @Test
    fun singleNumericalOperand_returnsCorrectNumber() {
        assertEquals(ExpressionResult.Number(42.0), calculator.calculate("42", delimiter = ' '))
    }

    @Test
    fun emptyOperand_returnsEmptyExpression() {
        assertEquals(ExpressionResult.EmptyExpression, calculator.calculate("", delimiter = ' '))
        assertEquals(ExpressionResult.EmptyExpression, calculator.calculate(" ", delimiter = ' '))
    }

    @Test
    fun missingOperands_returnsInvalidExpression() {
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("+", delimiter = ' '))
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("8 ÷", delimiter = ' '))
    }

    @Test
    fun extraOperands_returnsInvalidExpression() {
        assertEquals(
            ExpressionResult.InvalidExpression,
            calculator.calculate("1 2 3 +", delimiter = ' '),
        )
    }

    @Test
    fun divideByZero_returnsDivideByZeroError() {
        assertEquals(ExpressionResult.DivideByZeroError, calculator.calculate("4 0 ÷", delimiter = ' '))
    }

    @Test
    fun invalidCharacters_returnsInvalidExpression() {
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("4 0 %", delimiter = ' '))
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("4 a ÷", delimiter = ' '))
    }

    @Test
    fun otherDelimiters_returnsExpectedResult() {
        assertEquals(ExpressionResult.Number(9.0), calculator.calculate("4,5,+", delimiter = ','))
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("4,5 +", delimiter = ','))
        assertEquals(ExpressionResult.InvalidExpression, calculator.calculate("4,+", delimiter = ','))
    }
}
