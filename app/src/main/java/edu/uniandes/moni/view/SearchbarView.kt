package edu.uniandes.moni.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.view.theme.azul3
import edu.uniandes.moni.view.theme.main

@Composable
fun SingleItemCard(name: String) {
    Card(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name)
        }
    }
}

@Composable
fun TopBar(
    onsearchIconClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
    ) {
        Text(
            text = "Search",
            color = main,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)

        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp)
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.TopEnd
        )

        {
            IconButton(onClick = onsearchIconClick) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(20.dp),
                    tint = azul3
                )
            }
        }


    }
}

@Composable
fun searchAppBar(
    onClosedIconClick: () -> Unit,
    searchText: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchText,
        onValueChange = {
            onTextChange(it)
        },
        textStyle = TextStyle(
            color = Color(0xFF173066),
            fontSize = 18.sp
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search Icon",
                tint = azul3
            )
        },
        trailingIcon = {
            IconButton(onClick = onClosedIconClick) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = azul3
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.White,
            focusedBorderColor = Color.White
        )
    )
}