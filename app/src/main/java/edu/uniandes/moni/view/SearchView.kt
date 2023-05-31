package com.example.monitores

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.R
import edu.uniandes.moni.model.Affirmation
import edu.uniandes.moni.model.Datasource
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.utils.CacheManager
import edu.uniandes.moni.view.MainActivity
import edu.uniandes.moni.view.cantidad_actividad
import edu.uniandes.moni.view.cantidad_bookeados
import edu.uniandes.moni.view.components.CreateDialog
import edu.uniandes.moni.view.theme.MoniTheme
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.TutoringViewModel

private  var analytics= Firebase.analytics
var cantidad_Physics:Int=0
var cantidad_Calculus:Int=0
var cantidad_Dancing:Int=0
var cantidad_Fitness:Int=0
var cantidadTotal:Int=0

@Composable
fun searchTheme(navController: NavController,tutoringViewModel: TutoringViewModel){
    val searchText by tutoringViewModel.searchText.collectAsState()

    val tutorings by tutoringViewModel.tutorings.collectAsState()

    var tutorias=tutorings
    var valido by remember {
        mutableStateOf(false)
    }

    var listcaches = rememberSaveable { mutableStateOf(listOf<String>()) }

    val i = remember { mutableStateOf(1000) }
    val isSearching by tutoringViewModel.isSearching.collectAsState()
    analytics.logEvent("searchValues"){
            param("cantidadPhysics", cantidad_Physics.toLong())
            param("cantidadCalculus", cantidad_Calculus.toLong())
            param("cantidadDancing", cantidad_Dancing.toLong())
            param("cantidadFitness", cantidad_Fitness.toLong())
            param("cantidadTotal", cantidadTotal.toLong())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchText,
            onValueChange = {tutoringViewModel.onSearchTextChange(it)
                text=it.lowercase() },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text(text="Insert the topic you are looking for, ex: calculus" )}
        )
        if (!listcaches.value.isEmpty()&&text.isEmpty()){
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                items(listcaches.value) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Transparent)
                        .clickable {
                            tutoringViewModel.onSearchTextChange(it)
                            text = it.lowercase()
                        }) {

                        Text(
                            text = it,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.h6,
                            fontSize = 20.sp
                        )
                        Divider(color = Color.Black, thickness = 1.dp)
                    }
                }
            }
        }
        if (text=="physics"){
            tutoringViewModel.getAllTutoringstopic(text){
                i.value=it
            }
            cantidad_Physics++
            cantidadTotal++
            valido=true
            if (!listcaches.value.contains("Physics")&& MainActivity.internetStatus == "Available"){
                listcaches.value=listcaches.value+"Physics"
            }

        }else if(text=="calculus"){
            tutoringViewModel.getAllTutoringstopic(text){
                i.value=it
            }
            cantidad_Calculus++
            cantidadTotal++
            valido=true
            if (!listcaches.value.contains("Calculus")&& MainActivity.internetStatus == "Available"){
                listcaches.value=listcaches.value+"Calculus"
            }

        }else if(text=="dancing"){
            tutoringViewModel.getAllTutoringstopic(text){
                i.value=it
            }
            cantidad_Dancing++
            cantidadTotal++
            valido=true
            if (!listcaches.value.contains("Dancing")&& MainActivity.internetStatus == "Available"){
                listcaches.value=listcaches.value+"Dancing"
            }

        }else if(text=="fitness"){
            tutoringViewModel.getAllTutoringstopic(text){
                i.value=it
            }
            cantidad_Fitness++
            cantidadTotal++
            valido=true
            if (!listcaches.value.contains("Fitness")&& MainActivity.internetStatus == "Available"){
                listcaches.value=listcaches.value+"Fitness"
            }
        }else{
            tutorias=emptyList()
            valido=false
        }
        Spacer(modifier = Modifier.height(16.dp))
        if(isSearching && text.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            if (valido){
                if (i.value==3) {
                    CreateDialog(
                        "No internet connection",
                        "Please check your internet connection"
                    ) { i.value=1000
                        tutoringViewModel.onSearchTextChange("")
                        text=""}
                }else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                        items(tutorias) { tutor ->
                            Column(modifier = Modifier
                                .padding(16.dp)
                                .background(Color.Transparent)
                                .clickable {
                                    var id = tutor.id
                                    var title = tutor.title
                                    var description = tutor.description
                                    var price = tutor.price
                                    var email = tutor.tutorEmail
                                    navController.navigate(route = AppScreens.BookTutoringScreen.route + "/$id/$title/$description/$price/$email") {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                                inclusive = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {

                                Text(
                                    text = tutor.title,
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.h6,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = tutor.description,
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.h6,
                                    fontSize = 16.sp
                                )
                            }
                            Divider(color = Color.Blue, thickness = 1.dp)
                        }

                    }
                }
            }else if(!valido && text.isNotEmpty()){
                Text(
                    text = "The introduced text is not within the topics.",
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    color = Color.Red,
                    fontFamily = moniFontFamily,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
        }
    }
}
@Composable
fun HolePage(navController: NavController,tutoringViewModel: TutoringViewModel) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Search") },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            searchTheme(navController,tutoringViewModel)
        }

    }

}

@Composable
fun ButtonWithImage(navController: NavController, imageId: Int) {
    var checked = false
    var route = AppScreens.SignUpScreen.route
    if (imageId == R.drawable.plus) {
        route = AppScreens.CreateTutoryScreen.route
    } else if (imageId == R.drawable.home) {
        route = AppScreens.MarketScreen.route
    } else if (imageId == R.drawable.search) {
        route = AppScreens.SearchScreen.route
    } else if (imageId == R.drawable.calendar) {
        route = AppScreens.CalendarScreen.route
    } else if (imageId == R.drawable.profile) {
        route = AppScreens.ProfileScreen.route
    }
    IconToggleButton(checked = checked,
        onCheckedChange = {
            checked = it
            navController.navigate(route = route)
        }) { //2
        Icon(
            painter = painterResource( //3
                if (checked) imageId
                else imageId
            ),

            contentDescription = //4
            if (checked) "Añadir a marcadores"
            else "Quitar de marcadores",
            tint = Color(0xFF26C6DA), //5
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun BottomPart(navController: NavController) {
    BottomAppBar(backgroundColor = Color.White) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .weight(1f)
                .size(200.dp)
        ) {
            ButtonWithImage(navController, R.drawable.home)
            ButtonWithImage(navController, R.drawable.search)
            ButtonWithImage(navController, R.drawable.plus)
            ButtonWithImage(navController, R.drawable.calendar)
            ButtonWithImage(navController, R.drawable.profile)
        }

    }

}

@Composable
fun TitleWithButtons(centerText: String) {

    TopAppBar(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = centerText,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
                color = Color(23, 48, 102),
                fontSize = 30.sp
            )
        }
    }


}


@Composable
fun SearchList(
    navController: NavController,
    affirmationList: List<Affirmation>,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        TextFieldWithImage(
//            "Search", painterResource(id = R.drawable.search),
//            modifier = Modifier.weight(1f)
//        )
        LazyColumn(horizontalAlignment = Alignment.Start) {
            items(affirmationList) { affirmation ->
                MonitorCard(affirmation)
            }
        }

    }

}

@Composable
fun MonitorCard(affirmation: Affirmation) {
    Row(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(affirmation.imageResourceId),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier.size(100.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = LocalContext.current.getString(affirmation.nameId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
                fontSize = 20.sp
            )
            Text(
                text = LocalContext.current.getString(affirmation.descriptionId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp
            )
        }
    }
}