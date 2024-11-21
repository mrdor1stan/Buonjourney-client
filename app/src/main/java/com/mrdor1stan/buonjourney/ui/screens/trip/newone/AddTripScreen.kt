package com.mrdor1stan.buonjourney.ui.screens.trip.newone

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.data.db.PlaceDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.DatePicker
import com.mrdor1stan.buonjourney.ui.common.Dropdown
import com.mrdor1stan.buonjourney.ui.common.InputWithLabel
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripScreen(
    viewModel: AddTripScreenViewModel = viewModel(factory = AddTripScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    var showStartDatePicker by remember {
        mutableStateOf(false)
    }
    var showEndDatePicker by remember {
        mutableStateOf(false)
    }

    if (showStartDatePicker)
        DatePicker(onSuccess = {
            viewModel.updateStartDate(it)
            showStartDatePicker = false
        }, onDismiss = {
            showStartDatePicker = false
        },
            maxDate = state.endDate,
            currentDate = state.startDate ?: state.endDate
        )

    if (showEndDatePicker) DatePicker(onSuccess = {
        viewModel.updateEndDate(it)
        showEndDatePicker = false
    }, onDismiss = {
        showEndDatePicker = false
    },
        minDate = state.startDate,
        currentDate = state.endDate ?: state.startDate
    )

    Column {
        PrimaryButton(text = state.startDate?.toShortString?.let { "Start date: ${it}" }
            ?: "Set start date", onClick = { showStartDatePicker = true }, enabled = true)
        PrimaryButton(text = state.endDate?.toShortString?.let { "End date: ${it}" }
            ?: "End start date", onClick = { showEndDatePicker = true }, enabled = true)

        InputWithLabel("Destination") {
            Dropdown(state.destination?.name ?: "", state.allPlaces, { it: PlaceDto -> it.name }, {
                viewModel.updateDestination(it)
            })
        }
        InputWithLabel("Title") {
            TextField(state.title, onValueChange = viewModel::updateTitle)
        }
        InputWithLabel("Status") {
            Dropdown(state.status.name,
                TripDto.TripStatus.entries,
                { it: TripDto.TripStatus -> it.name },
                {
                    viewModel.updateStatus(it)
                })
        }
        PrimaryButton(text = "Add", onClick = {
            scope.launch {
                viewModel.addTrip()
                navigateBack()
            }
        }, enabled = state.isAddButtonEnabled)
    }
}
