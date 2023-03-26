package edu.uniandes.moni.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.R
import edu.uniandes.moni.data.TutoringDAO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.TutoriaViewModel
import edu.uniandes.moni.viewmodel.UserViewModel

@Composable
fun MarketScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val tutorias = TutoriaViewModel.getTutories()
    val interest1 = UserViewModel.getUser1().interest1
    val interest2 = UserViewModel.getUser1().interest2

    val listInterest1 = createNewList(interest1, tutorias)
    val listInterest2 = createNewList(interest2, tutorias)


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Market", true, true) },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .padding(15.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                item {
                    ScrollableRowWithCards(
                        listInterest1,
                        "Based on your main interest",
                        navController
                    )
                }
                item {
                    ScrollableRowWithCards(
                        listInterest2,
                        "Other things you may like",
                        navController
                    )
                }
                item {
                    ScrollableRowWithCards(tutorias, "All", navController)
                }
            }

        }
    }
}

fun createNewList(interest: String, tutories: List<TutoringDAO>): List<TutoringDAO> {
    var newList: MutableList<TutoringDAO> = mutableListOf()
    for (tutory in tutories) {
        val topic = tutory.topic
        if (topic == interest) {
            newList.add(tutory)
        }
    }
    return newList

}

@Composable
fun ScrollableRowWithCards(
    tutories: List<TutoringDAO>,
    title1: String,
    navController: NavController
) {
    Column() {
        Text(
            text = title1,
            fontSize = 36.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (tutory in tutories) {
                val title: String = tutory.title
                val price: String = tutory.price
                val description = tutory.description
                var id2 = R.drawable.gym
                if (tutory.topic == "Calculus" || tutory.topic == "Physics")
                    id2 = R.drawable.school
                item {
                    TutoringCard(
                        title,
                        painterResource(id = id2),
                        price,
                        description,
                        tutory.id,
                        navController
                    )
                }

            }
        }

    }


}

@Composable
fun TutoringCard(
    title: String,
    image: Painter,
    price: String,
    description: String,
    id: String,
    navController: NavController
) {

    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            Log.d("TAG", "$title titulo en market")
            navController.navigate(route = AppScreens.BookTutoringScreen.route + "/$id/$title/$description/$price")
        }) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 5.dp)
        )
        Text(
            text = price,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 5.dp)
        )

    }
}

