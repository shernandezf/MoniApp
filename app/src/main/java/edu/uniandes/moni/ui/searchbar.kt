package edu.uniandes.moni.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.ui.theme.azul1
import edu.uniandes.moni.ui.theme.azul3
@Composable
fun SearchBarTopic(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        TopBar()
    }
}
@Composable
fun TopBar(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
    ) {
        Text(
            text="Search",
            color = azul1,
            fontSize=30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)

        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp)
            .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.TopEnd)

        {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(20.dp),
                    tint=azul3
                )
            }
        }


    }
}
@Composable
fun searchAppBar(){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = "",
        onValueChange ={},
    leadingIcon = {
        Icon(imageVector = Icons.Filled.Search, contentDescription ="search Icon",tint=azul3)
    },
    trailingIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription ="Close Icon" ,tint=azul3)
        }
    },
    colors=TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = Color.White,
        focusedBorderColor = Color.White
    )
    )
}
@Preview
@Composable
fun prev(){
    SearchBarTopic()
}