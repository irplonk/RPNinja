package com.iplonk.rpninja.domain

import androidx.core.text.isDigitsOnly
import com.iplonk.rpninja.ui.ExpressionResult

class RpnCalculator {

	fun calculate(expression: String, delimiter: Char): ExpressionResult {
		val splitExpression = expression.split(delimiter)
		if (splitExpression.isEmpty()) {
			return ExpressionResult.EmptyExpression
		}
		val numberStack: ArrayDeque<Double> = ArrayDeque()
		for (value in splitExpression) {
			if (value.isDigitsOnly()) {
				numberStack.add(value.toDouble())
			} else {
				val number1 = numberStack.removeFirstOrNull()
				val number2 = numberStack.removeFirstOrNull()
				if (number1 != null && number2 != null) {
					val result = when (value) {
						Operator.SUBTRACT.symbol -> number1 - number2
						Operator.MULTIPLY.symbol -> number1 * number2
						Operator.ADD.symbol -> number1 + number2
						Operator.DIVIDE.symbol -> {
							if (number2 == 0.0) {
								return ExpressionResult.DivideByZeroError
							} else {
								number1 / number2
							}
						}
						else -> return ExpressionResult.InvalidExpression
					}
					numberStack.add(result)
				} else {
					return ExpressionResult.InvalidExpression
				}
			}
		}
		return ExpressionResult.Number(numberStack.first().toString())
	}
}