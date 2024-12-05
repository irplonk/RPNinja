package com.iplonk.rpninja.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iplonk.rpninja.domain.CalculatorViewModel

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel = viewModel()) {
	val uiState by viewModel.uiState.collectAsState()
	CalculatorScreen(uiState = uiState, onCalculatorButtonClick = viewModel::onAction)
}