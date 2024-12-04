package com.iplonk.rpninja.domain

import app.cash.turbine.test
import com.iplonk.rpninja.ui.CalculatorError
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
// TODO Double-check that everything is covered here
class CalculatorViewModelTest {

	private lateinit var calculatorViewModel: CalculatorViewModel

	private val testDispatcher = UnconfinedTestDispatcher()

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
		calculatorViewModel = CalculatorViewModel()
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun stateInitialization() = runTest {
		calculatorViewModel.uiState.test {
			val uiState = awaitItem()

			assertEquals("", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)
		}
	}

	@Test
	fun addingSingleNumberUpdatesWorkingNumber() = runTest {
		calculatorViewModel.uiState.test {
			calculatorViewModel.onAction(CalculatorAction.Number(5))

			val uiState = expectMostRecentItem()

			assertEquals("5", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)
		}
	}

	@Test
	fun addingMultipleNumbersUpdatesWorkingNumber() = runTest {
		calculatorViewModel.uiState.test {
			calculatorViewModel.onAction(CalculatorAction.Number(5))
			calculatorViewModel.onAction(CalculatorAction.Number(3))

			val uiState = expectMostRecentItem()

			assertEquals("53", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)
		}
	}

	@Test
	fun addingDecimalUpdatesWorkingNumber() = runTest {
		calculatorViewModel.uiState.test {
			// Check that the first decimal gets appended to the working number
			calculatorViewModel.onAction(CalculatorAction.Number(5))
			calculatorViewModel.onAction(CalculatorAction.Decimal)

			val uiState = expectMostRecentItem()

			assertEquals("5.", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)

			// Check that another decimal cannot be added
			calculatorViewModel.onAction(CalculatorAction.Decimal)

			expectNoEvents()
		}
	}

	@Test
	fun enteringValidNumberUpdatesStackAndClearsWorkingNumber() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(5)

			val uiState = expectMostRecentItem()

			assertEquals("", uiState.workingNumber)
			assertEquals(listOf(5.0), uiState.stackSnapshot)
		}
	}

	@Test
	fun performingAdditionUpdatesState() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(5)
			enterNumber(3)
			// Simulating the addition operator being pressed
			calculatorViewModel.onAction(CalculatorAction.Operation(Add))

			val uiState = expectMostRecentItem()

			assertEquals(listOf(8.0), uiState.stackSnapshot)
		}
	}

	@Test
	fun performingExponentiationUpdatesState() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(5)
			// Simulating the exponentiation operator being pressed
			calculatorViewModel.onAction(CalculatorAction.Operation(Exponentiate))

			val uiState = expectMostRecentItem()

			assertEquals(listOf(25.0), uiState.stackSnapshot)
		}
	}

	@Test
	fun performingNegationUpdatesState() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(5)
			// Simulating the negation operator being pressed
			calculatorViewModel.onAction(CalculatorAction.Operation(Negate))

			var uiState = expectMostRecentItem()

			assertEquals(listOf(-5.0), uiState.stackSnapshot)

			// Simulating the negation operator being pressed again
			calculatorViewModel.onAction(CalculatorAction.Operation(Negate))

			uiState = expectMostRecentItem()

			assertEquals(listOf(5.0), uiState.stackSnapshot)
		}
	}

	@Test
	fun dividingByZeroUpdatesState() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(5)
			enterNumber(0)
			calculatorViewModel.onAction(CalculatorAction.Operation(Divide))

			val uiState = expectMostRecentItem()

			assertNotNull(uiState.calculatorError)
			assertEquals(CalculatorError.DivideByZero, uiState.calculatorError)
		}
	}

	@Test
	fun performingAUnaryOperationWithoutOperands() = runTest {
		calculatorViewModel.uiState.test {
			calculatorViewModel.onAction(CalculatorAction.Operation(Negate))

			val uiState = expectMostRecentItem()

			assertNotNull(uiState.calculatorError)
			assertEquals(
				CalculatorError.InsufficientOperands,
				uiState.calculatorError
			)
		}
	}

	@Test
	fun performingABinaryOperationWithoutOperands() = runTest {
		calculatorViewModel.uiState.test {
			calculatorViewModel.onAction(CalculatorAction.Operation(Add))

			val uiState = expectMostRecentItem()

			assertNotNull(uiState.calculatorError)
			assertEquals(
				CalculatorError.InsufficientOperands,
				uiState.calculatorError
			)
		}
	}

	@Test
	fun performingABinaryOperationsWithOneOperand() = runTest {
		calculatorViewModel.uiState.test {
			enterNumber(4)
			calculatorViewModel.onAction(CalculatorAction.Operation(Add))

			val uiState = expectMostRecentItem()

			assertNotNull(uiState.calculatorError)
			assertEquals(
				CalculatorError.InsufficientOperands,
				uiState.calculatorError
			)
		}
	}

	@Test
	fun clearResetsState() = runTest {
		calculatorViewModel.uiState.test {
			// Put the number 5 onto the stack
			enterNumber(5)
			// Input the number 4 but do not put into onto the stack
			calculatorViewModel.onAction(CalculatorAction.Number(4))
			// Try to subtract
			calculatorViewModel.onAction(CalculatorAction.Operation(Subtract))
			var uiState = expectMostRecentItem()

			// Verify expected state before clearing
			assertEquals("4", uiState.workingNumber)
			assertEquals(listOf(5.0), uiState.stackSnapshot)
			assertEquals(
				CalculatorError.InsufficientOperands,
				uiState.calculatorError
			)

			calculatorViewModel.onAction(CalculatorAction.AllClear)

			uiState = expectMostRecentItem()

			// Verify that state has been reset
			assertEquals("", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)
		}
	}

	@Test
	fun deleteRemovesLastCharacterOfWorkingNumber() = runTest {
		calculatorViewModel.uiState.test {
			// Simulating the number 12 being inputted
			calculatorViewModel.onAction(CalculatorAction.Number(1))
			calculatorViewModel.onAction(CalculatorAction.Number(2))

			var uiState = expectMostRecentItem()

			// Verify the state before the backspace
			assertEquals("12", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)

			// Simulating the backspace being pressed
			calculatorViewModel.onAction(CalculatorAction.DeleteLastCharacter)

			uiState = expectMostRecentItem()

			assertEquals("1", uiState.workingNumber)
			assertEquals(emptyList<Double>(), uiState.stackSnapshot)
			assertNull(uiState.calculatorError)
		}
	}

	private fun enterNumber(number: Int) {
		require(number in 0..9)
		calculatorViewModel.onAction(CalculatorAction.Number(number))
		calculatorViewModel.onAction(CalculatorAction.Enter)
	}
}