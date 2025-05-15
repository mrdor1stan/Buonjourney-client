package com.mrdor1stan.buonjourney.ui.screens.trip.newone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.DateInputField
import com.mrdor1stan.buonjourney.ui.common.DatePicker
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import kotlinx.coroutines.launch

@Composable
fun AddTripScreen(
    tripId: Long?,
    viewModel: AddTripScreenViewModel = viewModel(factory = AddTripScreenViewModel.Factory(tripId)),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
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

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))) {
        OutlinedTextField(
            value = state.title,
            onValueChange = viewModel::updateTitle,
            label = { BodyText("Title") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                )
                .padding(top = dimensionResource(id = R.dimen.small_margin))
        )

        DateInputField(
            value = state.startDate?.toShortString ?: "",
            label = "Start date",
            showDatePicker = { showStartDatePicker = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                )
        )
        DateInputField(
            value = state.endDate?.toShortString ?: "",
            label = "End date",
            showDatePicker = { showEndDatePicker = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                )
        )
        OutlinedTextField(
            value = state.description ?: "",
            onValueChange = viewModel::updateDescription,
            label = { BodyText("Description") },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.middle_margin))
                .heightIn(min = 160.dp)
        )
        PrimaryButton(
            text = stringResource(R.string.save_button_label), modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.middle_margin))
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                ), onClick = {
                scope.launch {
                    viewModel.saveTrip()
                    navigateBack()
                }
            }, enabled = state.isAddButtonEnabled
        )
    }
}


