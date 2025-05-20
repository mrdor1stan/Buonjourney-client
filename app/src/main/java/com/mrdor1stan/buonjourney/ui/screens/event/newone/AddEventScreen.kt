package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDataType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.ChipGroup
import com.mrdor1stan.buonjourney.ui.common.DateInputField
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import com.mrdor1stan.buonjourney.ui.common.TimeInputField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    tripId: Long,
    eventId: Long?,
    viewModel: AddEventScreenViewModel = viewModel(factory = AddEventScreenViewModel.Factory(tripId, eventId)),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin)),
    ) {
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

        OutlinedTextField(
            value = state.description ?: "",
            onValueChange = viewModel::updateDescription,
            label = { BodyText("Description") },
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.middle_margin))
                .heightIn(min = 60.dp)
        )

        OutlinedTextField(
            value = state.address,
            onValueChange = viewModel::updateAddress,
            label = { BodyText("Address") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                )
                .padding(top = dimensionResource(id = R.dimen.small_margin))
        )

        Text(
            text = "Event type",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                )
                .padding(top = dimensionResource(id = R.dimen.small_margin))
        )

        ChipGroup(
            items = EventDto.Type.entries,
            selectedItemIndex = state.eventType.ordinal,
            onSelectedChanged = viewModel::updateEventType
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap)),
                modifier = Modifier
                    .semantics(mergeDescendants = true) {
                        this.contentDataType = ContentDataType.Toggle
                    }
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.small_margin)
                    )
            ) {
                Icon(imageVector = it.icon, contentDescription = null)
                Text(
                    text = stringResource(id = it.titleResId)
                )
            }
        }

        when (state.eventType) {
            EventDto.Type.Accommodation -> {
                Text(
                    text = "Check-in",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                DateInputField(
                    value = state.startDate,
                    label = "Date",
                    updateValue = viewModel::updateStartDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        ),
                    maxDate = state.endDate
                )

                AnimatedVisibility(visible = state.startDate != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal =
                                dimensionResource(id = R.dimen.middle_margin)
                            ),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                    ) {
                        TimeInputField(
                            value = state.startTimeFrom,
                            label = "From",
                            updateValue = viewModel::updateStartTimeFrom,
                            modifier = Modifier.weight(3f)
                        )
                        TimeInputField(
                            value = state.startTimeTo,
                            label = "To",
                            updateValue = viewModel::updateStartTimeTo,
                            modifier = Modifier.weight(3f)
                        )
                    }
                }

                Text(
                    text = "Check-out",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )

                DateInputField(
                    value = state.endDate, label = "Date", updateValue = viewModel::updateEndDate,
                    minDate = state.startDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                AnimatedVisibility(visible = state.endDate != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal =
                                dimensionResource(id = R.dimen.middle_margin)
                            ),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                    ) {
                        TimeInputField(
                            value = state.endTimeFrom,
                            label = "From",
                            updateValue = viewModel::updateEndTimeFrom,
                            modifier = Modifier.weight(3f)
                        )
                        TimeInputField(
                            value = state.endTimeTo,
                            label = "To",
                            updateValue = viewModel::updateEndTimeTo,
                            modifier = Modifier.weight(3f)
                        )
                    }
                }
            }

            EventDto.Type.Entertainment -> {

                Text(
                    text = "Start",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                ) {
                    DateInputField(
                        value = state.startDate,
                        label = "Date",
                        updateValue = viewModel::updateStartDate,
                        modifier = Modifier.weight(4f),
                        maxDate = state.endDate
                    )

                    TimeInputField(
                        value = state.startTimeFrom, label = "Time", viewModel::updateStartTimeFrom,
                        modifier = Modifier.weight(3f)
                    )
                }

                Text(
                    text = "End",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                ) {
                    DateInputField(
                        value = state.endDate,
                        label = "Date",
                        updateValue = viewModel::updateEndDate,
                        modifier = Modifier.weight(4f),
                        minDate = state.startDate
                    )

                    TimeInputField(
                        value = state.endTimeFrom,
                        label = "Time",
                        updateValue = viewModel::updateEndTimeFrom,
                        modifier = Modifier.weight(3f)
                    )
                }
            }

            EventDto.Type.Transport -> {
                Text(
                    text = "Departure",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                ) {
                    DateInputField(
                        value = state.startDate,
                        label = "Date",
                        updateValue = viewModel::updateStartDate,
                        modifier = Modifier.weight(4f)
                    )

                    TimeInputField(
                        value = state.startTimeFrom,
                        label = "Time",
                        updateValue = viewModel::updateStartTimeFrom,
                        modifier = Modifier.weight(3f)
                    )
                }
                Text(
                    text = "Arrival",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
                ) {
                    DateInputField(
                        value = state.endDate,
                        label = "Date",
                        updateValue = viewModel::updateEndDate,
                        modifier = Modifier.weight(4f)
                    )

                    TimeInputField(
                        value = state.endTimeFrom,
                        label = "Time",
                        updateValue = viewModel::updateEndTimeFrom,
                        modifier = Modifier.weight(3f)
                    )
                }
            }

            EventDto.Type.NoType -> Unit
        }

        PrimaryButton(
            text = stringResource(R.string.save_button_label), modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.middle_margin))
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                ), onClick = {
                scope.launch {
                    viewModel.saveEvent()
                    navigateBack()
                }
            }, enabled = state.isAddButtonEnabled
        )
    }
}

