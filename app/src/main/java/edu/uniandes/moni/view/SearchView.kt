package com.example.monitores

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.uniandes.moni.R
import edu.uniandes.moni.model.Affirmation
import edu.uniandes.moni.model.Datasource
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.TextFieldWithImage
import edu.uniandes.moni.view.theme.MoniTheme

@Composable
fun SearchView(navController: NavController) {
    MoniTheme() {
        SearchList(navController, affirmationList = Datasource().loadAffirmations())
    }
}

@Composable
fun HolePage(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Search", true, true) },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            SearchView(navController)
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
fun TitleWithButtons(centerText: String, canNavigateBack: Boolean, canFilter: Boolean) {

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
            if (canNavigateBack) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(

                        text = stringResource(id = R.string.back),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6,
                        color = Color(53, 109, 230)

                    )
                }

            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = centerText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6,
                    color = Color(23, 48, 102),
                    fontSize = 30.sp
                )

            }
            if (canFilter) {
                TextButton(onClick = { /*TODO*/ }) {

                    Text(
                        text = stringResource(id = R.string.filter),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6,
                        color = Color(53, 109, 230)
                    )

                }

            }
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
        TextFieldWithImage(
            "Search", painterResource(id = R.drawable.search),
            modifier = Modifier.weight(1f)
        )
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


