package com.iplonk.rpninja.domain

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ObservableRpnStackTest {

	private val stack = ObservableRpnStack()

	@Test
	fun stackInitialization() {
		assertEquals(0, stack.size)
		assertEquals(emptyList<Double>(), stack.stackSnapshot.value)
		assertTrue(stack.isEmpty())
	}

	@Test
	fun addSingleElementToStack() {
		stack.add(1.0)
		assertEquals(1, stack.size)
		assertEquals(listOf(1.0), stack.stackSnapshot.value)
		assertFalse(stack.isEmpty())
	}

	@Test
	fun addMultipleElementsToStack() {
		stack.add(1.0)
		stack.add(2.0)
		stack.add(3.0)
		assertEquals(3, stack.size)
		assertEquals(listOf(1.0, 2.0, 3.0), stack.stackSnapshot.value)
		assertFalse(stack.isEmpty())
	}

	@Test
	fun snapshotLimitDoesNotExceedMaxSize() {
		stack.add(1.0)
		stack.add(2.0)
		stack.add(3.0)
		stack.add(4.0)
		assertEquals(4, stack.size)
		assertEquals(listOf(2.0, 3.0, 4.0), stack.stackSnapshot.value)
		assertFalse(stack.isEmpty())
	}

	@Test
	fun removeElementsFromStack() {
		stack.add(1.0)
		stack.add(2.0)
		stack.add(3.0)
		stack.remove()
		assertEquals(2, stack.size)
		assertEquals(listOf(1.0, 2.0), stack.stackSnapshot.value)
		assertFalse(stack.isEmpty())

		stack.remove()
		stack.remove()
		assertEquals(0, stack.size)
		assertEquals(emptyList<Double>(), stack.stackSnapshot.value)
		assertTrue(stack.isEmpty())
	}

	@Test(expected = NoSuchElementException::class)
	fun removeFromEmptyStack() {
		stack.remove()
	}

	@Test
	fun clearStack() {
		stack.add(1.0)
		stack.add(2.0)
		stack.add(3.0)

		stack.clear()
		assertEquals(0, stack.size)
		assertEquals(emptyList<Double>(), stack.stackSnapshot.value)
		assertTrue(stack.isEmpty())
	}

	@Test
	fun snapshotChangesAsElementsAreAddedAndRemoved() {
		stack.add(1.0)
		stack.add(2.0)
		stack.add(3.0)
		assertEquals(listOf(1.0, 2.0, 3.0), stack.stackSnapshot.value)

		stack.remove()
		assertEquals(listOf(1.0, 2.0), stack.stackSnapshot.value)

		stack.add(4.0)
		assertEquals(listOf(1.0, 2.0, 4.0), stack.stackSnapshot.value)
	}
}