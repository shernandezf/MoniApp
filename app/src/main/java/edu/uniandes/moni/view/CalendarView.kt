package edu.uniandes.moni.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.model.Day
import edu.uniandes.moni.model.adapter.SessionAdapter
import edu.uniandes.moni.model.dto.SessionDTO
import edu.uniandes.moni.model.dto.SessionExtendedDTO
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.TutoringViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import androidx.compose.foundation.lazy.items as itemsVar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(navController: NavController, sessionViewModel: SessionViewModel) {
    val scaffoldState = rememberScaffoldState()
    val currentMonth = remember { mutableStateOf(YearMonth.now()) }
    val (monthYear, firstDayOfWeek, days) = getMonthEvents(currentMonth.value)
    val selectedDay = remember { mutableStateOf<LocalDate?>(null) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Calendar") },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        currentMonth.value = currentMonth.value.minusMonths(1)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous month")
                    }
                    Text(
                        text = monthYear,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    IconButton(onClick = {
                        currentMonth.value = currentMonth.value.plusMonths(1)
                    }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Next month")
                    }
                }

                Row {
                    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                    daysOfWeek.forEach { dayOfWeek ->
                        Text(text = dayOfWeek, Modifier.weight(1f), textAlign = TextAlign.Center)
                    }
                }

                LazyColumn(Modifier.weight(0.7f)) {
                    items((days.size + firstDayOfWeek + 6) / 7) { week ->
                        Row {
                            (1..7).forEach { dayInWeek ->
                                val index = week * 7 + dayInWeek - 1 - firstDayOfWeek
                                if (index in days.indices) {
                                    val day = days[index]
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                selectedDay.value = day.date
                                            }
                                            .background(
                                                if (day.date == selectedDay.value) Color(0xFFFFA500) else Color.White,
                                                shape = CircleShape
                                            )
                                            .weight(1f)
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = day.date.dayOfMonth.toString(),
                                            color = Color.Black
                                        )
                                    }
                                } else {
                                    Spacer(Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                Divider()

                val dayEvents = days.find { it.date == selectedDay.value }
                var listaSesiones = remember { mutableStateOf(listOf<SessionExtendedDTO>()) }
                LazyColumn(Modifier.weight(0.8f)) {
                    if (dayEvents != null) {
                        sessionViewModel.getSessionsByDate(dayEvents.date) {
                            listaSesiones.value = it
                            Log.d("CALENDARIO", "Fecha: ${dayEvents.date} y Sesiones: ${it.size}")
                        }
                    }
                    if (listaSesiones.value.isNotEmpty()) {
                        itemsVar(listaSesiones.value) {
                            TextButton(onClick = {
                                navController.navigate(route = AppScreens.CalendarDetail.route + "/${it.sessionId}")
                            }) {
                                Text(it.tutoringTitle)
                            }
                        }
                    } else {
                        item {
                            Text("Not sessions")
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMonthEvents(yearMonth: YearMonth): Triple<String, Int, List<Day>> {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = yearMonth.atDay(1).dayOfWeek.value % 7

    val days = mutableListOf<Day>()
    for (i in 1..daysInMonth) {
        days.add(Day(yearMonth.atDay(i), listOf())) // Add your events here
    }

    val monthYear =
        yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + yearMonth.year
    return Triple(monthYear, firstDayOfWeek, days)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar(tutoringViewModel: TutoringViewModel, navController: NavController) {
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
                /*
                sessionAdapter.retriveSessionsUser() {
                    items = it
                }
                print("The size of the list is " + items.size)

                 */
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Show events for ${selectedDate.value}")
            items.forEach { item ->
                println(item.tutorEmail)
                var tutoria by remember { mutableStateOf<TutoringDTO?>(null) }
                LaunchedEffect(item.tutoringId) {
                    tutoringViewModel.getTutoringById(item.tutoringId) {
                        tutoria = it
                    }
                }
                tutoria?.let {
                    SessionRow(
                        title = it.title,
                        date = item.meetingDate.toString()
                    )
                }
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
