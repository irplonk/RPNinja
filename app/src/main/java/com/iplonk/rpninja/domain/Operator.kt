package com.iplonk.rpninja.domain

/**
 * Represents all of the available operators that can be applied on two or more numbers
 */
enum class Operator(val symbol: Char) {
	DIVIDE('/'),
	MULTIPLY('x'),
	SUBTRACT('-'),
	ADD('+'),
}