package com.example.monitores

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import edu.uniandes.moni.domain.Affirmation
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.data.Datasource
import edu.uniandes.moni.ui.SignUpScreen
import edu.uniandes.moni.ui.TextFieldWithImage
import edu.uniandes.moni.ui.theme.MoniTheme
import edu.uniandes.moni.R
import edu.uniandes.moni.ui.LogInScreen

@Composable
fun SearchView() {
    MoniTheme() {
        SearchList(affirmationList = Datasource().loadAffirmations())
    }
}

@Composable
fun HolePage() {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons( "Search", true, true) },
        bottomBar = { BottomPart() }
    ) {contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)) {
            SearchView()
        }

    }

}

@Composable
fun ButtonWithImage(imageId: Int) {
    var checked = false
    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) { //2
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
fun BottomPart() {
    BottomAppBar(backgroundColor = Color.White) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .weight(1f)
            .size(200.dp)) {
            ButtonWithImage(R.drawable.home)
            ButtonWithImage(R.drawable.search)
            ButtonWithImage(R.drawable.plus)
            ButtonWithImage(R.drawable.calendar)
            ButtonWithImage(R.drawable.profile)
        }

    }

}

@Composable
fun TitleWithButtons(centerText: String, canNavigateBack: Boolean, canFilter: Boolean) {

    TopAppBar(backgroundColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp),
            ) {
        Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)) {
            if(canNavigateBack) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(

                        text = stringResource(id = R.string.back),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6,
                        color = Color(53,109,230)

                    )
            }

            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = centerText,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.h6,
                    color = Color(23,48,102),
                    fontSize = 30.sp
                )
                
            }
            if(canFilter) {
                TextButton(onClick = { /*TODO*/ }) {

                    Text(
                        text = stringResource(id = R.string.filter),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.h6,
                        color = Color(53,109,230)
                    )

                }

            }
        }
    }
    

}



@Composable
fun SearchList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextFieldWithImage("Search", painterResource(id = R.drawable.search),
            modifier= Modifier.weight(1f))
        LazyColumn(horizontalAlignment = Alignment.Start) {
            items(affirmationList) { affirmation ->
                MonitorCard(affirmation)
            }
        }
        
    }

}

@Composable
fun MonitorCard(affirmation: Affirmation) {
    Row (modifier = Modifier.padding(16.dp)){
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoniTheme() {

        LogInScreen()

    }
}

