package com.mrdor1stan.buonjourney.ui.screens.trip.newone

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.data.db.TripDto.TripStatus
import com.mrdor1stan.buonjourney.ui.common.DatePicker
import com.mrdor1stan.buonjourney.ui.common.ListHeader
import com.mrdor1stan.buonjourney.ui.common.TripElement
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripScreen(
    viewModel: AddTripScreenViewModel = viewModel(factory = AddTripScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showStartDatePicker by remember {
        mutableStateOf(false)
    }
    var showEndDatePicker by remember {
        mutableStateOf(false)
    }

    if(showStartDatePicker)
    DatePicker(onSuccess = {
        viewModel.updateStartDate(it)
        showStartDatePicker = false
    }, onDismiss = {
        showStartDatePicker = false
    })

    if(showEndDatePicker)
        DatePicker(onSuccess = {
            viewModel.updateEndDate(it)
            showEndDatePicker = false
        }, onDismiss = {
            showEndDatePicker = false
        })

    Column {
        TextField( state.title, onValueChange = viewModel::updateTitle)
        state.status
    }
}