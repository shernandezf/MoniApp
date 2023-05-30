package edu.uniandes.moni.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.viewmodel.TutoringViewModel

@Composable
fun CheckReview(navController: NavController,
                tutoringViewModel: TutoringViewModel,
                id: String) {
    val scaffoldState = rememberScaffoldState()

    var tutoria by remember { mutableStateOf<TutoringDTO?>(null) }

    var calification by remember { mutableStateOf(0) }

    LaunchedEffect(id) {
        tutoringViewModel.getTutoringById(id) {
            tutoria = it
            var sum = 0
            var cant = 0
            for(num in tutoria!!.scores) {
                sum += num
                cant ++
            }
            if(cant > 0) {
                calification = sum /cant
            }

        }

    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Check reviews") },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(15.dp)
        ) {
            LazyColumn() {
                item {
                    tutoria?.let {
                        Text(
                            text = it.title,
                            fontSize = 36.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(0.dp, 15.dp)
                        )
                    }
                }

                item {
                    Text(
                        text = "Calification",
                        fontSize = 36.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 15.dp)
                    )
                }

                item {
                    Text(
                        text = calification.toString(),
                        fontSize = 26.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(0.dp, 15.dp)
                    )
                }

                item {
                    Text(
                        text = "Reviews",
                        fontSize = 36.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp, 15.dp)
                    )
                }
                item {
                    tutoria?.let { Reviews(it.reviews) }
                }

            }
        }
    }
}

@Composable
fun Reviews(reviews: Array<String>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        for(review in reviews) {

                Text(
                    text = review,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(0.dp, 15.dp)
                )

        }

    }

}