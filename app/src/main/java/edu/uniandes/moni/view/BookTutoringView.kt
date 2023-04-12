package edu.uniandes.moni.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.model.dao.TutoringDAO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.InputText
import edu.uniandes.moni.view.components.MainButton
import edu.uniandes.moni.view.components.NewDatePicker
import edu.uniandes.moni.view.components.NewTimePicker
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.TutoringViewModel
import edu.uniandes.moni.viewmodel.UserViewModel
import java.sql.Timestamp

@Composable
fun BookTutoringScreen(
    navController: NavController,
    id: String,
    tutoringTitle: String?,
    description: String?,
    rate: String?,
    tutorEmail: String?,
) {

    val sessionViewModel = SessionViewModel()

    TutoringViewModel().getTutoringById(id)
    val tutoria: TutoringDAO = TutoringViewModel.getOneTutoring()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Book", true, true) },
        bottomBar = { }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(15.dp)
        ) {
            if (tutoringTitle != null && description != null && rate != null && id != null) {
                var commentary = ""
                var date = ""
                var time = ""
                var place = ""

                LazyColumn() {
                    item {
                        TutoringDescription(tutoringTitle, description)
                    }

                    item {

                        Column(
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            Text(
                                text = "Commentaries for the tutor",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )
                            InputText("I'd like to learn about...", "") {
                                commentary = it
                            }

                        }
                    }

                    item {

                        Column(
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            Text(
                                text = "Hourly rate",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )
                            Text(
                                text = rate,
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )

                        }
                    }

                    item {

                        Column(
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            Text(
                                text = "Date",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )
                            NewDatePicker() {
                                date = it
                            }

                        }
                    }


                    item {

                        Column(
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            Text(
                                text = "Time",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )
                            NewTimePicker() {
                                time = it
                            }
                        }
                    }

                    item {

                        Column(
                            modifier = Modifier.padding(
                                bottom = 40.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        ) {
                            Text(
                                text = "Place",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontFamily = moniFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(20.dp)
                            )
                            InputText("Describe the place", "") {
                                place = it
                            }

                        }
                    }

                    if (tutoria.tutorEmail != UserViewModel.getUser().email) {
                        item {
                            MainButton(text = "Confirm") {
                                if (tutorEmail != null) {
                                    val dateS = date.split("/")
                                    val day = dateS[0].toInt()
                                    val month = dateS[1].toInt()
                                    val year = dateS[2].toInt()

                                    val timeS = time.split(":")
                                    val hour = timeS[0].toInt()
                                    val min = timeS[1].toInt()


                                    val meetingPlace = Timestamp(year, month, day, hour, min, 0, 0)
                                    Log.d("ASSEr", meetingPlace.toString())
                                    sessionViewModel.addSession2(UserViewModel.getUser().email, meetingPlace, place, tutorEmail, id) {

                                        }
                                }
                                navController.navigate(route = AppScreens.MarketScreen.route)
                            }
                        }

                    } else {
                        item {
                            MainButton(text = "You can't book your own tutoring") {
                                navController.navigate(route = AppScreens.MarketScreen.route)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun TutoringDescription(tutoryTitle: String, description: String) {
        Column() {
            Text(
                text = tutoryTitle,
                fontSize = 30.sp,
                color = Color.Black,
                fontFamily = moniFontFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            )

            Text(
                text = "Description",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = moniFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp)
            )

            Text(
                text = description,
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = moniFontFamily,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp)
            )

        }

}


@Composable
fun BoxWithRows(
    title1: String, show1: String,
    title2: String, show2: String,
    title3: String, show3: String,
    title4: String, show4: String
) {
    var date = ""
    var time = ""
    var place  = ""
    Box(
        modifier = Modifier
            .background(Color(247, 247, 248))
            .padding(20.dp)
            .width(300.dp)
    ) {
        Column() {
            RowWithTitleText(title1, show1)
            RowWithTitleTextField(title2, show2) {
                date = it
            }
            RowWithTitleTextField(title3, show3) {
                time = it
            }
            RowWithTitleTextField(title4, show4) {
                place = it
            }
        }
    }
}

@Composable
fun RowWithTitleTextField(title: String, show: String, valueCallback: (value: String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,
            fontFamily = moniFontFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(20.dp)
        )
        InputText(show, "") {
            text = it
        }

    }
    valueCallback(text)

}

@Composable
fun RowWithTitleText(title: String, show: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )

        Text(
            text = show,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}
