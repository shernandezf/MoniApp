package edu.uniandes.moni.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons

@Composable
fun BookTutoringScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons( "Search", true, true) },
        bottomBar = { }
    ) {contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)) {
        }
    }
}

