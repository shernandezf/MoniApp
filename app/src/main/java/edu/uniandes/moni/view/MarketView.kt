package edu.uniandes.moni.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.SessionViewModel
import edu.uniandes.moni.viewmodel.TutoringViewModel
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.random.Random


@Composable
fun MarketScreen(
    navController: NavController,
    tutoringViewModel: TutoringViewModel,
    sessionViewModel: SessionViewModel
) {

    val scaffoldState = rememberScaffoldState()
    var tutoringList: MutableList<TutoringDTO> by remember { mutableStateOf(mutableListOf()) }
    tutoringViewModel.getAllTutorings {
        tutoringList = it
    }

    val interestLists1 = createNewList(
        UserViewModel.getUser().interest1,
        tutoringList
    )
    val interestLists2 = createNewList(
        UserViewModel.getUser().interest2,
        tutoringList
    )
    var highlyRequestedTutoringList: MutableList<TutoringDTO> by remember {
        mutableStateOf(
            mutableListOf()
        )
    }

    var highlyRequestedTutoringId: String by remember { mutableStateOf("") }
    sessionViewModel.getRankTutoring {
        highlyRequestedTutoringId = it
    }

    LaunchedEffect(highlyRequestedTutoringId) {
        if (highlyRequestedTutoringId.isNotEmpty()) {
            tutoringViewModel.getTutoringById(highlyRequestedTutoringId) {
                highlyRequestedTutoringList.add(it)
            }

        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Market") },
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
                    ) {}
                }
                item {
                    ScrollableRowWithCards(
                        interestLists2,
                        "Other things you may like",
                        navController
                    ) {}
                }
                item {
                    ScrollableRowWithCards(
                        highlyRequestedTutoringList,
                        "Highly requested",
                        navController
                    ) {}
                }
                item {
                    ScrollableRowWithCards(
                        tutoringList,
                        "All",
                        navController
                    ) {}
                }
            }

        }
    }
}


fun createNewList(interest: String, tutoringList: List<TutoringDTO>): List<TutoringDTO> {
    val newList: MutableList<TutoringDTO> = mutableListOf()
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
    tutoringList: List<TutoringDTO>,
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
                val numeroRandom = Random.nextInt(1, 4)
                var id2 = 0
                when (numeroRandom) {
                    1 -> {
                        id2 = when (tutoring.topic) {
                            "Calculus" -> {
                                R.drawable.calculo1
                            }

                            "Physics" -> {
                                R.drawable.fisica1
                            }

                            "Dancing" -> {
                                R.drawable.dance1
                            }

                            else -> {
                                R.drawable.fit1
                            }
                        }
                    }

                    2 -> {
                        id2 = when (tutoring.topic) {
                            "Calculus" -> {
                                R.drawable.calculo2
                            }

                            "Physics" -> {
                                R.drawable.fisica2
                            }

                            "Dancing" -> {
                                R.drawable.dance2
                            }

                            else -> {
                                R.drawable.fit2
                            }
                        }
                    }

                    else -> {
                        id2 = when (tutoring.topic) {
                            "Calculus" -> {
                                R.drawable.calculo3
                            }

                            "Physics" -> {
                                R.drawable.fisica3
                            }

                            "Dancing" -> {
                                R.drawable.dance3
                            }

                            else -> {
                                R.drawable.fit3
                            }
                        }
                    }
                }
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

    val maxTitleLength = 15

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
            text = if (title.length > maxTitleLength) {
                title.trimToLength(maxTitleLength) + "..." // Agregar puntos suspensivos al final
            } else {
                title
            },
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

fun String.trimToLength(maxLength: Int): String {
    return if (length > maxLength) {
        substring(0, maxLength)
    } else {
        this
    }
}

