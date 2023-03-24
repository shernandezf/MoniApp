package edu.uniandes.moni.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.uniandes.moni.R
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.createUser


var userId: String? = ""

@Composable
fun SignUpScreen(navController: NavController, modifier: Modifier = Modifier) {

    val texts: List<String> = listOf(stringResource(R.string.name_text_field),stringResource(R.string.email_text_field), stringResource(R.string.password_text_field))
    val images: List<Painter> = listOf(painterResource(id = R.drawable.account_box_outline),
        painterResource(id = R.drawable.mail), painterResource(id = R.drawable.no_see))

    Scaffold() {contentPadding ->Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(contentPadding)
        .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val columns = ColumnWithTextFieldAndTitle("Sign Up" ,texts, images
        )

        Button(onClick = {
            val name: String = columns[0];
            val email: String = columns[1];
            val password: String = columns[2];
            userId = createUser(name, email, password)
            navController.navigate(route = AppScreens.SearchScreen.route)

        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(23,48,102)),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .size(300.dp, 40.dp))

        {
            Text(
                text = "Sign up",
                color = Color.White
            )

        }

        Button(onClick = {
            navController.navigate(route = AppScreens.LoginScreen.route)

        },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .size(300.dp, 40.dp))

        {
            Text(
                text = "Already registered?",
                color = Color.White
            )

        }

    }

    }


}


@Composable
fun ColumnWithTextFieldAndTitle(title: String, texts: List<String>, images: List<Painter>): List<String> {
    var states: List<String> = mutableListOf();
    Column(modifier = Modifier
        .padding(16.dp)
        .background(color = Color.White)
        .padding(40.dp),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = title,
            fontSize = 36.sp,
            color = Color(23, 48, 102),

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        var i = 0;
        while(i < texts.size) {
            val text = texts[i];
            val image = images[i];
            val state: String = TextFieldWithImage(text, image)
            states += state
            i ++
        }
    }
    return states;
}


@Composable
fun TextFieldWithImage(show: String, image: Painter, modifier: Modifier = Modifier): String {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange ={ text = it },
        label = { Text(show) },
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
        },
        modifier = Modifier
            .padding(10.dp)
            .size(300.dp, 60.dp)
    )
    return text;
}


