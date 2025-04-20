package com.mrdor1stan.buonjourney.ui.screens.places.newone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.Dropdown
import com.mrdor1stan.buonjourney.ui.common.InputWithLabel
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCityScreen(
    cityId: String?,
    placeLink: String?,
    viewModel: AddCityScreenViewModel = viewModel(
        factory = AddCityScreenViewModel.Factory(
            cityId = cityId,
            placeLink = placeLink
        )
    ),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Column(modifier) {
        InputWithLabel(label = "Country") {
            Dropdown(
                inputValue = state.selectedCountry,
                options = state.countriesList,
                getDisplayString = { "${it.flag ?: " "} ${it.name}" }) {
                viewModel.updateCountry(it.name)
            }
        }
        InputWithLabel(label = "City") {
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
