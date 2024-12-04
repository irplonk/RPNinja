package com.iplonk.rpninja.domain

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class OperatorTest {

	@Test
	fun multiplicationWithPositiveNumbers() {
		val operand1 = 5.0
		val operand2 = 3.0
		assertEquals(15.0, Multiply.apply(operand1, operand2))
	}

	@Test
	fun multiplicationWithNegativeNumbers() {
		val operand1 = -5.0
		val operand2 = 3.0
		assertEquals(-15.0, Multiply.apply(operand1, operand2))
	}

	@Test
	fun additionWithPositiveNumbers() {
		val operand1 = 3.0
		val operand2 = 3.0
		assertEquals(6.0, Add.apply(operand1, operand2))
	}

	@Test
	fun additionWithNegativeNumbers() {
		val operand1 = -3.0
		val operand2 = 3.0
		assertEquals(0.0, Add.apply(operand1, operand2))
	}

	@Test
	fun subtraction() {
		val operand1 = -3.0
		val operand2 = 3.0
		assertEquals(0.0, Add.apply(operand1, operand2))
	}

	@Test
	fun exponentiationWithPositiveNumbers() {
		val operand1 = 3.0
		val operand2 = 3.0
		assertEquals(27.0, Exponentiate.apply(operand1, operand2))
	}

	@Test
	fun exponentiationWithNegativeBase() {
		val operand1 = -3.0
		val operand2 = 2.0
		assertEquals(9.0, Exponentiate.apply(operand1, operand2))

		val operand3 = 3.0
		assertEquals(-27.0, Exponentiate.apply(operand1, operand3))
	}

	@Test
	fun exponentiationWithNegativeExponent() {
		val operand1 = 2.0
		val operand2 = -4.0
		assertEquals(0.0625, Exponentiate.apply(operand1, operand2))
	}

	@Test
	fun negationWithPositiveNumber() {
		val operand = 1.0
		assertEquals(-1.0, Negate.apply(operand))
	}

	@Test
	fun negationWithNegativeNumber() {
		val operand = -8.0
		assertEquals(8.0, Negate.apply(operand))
	}

	@Test
	fun divisionWithZeroDivisor() {
		val operand1 = 4.0
		val operand2 = 0.0
		assertNull(Divide.apply(operand1, operand2))
	}

	@Test
	fun divisionWithNonZeroDivisor() {
		val operand1 = 4.0
		val operand2 = 2.0
		assertEquals(2.0, Divide.apply(operand1, operand2))
	}
}