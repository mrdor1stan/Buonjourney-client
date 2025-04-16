package com.mrdor1stan.buonjourney.ui.screens.places.newone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.InputWithLabel
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityScreen(
    viewModel: AddCityScreenViewModel = viewModel(factory = AddCityScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Column(modifier) {
        InputWithLabel(label = "City name") {
            TextField(state.title, onValueChange = viewModel::updateTitle)
        }
        PrimaryButton("Add", enabled = state.isAddButtonEnabled, onClick = {
            scope.launch {
                viewModel.addCity()
            }.invokeOnCompletion {
                navigateBack()
            }
        })
    }
}
