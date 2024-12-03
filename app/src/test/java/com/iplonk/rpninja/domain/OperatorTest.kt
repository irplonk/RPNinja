package com.iplonk.rpninja.domain

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class OperatorTest {

	@Test
	fun operatorsHaveExpectedSymbols() {
		assertEquals(Operator.Divide.symbol, "÷")
		assertEquals(Operator.Multiply.symbol, "x")
		assertEquals(Operator.Add.symbol, "+")
		assertEquals(Operator.Subtract.symbol, "-")
	}

	@Test
	fun multiplicationWithPositiveNumbers() {
		val operand1 = 5.0
		val operand2 = 3.0
		assertEquals(15.0, Operator.Multiply.apply(operand1, operand2))
	}

	@Test
	fun multiplicationWithNegativeNumbers() {
		val operand1 = -5.0
		val operand2 = 3.0
		assertEquals(-15.0, Operator.Multiply.apply(operand1, operand2))
	}

	@Test
	fun additionWithPositiveNumbers() {
		val operand1 = 3.0
		val operand2 = 3.0
		assertEquals(6.0, Operator.Add.apply(operand1, operand2))
	}

	@Test
	fun additionWithNegativeNumbers() {
		val operand1 = -3.0
		val operand2 = 3.0
		assertEquals(0.0, Operator.Add.apply(operand1, operand2))
	}

	@Test
	fun subtraction() {
		val operand1 = -3.0
		val operand2 = 3.0
		assertEquals(0.0, Operator.Add.apply(operand1, operand2))
	}

	@Test
	fun divisionWithZeroDivisor() {
		val operand1 = 4.0
		val operand2 = 0.0
		assertNull(Operator.Divide.apply(operand1, operand2))
	}

	@Test
	fun divisionWithNonZeroDivisor() {
		val operand1 = 4.0
		val operand2 = 2.0
		assertEquals(2.0, Operator.Divide.apply(operand1, operand2))
	}
}