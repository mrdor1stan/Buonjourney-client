package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.common.DatePicker
import com.mrdor1stan.buonjourney.ui.common.InputWithLabel
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    tripId: Long,
    eventId: Long?,
    viewModel: AddEventScreenViewModel = viewModel(factory = AddEventScreenViewModel.Factory(tripId)),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    if (showDatePicker)
        DatePicker(onSuccess = {
            viewModel.updateDate(it)
            showDatePicker = false
        }, onDismiss = {
            showDatePicker = false
        },
            currentDate = state.dateTime
        )

    Column(modifier) {
        PrimaryButton(text = state.dateTime?.toShortString?.let { "Date: ${it}" }
            ?: "Set date", onClick = { showDatePicker = true }, enabled = true)

        InputWithLabel("Title") {
            TextField(state.title, onValueChange = viewModel::updateTitle)
        }
        InputWithLabel("Address") {
            TextField(state.address, onValueChange = viewModel::updateAddress)
        }
        InputWithLabel("Description") {
            TextField(state.description, onValueChange = viewModel::updateDescription)
        }

        PrimaryButton(text = stringResource(R.string.save_button_label), onClick = {
            scope.launch {
                viewModel.addEvent()
                navigateBack()
            }
        }, enabled = state.isAddButtonEnabled)
    }
}
