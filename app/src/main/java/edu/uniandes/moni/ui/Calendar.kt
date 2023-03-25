package edu.uniandes.moni.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.monitores.BottomPart
import com.example.monitores.SearchView
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.navigation.AppNavigation
import edu.uniandes.moni.ui.theme.MoniTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(navController: NavController){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons( "Search", true, true) },
        bottomBar = { BottomPart(navController) }
    ) {contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)) {
            Calendar()
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Calendar() {
    val today = LocalDate.now()
    val currentMonth = remember { mutableStateOf(YearMonth.from(today)) }
    val selectedDate = remember { mutableStateOf(today) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.value.year}",
            modifier = Modifier.padding(16.dp)
        )
        WeekdaysRow()
        DaysGrid(currentMonth.value, selectedDate)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Show events for the selected date
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Show events for ${selectedDate.value}")
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreviewCalendar() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
//        CalendarView()
    }
}
