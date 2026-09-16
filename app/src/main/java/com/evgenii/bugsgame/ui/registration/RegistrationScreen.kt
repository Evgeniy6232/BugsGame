package com.evgenii.bugsgame.ui.registration

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evgenii.bugsgame.R
import com.evgenii.bugsgame.domain.model.Difficulty
import com.evgenii.bugsgame.domain.model.Gender
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import com.evgenii.bugsgame.ui.common.difficultyLabel
import com.evgenii.bugsgame.ui.common.genderLabel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onNavigateToResult: () -> Unit
) {
    // Подписываемся на состояние из ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.registration_title),
            style = MaterialTheme.typography.headlineMedium
        )

        FullNameField(
            value = uiState.fullName,
            isError = uiState.nameError,
            onValueChange = viewModel::onFullNameChange
        )

        GenderSelector(
            selected = uiState.gender,
            isError = uiState.genderError,
            onSelect = viewModel::onGenderChange
        )

        CourseSelector(
            selected = uiState.course,
            isError = uiState.courseError,
            onSelect = viewModel::onCourseChange
        )

        DifficultySlider(
            value = uiState.difficulty,
            onValueChange = viewModel::onDifficultyChange
        )

        BirthDateField(
            value = uiState.birthDate,
            isError = uiState.birthDateError,
            onValueChange = viewModel::onBirthDateChange
        )

        Button(
            onClick = {
                if (viewModel.onSubmit()) {
                    onNavigateToResult()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.register_button))
        }
    }
}

@Composable
private fun FullNameField(
    value: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.full_name_label)) },
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) Text(stringResource(R.string.full_name_error))
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GenderSelector(
    selected: Gender?,
    isError: Boolean,
    onSelect: (Gender) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.gender_label),
            style = MaterialTheme.typography.bodyLarge
        )

        Row(modifier = Modifier.selectableGroup()) {
            Gender.entries.forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = selected == gender,
                            onClick = { onSelect(gender) },
                            role = Role.RadioButton
                        )
                        .padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = selected == gender,
                        onClick = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = genderLabel(gender))
                }
            }
        }

        if (isError) {
            Text(
                text = stringResource(R.string.gender_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CourseSelector(
    selected: String?,
    isError: Boolean,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val courses = stringArrayResource(R.array.courses).toList()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.course_label)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            isError = isError,
            supportingText = {
                if (isError) Text(stringResource(R.string.course_error))
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            courses.forEach { course ->
                DropdownMenuItem(
                    text = { Text(course) },
                    onClick = {
                        onSelect(course)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DifficultySlider(
    value: Difficulty,
    onValueChange: (Difficulty) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.difficulty_label),
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = value.ordinal.toFloat(),
            onValueChange = { newValue ->
                onValueChange(Difficulty.entries[newValue.roundToInt()])
            },
            valueRange = 0f..(Difficulty.entries.size - 1).toFloat(),
            steps = Difficulty.entries.size - 2
        )

        Text(
            text = difficultyLabel(value),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDateField(
    value: LocalDate?,
    isError: Boolean,
    onValueChange: (LocalDate) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value
            ?.atStartOfDay(ZoneOffset.UTC)
            ?.toInstant()
            ?.toEpochMilli()
    )

    OutlinedTextField(
        value = value?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(R.string.birth_date_label)) },
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
        },
        isError = isError,
        supportingText = {
            if (isError) Text(stringResource(R.string.birth_date_error))
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            onValueChange(date)
                        }
                        showDialog = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
