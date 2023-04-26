package edu.uniandes.moni.view.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import edu.uniandes.moni.view.theme.inputBackgroundColor
import edu.uniandes.moni.view.theme.main
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.view.theme.trailingIconColor
import java.util.*

@Composable
fun EmailInput(valueCallback: (value: TextFieldValue) -> Unit) {
    Surface(
        color = Color.White
    ) {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = text,
            trailingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(0.95f),
            onValueChange = {
                text = it
            },
            label = {
                Text(
                    text = "Email",
                    fontFamily = moniFontFamily,
                    fontStyle = FontStyle.Normal
                )
            },
            placeholder = {
                Text(
                    text = "Enter your e-mail",
                    fontFamily = moniFontFamily,
                    fontStyle = FontStyle.Normal
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                trailingIconColor = trailingIconColor,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            )
        )
        valueCallback(text)
    }
}

@Composable
fun PasswordInput(label: String, valueCallback: (value: String) -> Unit) {
    Surface(
        color = Color.White
    ) {
        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(0.95f),
            singleLine = true,
            label = { Text(label, fontFamily = moniFontFamily) },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
                    // Please provide localized description for accessibility services
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                trailingIconColor = trailingIconColor,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            )
        )
        valueCallback(password)
    }
}

@Composable
fun NameInput(valueCallback: (value: TextFieldValue) -> Unit) {
    Surface(
        color = Color.White
    ) {
        var name by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = name,
            modifier = Modifier.fillMaxWidth(0.95f),
            trailingIcon = { Icon(Icons.Outlined.AccountBox, contentDescription = null) },
            onValueChange = {
                name = it
            },
            label = { Text(text = "Name", fontFamily = moniFontFamily) },
            placeholder = { Text(text = "Enter your full name", fontFamily = moniFontFamily) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                trailingIconColor = trailingIconColor,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            )
        )
        valueCallback(name)
    }
}

@Composable
fun InputText(
    inputLabel: String,
    inputPlaceholder: String,
    valueRecovery: String,
    valueCallback: (value: String) -> Unit
) {
    Surface(
        color = Color.White
    ) {
        var value by remember { mutableStateOf(if (valueRecovery.isNotBlank()) valueRecovery else "") }
        OutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(0.95f),
            onValueChange = {
                value = it
                valueCallback(value)
            },
            label = { Text(text = inputLabel, fontFamily = moniFontFamily) },
            placeholder = {
                if (inputPlaceholder != null) {
                    Text(text = inputPlaceholder, fontFamily = moniFontFamily)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            )
        )

    }
}

@Composable
fun InputSearch(
    valueCallback: (value: TextFieldValue) -> Unit
) {
    Surface(
        color = Color.White
    ) {
        var value by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(0.95f),
            onValueChange = {
                value = it
            },
            trailingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            label = { Text(text = "Search...", fontFamily = moniFontFamily) },
            placeholder = {
                Text(text = "Search...", fontFamily = moniFontFamily)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            )
        )
        valueCallback(value)
    }
}

@Composable
fun Select(
    label: String,
    optionList: MutableList<String>,
    valueRecovery: String,
    valueCallback: (value: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    Surface(
        color = Color.White
    ) {
        TextField(
            value = if (valueRecovery != "null") valueRecovery else selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clickable() { expanded = !expanded }
                .onGloballyPositioned { coordinates -> textFiledSize = coordinates.size.toSize() },
            label = { Text(text = label, fontFamily = moniFontFamily) },
            trailingIcon = {
                Icon(Icons.Outlined.ArrowDropDown, "", Modifier.clickable { expanded = !expanded })
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = main,
                focusedLabelColor = main,
                cursorColor = main,
                backgroundColor = inputBackgroundColor,
                unfocusedBorderColor = inputBackgroundColor
            ),
            enabled = false
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFiledSize.width.toDp() })
        ) {
            optionList.forEach { label ->
                DropdownMenuItem(onClick =
                {
                    selectedItem = label
                    valueCallback(selectedItem)
                    expanded = false
                }) {
                    Text(
                        text = label,
                        fontSize = 20.sp,
                        color = Color.Black,

                        fontWeight = FontWeight.Bold,
                        modifier = Modifier

                            .padding(20.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun NewTimePicker(valueRecovery: String, valueCallback: (value: String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedTimeText by remember { mutableStateOf(if (valueRecovery.isNotBlank()) valueRecovery else "") }

// Fetching current hour, and minute
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val timePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedTimeText = "$selectedHour:$selectedMinute"
            valueCallback(selectedTimeText)
        }, hour, minute, false
    )

    TextField(
        value = selectedTimeText,
        onValueChange = { selectedTimeText = it },
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .clickable() { timePicker.show() },
        label = { Text(text = "Select an hour (HH:MM:SS)", fontFamily = moniFontFamily) },
        trailingIcon = {
            Icon(
                Icons.Outlined.DateRange,
                "",
                Modifier.clickable { timePicker.show() })
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = main,
            focusedLabelColor = main,
            cursorColor = main,
            backgroundColor = inputBackgroundColor,
            unfocusedBorderColor = inputBackgroundColor
        ),
        enabled = false
    )
}

@Composable
fun NewDatePicker(valueRecovery: String, valueCallback: (value: String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf(if (valueRecovery != "") valueRecovery else "") }

// Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            valueCallback(selectedDateText)

        },
        year, month, dayOfMonth,

        )

    TextField(
        value = selectedDateText,
        onValueChange = { selectedDateText = it },
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .clickable() { datePicker.show() },
        label = { Text(text = "Select a date (dd/mm/yyyy)", fontFamily = moniFontFamily) },
        trailingIcon = {
            Icon(
                Icons.Outlined.DateRange,
                "",
                Modifier.clickable { datePicker.show() })
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = main,
            focusedLabelColor = main,
            cursorColor = main,
            backgroundColor = inputBackgroundColor,
            unfocusedBorderColor = inputBackgroundColor
        ),
        enabled = false
    )

}


@Preview
@Composable
fun PreviewFunction() {
}

