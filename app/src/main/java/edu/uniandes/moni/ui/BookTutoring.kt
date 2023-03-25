package edu.uniandes.moni.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.navigation.AppScreens

@Composable
fun BookTutoringScreen(navController: NavController, tutoryTitle: String?, description: String?, rate: String?) {

    if(tutoryTitle != null)
        Log.d("TAG", tutoryTitle)
    else
        Log.d("TAG", "No se encontro esa mierdaaaaaaaaa")

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons( "Book", true, true) },
        bottomBar = { }
    ) {contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .padding(15.dp)
        ) {
            if(tutoryTitle != null && description != null && rate != null)
                Column() {
                    TutoringDescription(tutoryTitle, description)
                    var commentary = TextFieldWithTitle("Commentaries for the tutor", "I'd like to learn about...")
                    BoxWithRows("Hourly Rate", rate,
                    "Date", "dd/mm/yyyy",
                    "time","HH:MM",
                    "Place", "Describe the place")
                    Button(onClick = {
                        navController.navigate(route = AppScreens.SignUpScreen.route)

                    },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(23,48,102)),
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp)
                            .size(300.dp, 40.dp))

                    {
                        Text(
                            text = "Confirm",
                            color = Color.White
                        )

                    }
                }

        }
    }
}

@Composable
fun TutoringDescription(tutoryTitle: String, description: String) {
    Box(
        modifier = Modifier
            .background(Color(247, 247, 248))
            .padding(20.dp)
    ) {
        Column() {
            Text(
                text = tutoryTitle,
                fontSize = 30.sp,
                color = Color.Black,

                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            )

            Text(
                text = "Description",
                fontSize = 20.sp,
                color = Color.Black,

                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(5.dp)
            )

            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Black,

                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(5.dp)
            )
            
        }
        
    }
}


@Composable
fun BoxWithRows(title1: String, show1: String,
                title2: String, show2: String,
                title3: String, show3: String,
                title4: String, show4: String) {
    Box(modifier = Modifier
        .background(Color(247, 247, 248))
        .padding(20.dp)
        .width(300.dp)) {
        Column() {
            RowWithTitleText(title1, show1)
            val date = RowWithTitleTextField(title2, show2)
            val time = RowWithTitleTextField(title3, show3)
            val place = RowWithTitleTextField(title4, show4)
        }

    }
}

@Composable
fun RowWithTitleTextField(title: String, show: String): String {
    var text by remember { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )
        TextField(
            value = text,
            onValueChange ={ text = it },
            label = {
                Text(text = show,
                    fontSize = 7.sp) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(10.dp)
                .size(200.dp, 20.dp)
        )

    }
    return text

}
@Composable
fun RowWithTitleText(title: String, show: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = title,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )

        Text(
            text = show,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(20.dp)
        )

    }

}

