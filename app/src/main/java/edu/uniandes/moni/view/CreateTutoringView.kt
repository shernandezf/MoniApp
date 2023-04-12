package edu.uniandes.moni.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.InputText
import edu.uniandes.moni.view.components.MainButton
import edu.uniandes.moni.view.components.Select
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.TutoringViewModel
import edu.uniandes.moni.viewmodel.UserViewModel


@Composable
fun CreateTutoryScreen(navController: NavController) {
    val tutoringViewModel = TutoringViewModel()
    val scaffoldState = rememberScaffoldState()
    val listOfTeach = mutableListOf("Calculus", "Physics", "Dancing", "Fitness")
    val listOfLocation = mutableListOf("University", "Out")
    var topic = "null"
    var tutoringTitle = "null"
    var tutoringDescription = "null"
    var tutoringLocation = "null"
    var fee = "null"
    var user = UserViewModel.getUser()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Create", true, true) },
        bottomBar = { }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "What do they teach?",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(20.dp)
                    )

                    Select("Select item", listOfTeach, topic) {
                        topic = it
                    }
                }


            }
            item {

                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Title of the tutory",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(20.dp)
                    )
                    InputText("Insert the title", "", tutoringTitle) {
                        tutoringTitle = it
                    }

                }

            }
            item {

                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Tutory description",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(20.dp)
                    )
                    InputText("Write about your methodology", "", tutoringDescription) {
                        tutoringDescription = it
                    }

                }
            }
            item {
                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Location of the tutory",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(20.dp)
                    )

                    Select("Select item", listOfLocation, topic) {
                        topic = it
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(bottom = 40.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Fee",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(20.dp)
                    )
                    InputText("price per hour", "", fee) {
                        fee = it
                    }

                }
            }
            item {
                Box(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    MainButton("Create") {
                        var inUniversity = false
                        if (tutoringLocation == "University") {
                            inUniversity = true
                        }

                        tutoringViewModel.createTutoring(
                            tutoringDescription,
                            inUniversity,
                            fee,
                            tutoringTitle,
                            topic,
                            user.email,
                        )
                        navController.navigate(route = AppScreens.MarketScreen.route)
                    }
                }
            }

        }
    }

}


@Composable
fun TextFieldWithTitle(title: String, show: String): String {

    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(show) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(10.dp)
                .size(300.dp, 40.dp)
        )
    }
    return text
}

@Composable
fun DesplegableTextFieldWithTitle(title: String, list: List<String>): String {
    var selectedItem = ""
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
        )
        selectedItem = DesplegableTextField(list = list)

    }
    return selectedItem

}

@Composable
fun DesplegableTextField(list: List<String>): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    Column() {
        OutlinedTextField(value = selectedItem,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFiledSize = coordinates.size.toSize() },
            label = { Text(text = "Select item") },
            trailingIcon = {
                Icon(icon, "", Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFiledSize.width.toDp() })
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick =
                {
                    selectedItem = label
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
    return selectedItem
}


