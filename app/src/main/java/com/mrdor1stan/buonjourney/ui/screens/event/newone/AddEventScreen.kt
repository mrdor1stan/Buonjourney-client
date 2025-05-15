package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
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
    viewModel: AddEventScreenViewModel = viewModel(factory = AddEventScreenViewModel.Factory(tripId)),
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
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
            value = state.title,
            onValueChange = viewModel::updateTitle,
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

        ChipGroup(
            items = EventDto.Type.entries,
            selectedItemIndex = state.eventType.ordinal,
            onSelectedChanged = viewModel::updateEventType
        ) {
            Text(text = it.name)
        }

        when (state.eventType) {
            EventDto.Type.Accommodation -> TODO()
            EventDto.Type.Transport -> TODO()
            EventDto.Type.Entertainment -> {
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
                        value = "",
                        label = "Start date",
                        showDatePicker = { /*TODO*/ },
                        modifier = Modifier.weight(4f)
                    )

                    TimeInputField(
                        value = "", label = "Start time", showTimePicker = { /*TODO*/ },
                        modifier = Modifier.weight(3f)
                    )
                }
                DateInputField(
                    value = "",
                    label = "End date",
                    showDatePicker = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal =
                            dimensionResource(id = R.dimen.middle_margin)
                        )
                )


            }

            EventDto.Type.NoType -> Unit
        }

//        DateInput(
//            value = state.startDate?.toShortString ?: "",
//            label = "Start date",
//            showDatePicker = { showStartDatePicker = true },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    horizontal =
//                    dimensionResource(id = R.dimen.middle_margin)
//                )
//        )

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
        PrimaryButton(
            text = stringResource(R.string.save_button_label), modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(id = R.dimen.middle_margin))
                .padding(
                    horizontal =
                    dimensionResource(id = R.dimen.middle_margin)
                ), onClick = {
                scope.launch {
                    viewModel.addEvent()
                    navigateBack()
                }
            }, enabled = state.isAddButtonEnabled
        )
    }
}

