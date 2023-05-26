package edu.uniandes.moni.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.model.SessionModel
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.view.components.MainButton
import edu.uniandes.moni.view.components.TextButton
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.TutoringViewModel

@Composable
fun CalendarDetail(navController: NavController, tutoringViewModel: TutoringViewModel, sessionViewModel: SessionViewModel, idSession: String) {

    val scaffoldState = rememberScaffoldState()
    var session: SessionDTO by remember {mutableStateOf(SessionDTO())}
    var tutoringRelated: TutoringDTO by remember {mutableStateOf(TutoringDTO())}

    LaunchedEffect(tutoringRelated, session) {
        sessionViewModel.getSessionById(idSession) {
            if (it != null) {
                session = it
            }
        }

        while(session.tutoringId != "") {
            tutoringViewModel.getTutoringById(session.tutoringId) {
                tutoringRelated = it
            }
        }
        println("holaaaaaaaa2")
        println(tutoringRelated)

    }

    println(tutoringRelated)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Your session", false, false) },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(15.dp)
        ) {
            Column(modifier = Modifier.padding(bottom = 15.dp)) {
                Text(
                    text = tutoringRelated.title,
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h4,
                    color = Color(53, 109, 230)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 10.dp)) {
                    Text(
                        text = "Date:",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)

                    )
                    Text(
                        text = session.meetingDate.toString(),
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp)) {
                    Text(
                        text = "Tutor email:",
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f)

                    )
                    Text(
                        text = session.tutorEmail,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier
                    .padding(top = 10.dp, bottom = 120.dp)) {
                    Text(
                        text = "Place:",
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = session.place,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier.weight(1f)

                    )

                }

                MainButton(text = "Reschedule") {

                }
                Box(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)) {
                    TextButton(text = "Cancel") {

                    }
                }


            }
        }

    }

}