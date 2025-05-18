package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import com.mrdor1stan.buonjourney.common.extentions.dateFromMillis
import com.mrdor1stan.buonjourney.common.extentions.millis
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.common.extentions.toString
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar


@Composable
fun DateInputField(
    value: LocalDateTime?,
    label: String,
    updateValue: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    minDate: LocalDateTime? = null,
    maxDate: LocalDateTime? = null
) {

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker)
        DatePicker(onSuccess = {
            updateValue(it)
            showDatePicker = false
        }, onDismiss = {
            showDatePicker = false
        },
            maxDate = maxDate,
            minDate = minDate,
            currentDate = value ?: minDate ?: maxDate
        )

    OutlinedTextField(
        value = value?.toShortString ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(value) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        showDatePicker = true
                    }
                }
            }
    )
}

@Composable
fun TimeInputField(
    value: LocalTime?,
    label: String,
    updateValue: (LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember {
        mutableStateOf(false)
    }

    if (showTimePicker)
        TimePicker(
            onSuccess = {
                updateValue(it)
                showTimePicker = false
            },
            onDismiss = {
                showTimePicker = false
            },
            initialTime = value
        )


    OutlinedTextField(
        value = value?.toString ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        placeholder = { Text("HH:MM") },
        trailingIcon = {
            Icon(Icons.Default.AccessTime, contentDescription = "Select time")
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(value) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        showTimePicker = true
                    }
                }
            }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onSuccess: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    currentDate: LocalDateTime? = null,
    minDate: LocalDateTime? = null,
    maxDate: LocalDateTime? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate?.millis,
        initialDisplayedMonthMillis = (currentDate ?: LocalDateTime.now()).millis,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return when {
                    minDate != null -> minDate.millis <= utcTimeMillis
                    maxDate != null -> maxDate.millis >= utcTimeMillis
                    else -> super.isSelectableDate(utcTimeMillis)
                }
            }
        }
    )
    val confirmEnabled = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis != null
        }
    }
    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onSuccess(it.dateFromMillis)
                    }
                },
                enabled = confirmEnabled.value
            ) {
                Text(text = "OK")
            }
        }
    ) {
        androidx.compose.material3.DatePicker(state = datePickerState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    onSuccess: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialTime: LocalTime? = null,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime?.hour ?: currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = initialTime?.minute ?: currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Select time")
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    onSuccess(time)
                }
            ) {
                Text(text = "OK")
            }
        },
        text = {
            androidx.compose.material3.TimeInput(state = timePickerState)
        })
}
