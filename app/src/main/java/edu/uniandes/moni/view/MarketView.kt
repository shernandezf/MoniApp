package edu.uniandes.moni.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
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
import edu.uniandes.moni.model.dao.TutoringDAO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.TutoringViewModel
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.flow.distinctUntilChanged


private val tutoringViewModel: TutoringViewModel = TutoringViewModel()

@Composable
fun MarketScreen(navController: NavController) {


    val scaffoldState = rememberScaffoldState()
    val tutoringList = TutoringViewModel.getTutoringList()
    val interestLists1 = createNewList(UserViewModel.getUser().interest1, tutoringList)
    val interestLists2 = createNewList(UserViewModel.getUser().interest2, tutoringList)

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
                        interestLists1,
                        "Based on your main interest",
                        navController
                    ) {
//                        onLoadMore()
                    }
                }
                item {
                    ScrollableRowWithCards(
                        interestLists2,
                        "Other things you may like",
                        navController
                    ) {
//                        onLoadMore()
                    }
                }
                item {
                    ScrollableRowWithCards(
                        tutoringList,
                        "All",
                        navController
                    ) {
//                        onLoadMore()
                    }
                }
            }

        }
    }
}

fun createNewList(interest: String, tutoringList: List<TutoringDAO>): List<TutoringDAO> {
    val newList: MutableList<TutoringDAO> = mutableListOf()
    for (tutoring in tutoringList) {
        val topic = tutoring.topic
        if (topic == interest) {
            newList.add(tutoring)
        }
    }
    return newList
}

@Composable
fun ScrollableRowWithCards(
    tutoringList: List<TutoringDAO>,
    title1: String,
    navController: NavController,
    onLoadMore: () -> Unit
) {
    Column {
        Text(
            text = title1,
            fontSize = 36.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 15.dp)
        )
        val listState = rememberLazyListState()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (tutoring in tutoringList) {
                val title: String = tutoring.title
                val price: String = tutoring.price
                val description = tutoring.description
                val tutorEmail = tutoring.tutorEmail

                var id2 = R.drawable.gym
                if (tutoring.topic == "Calculus" || tutoring.topic == "Physics")
                    id2 = R.drawable.school
                item {
                    if (tutorEmail != null) {
                        TutoringCard(
                            title,
                            painterResource(id = id2),
                            price,
                            description,
                            tutoring.id,
                            tutorEmail,

                            navController
                        )
                    }
                }
            }
        }
        InfiniteListHandler(listState = listState) {
            onLoadMore()
        }
    }
}

fun onLoadMore() {
    tutoringViewModel.getTutoringsRange()
}

@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 0,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .distinctUntilChanged()
            .collect {
                onLoadMore()
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
    tutorEmail: String,
    navController: NavController
) {

    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            navController.navigate(route = AppScreens.BookTutoringScreen.route + "/$id/$title/$description/$price/$tutorEmail")
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

