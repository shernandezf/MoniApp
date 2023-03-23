package edu.uniandes.moni.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.R


@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        ColumnWithTextFieldAndTitle(
            stringResource(R.string.name_text_field), painterResource(id = R.drawable.account_box_outline),
            stringResource(R.string.email_text_field), painterResource(id = R.drawable.mail),
            stringResource(R.string.password_text_field), painterResource(id = R.drawable.no_see)
        )

        Button(onClick = {},
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

    }

}


@Composable
fun ColumnWithTextFieldAndTitle(show1: String, image1: Painter,
                                show2: String, image2: Painter,
                                show3: String, image3: Painter
) {
    Column(modifier = Modifier
        .padding(16.dp)
        .background(color = Color.White)
        .padding(40.dp),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Sign up",
            fontSize = 36.sp,
            color = Color(23, 48, 102),

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        )
        TextFieldWithImage(show1, image1)
        TextFieldWithImage(show2, image2)
        TextFieldWithImage(show3, image3)
    }
}


@Composable
fun TextFieldWithImage(show: String, image: Painter, modifier: Modifier = Modifier) {
    val text = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = text.value,
        onValueChange ={ text.value = it },
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
            .size(300.dp, 40.dp)
    )
}

