package edu.uniandes.moni.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.viewmodel.TutoringViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(navController: NavController, tutoringViewModel: TutoringViewModel) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Calendar", false, false) },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Calendar(tutoringViewModel = tutoringViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(tutoringViewModel: TutoringViewModel) {
    val sessionAdapter: SessionAdapter = SessionAdapter()
    val today = LocalDate.now()
    val currentMonth = remember { mutableStateOf(YearMonth.from(today)) }
    val selectedDate = remember { mutableStateOf(today) }
    var items: MutableList<SessionDTO> = mutableListOf<SessionDTO>()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${
                currentMonth.value.month.getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                )
            } ${currentMonth.value.year}",
            modifier = Modifier.padding(16.dp)
        )
        WeekdaysRow()
        DaysGrid(currentMonth.value, selectedDate)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                // Show events for the selected date
                sessionAdapter.retriveSessionsUser() {
                    items = it
                }
                print("The size of the list is " + items.size)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Show events for ${selectedDate.value}")
            items.forEach { item ->
                println(item.tutorEmail)
                tutoringViewModel.getTutoringById(item.tutoringId)
                val tutoria: TutoringDTO = TutoringViewModel.getOneTutoring()
                SessionRow(
                    title = tutoria.title,
                    date = item.meetingDate.toString()
                )
            }
        }
    }
}

@Composable
fun WeekdaysRow() {
    Row {
        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        for (day in daysOfWeek) {
            Text(
                text = day,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysGrid(month: YearMonth, selectedDate: MutableState<LocalDate>) {
    val firstDayOfMonth = month.atDay(1)
    val daysInMonth = month.lengthOfMonth()
    val initialEmptyDays = firstDayOfMonth.dayOfWeek.value % 7

    val totalDays = initialEmptyDays + daysInMonth

    Column {
        for (i in 0 until totalDays step 7) {
            Row {
                for (j in 0..6) {
                    val day = i + j - initialEmptyDays + 1
                    if (day in 1..daysInMonth) {
                        Button(
                            onClick = {
                                selectedDate.value = month.atDay(day)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text("$day")
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun SessionRow(title: String, date: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color(0xFFFFA500), CircleShape)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = date,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
