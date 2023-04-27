package edu.uniandes.moni.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.CreateDialog
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
    var topic = ""
    var tutoringTitle = ""
    var tutoringDescription = ""
    var tutoringLocation = ""
    var fee = ""

    var pressedButton = false
    val filledTopic = remember { mutableStateOf(true) }
    val filledTutoringTitle = remember { mutableStateOf(true) }
    val filledTutoringDescription = remember { mutableStateOf(true) }
    val filledTutoringLocation = remember { mutableStateOf(true) }
    val filledFee = remember { mutableStateOf(true) }

    val user = UserViewModel.getUser()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Create", false, false) },
        bottomBar = { }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "What are you teaching?",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 15.dp)
                    )

                    Select("Select item", listOfTeach, topic) {
                        topic = it
                        if(pressedButton)
                            filledTopic.value = it.isNotBlank()
                    }
                    if(!filledTopic.value) {
                        Text(
                            text = "Please fill the topic of the tutory",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }


            }
            item {

                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Title of the tutoring",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 15.dp)
                    )
                    InputText("Insert the title", "", tutoringTitle) {
                        tutoringTitle = it
                        if(pressedButton)
                            filledTutoringTitle.value = it.isNotBlank()
                    }
                    if(!filledTutoringTitle.value) {
                        Text(
                            text = "Please fill the tutoring title",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                }

            }
            item {

                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Tutoring description",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 15.dp)
                    )
                    InputText("Write about your methodology", "", tutoringDescription) {
                        tutoringDescription = it
                        if(pressedButton)
                            filledTutoringDescription.value = it.isNotBlank()
                    }

                    if(!filledTutoringDescription.value) {
                        Text(
                            text = "Please fill the tutoring description",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                }
            }
            item {
                Column(modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp)) {
                    Text(
                        text = "Location of the tutoring",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 15.dp)
                    )

                    Select("Select item", listOfLocation, tutoringLocation) {
                        tutoringLocation = it
                        if(pressedButton)
                            filledTutoringLocation.value = it.isNotBlank()
                    }
                    if(!filledTutoringLocation.value) {
                        Text(
                            text = "Please fill the tutoring location",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
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
                            .padding(bottom = 15.dp)
                    )
                    InputText("Price per hour", "", fee) {
                        fee = it
                        if(pressedButton)
                            filledFee.value = it.isNotBlank()
                    }
                    if(!filledFee.value) {
                        Text(
                            text = "Please fill the tutoring fee",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                }
            }
            item {
                Box(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 20.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                ) {
                    val i = remember { mutableStateOf(1000) }
                    MainButton("Create") {
                        pressedButton = true

                        if(topic.isBlank() || tutoringTitle.isBlank() || tutoringDescription.isBlank() || tutoringLocation.isBlank() || fee.isBlank()) {
                            if(topic.isBlank())
                                filledTopic.value = false
                            if(tutoringTitle.isBlank())
                                filledTutoringTitle.value = false
                            if(tutoringDescription.isBlank())
                                filledTutoringDescription.value = false
                            if(tutoringLocation.isBlank())
                                filledTutoringLocation.value = false
                            if(fee.isBlank())
                                filledFee.value = false
                        }
                        else {
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
                            i.value = 0

                        }
                    }
                    if(i.value == 0)
                        CreateDialog("Created tutoring", "Tutoring created successfully") {
                            navController.navigate(route = AppScreens.MarketScreen.route)
                            i.value = 1000
                        }
                }
            }

        }
    }
}


