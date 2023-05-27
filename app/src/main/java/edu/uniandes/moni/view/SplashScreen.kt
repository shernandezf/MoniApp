package edu.uniandes.moni.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.uniandes.moni.R
import edu.uniandes.moni.navigation.AppScreens
import kotlinx.coroutines.delay


@Composable
fun splashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack()
        navController.navigate(AppScreens.LoginScreen.route)
    }
    splash()
}

@Composable
fun splash() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(500.dp, alignment = Alignment.CenterVertically),


        ) {
        Image(
            painter = painterResource(id = R.drawable.monilogo),
            contentDescription = "app logo"
        )
        Modifier.size(150.dp, 150.dp)

    }
}
