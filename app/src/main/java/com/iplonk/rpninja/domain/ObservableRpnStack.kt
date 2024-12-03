package com.iplonk.rpninja.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ObservableRpnStack {

	private val stack = ArrayDeque<Double>()
	private val _stackSnapshot = MutableStateFlow<List<Double>>(emptyList())

	val stackSnapshot: StateFlow<List<Double>> = _stackSnapshot

	val size: Int
		get() = stack.size

	fun add(element: Double): Boolean {
		val wasAdded = stack.add(element)
		updateStackSnapshot()
		return wasAdded
	}

	fun remove(): Double {
		val removedElement = stack.removeLast()
		updateStackSnapshot()
		return removedElement
	}

	fun clear() {
		stack.clear()
		updateStackSnapshot()
	}

	private fun updateStackSnapshot() {
		_stackSnapshot.value = stack.takeLast(MAX_STACK_SNAPSHOT_SIZE)
	}

	companion object {
		const val MAX_STACK_SNAPSHOT_SIZE = 3
	}
}