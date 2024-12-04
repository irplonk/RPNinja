package com.iplonk.rpninja.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Represents the stack of numbers for the current RPN calculation
 */
class ObservableRpnStack {

	private val stack = ArrayDeque<Double>()
	private val _stackSnapshot = MutableStateFlow<List<Double>>(emptyList())

	/**
	 * The last three elements added to the stack from least recently added to most recently added
	 * e.g.
	 * stack.add(1)
	 * stack.add(2)
	 * stack.add(3)
	 * would result in a [stackSnapshot] of [1.0, 2.0, 3.0]
	 */
	val stackSnapshot: StateFlow<List<Double>> = _stackSnapshot

	/**
	 * Returns the size of the stack
	 */
	val size: Int
		get() = stack.size

	/**
	 * Adds a number onto the stack
	 */
	fun add(element: Double): Boolean {
		val wasAdded = stack.add(element)
		updateStackSnapshot()
		return wasAdded
	}

	/**
	 * Takes a peak the last added number without removing it
	 */
	fun peak(): Double? {
		return stack.lastOrNull()
	}


	/**
	 * Removes the last added number from the stack
	 */
	fun remove(): Double {
		val removedElement = stack.removeLast()
		updateStackSnapshot()
		return removedElement
	}

	/**
	 * Removes all numbers from the stack
	 */
	fun clear() {
		stack.clear()
		updateStackSnapshot()
	}

	/**
	 * Returns true is the stack is empty. Otherwise, false.
	 */
	fun isEmpty() = stack.isEmpty()

	private fun updateStackSnapshot() {
		_stackSnapshot.value = stack.takeLast(MAX_STACK_SNAPSHOT_SIZE)
	}

	companion object {
		internal const val MAX_STACK_SNAPSHOT_SIZE = 3
	}
}