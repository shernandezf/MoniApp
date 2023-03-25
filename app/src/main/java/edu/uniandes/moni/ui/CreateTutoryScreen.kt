package edu.uniandes.moni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.viewmodel.TutoriaViewModel
import edu.uniandes.moni.viewmodel.UserViewModel


@Composable
fun CreateTutoryScreen(navController: NavController) {
    val tutoriaViewModel = TutoriaViewModel()
    val scaffoldState = rememberScaffoldState()
    val listOfTeach = listOf("Calculus", "Physics", "Dancing", "Fitness")
    val listOfLocation = listOf("University", "Out")
    var topic = ""
    var tutoryTitle = ""
    var tutoryDescription = ""
    var tutoryLocation = ""
    var fee = ""
    var user = UserViewModel.getUser1()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons( "Create", true, true) },
        bottomBar = {  }
    ) {contentPadding ->

        LazyColumn(modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.Start) {
            item {
                topic = DesplegableTextFieldWithTitle("What do they teach?", listOfTeach)
            }
            item {
                tutoryTitle = TextFieldWithTitle("Title of the tutory", "Insert the title")
            }
            item {
                tutoryDescription = TextFieldWithTitle("Tutory description", "Write about your methodology")
            }
            item {
                tutoryLocation = DesplegableTextFieldWithTitle("Location of the tutory", listOfLocation)
            }
            item {
                fee = TextFieldWithTitle("Fee", "price per hour")
            }
            item {
                Button(onClick = {
                    var inUniversity = false
                    if(tutoryLocation == "University"){
                        inUniversity = true
                    }

                    tutoriaViewModel.writeNewTutoria(tutoryDescription, inUniversity, fee, tutoryTitle, topic, user.email,)
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
                        text = "Create",
                        color = Color.White
                    )

                }
            }
        }



    }

}

@Composable
fun TextFieldWithTitle(title: String, show: String): String {

    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .padding(16.dp, 0.dp)
        .background(color = Color.White),
        verticalArrangement = Arrangement.Center) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
        )
        TextField(
            value = text,
            onValueChange ={ text = it },
            label = { Text(show) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(10.dp)
                .size(300.dp, 40.dp)
        )
    }
    return text
}

@Composable
fun DesplegableTextFieldWithTitle(title: String, list: List<String>): String {
    var selectedItem = ""
    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .background(color = Color.White)
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            color = Color.Black,

            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
        )
        selectedItem = DesplegableTextField(list = list)

    }
    return selectedItem

}

@Composable
fun DesplegableTextField(list: List<String>): String {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    }
    else {
        Icons.Filled.KeyboardArrowDown
    }
    
    Column() {
        OutlinedTextField(value = selectedItem,
            onValueChange = {selectedItem = it},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFiledSize = coordinates.size.toSize() },
            label = { Text(text = "Select item")},
            trailingIcon = {
                Icon(icon, "", Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current){ textFiledSize.width.toDp()})
        ) {
            list.forEach { label ->
                DropdownMenuItem(onClick =
                { selectedItem = label
                    expanded = false }) {
                    Text(text = label,
                        fontSize = 20.sp,
                        color = Color.Black,

                        fontWeight = FontWeight.Bold,
                        modifier = Modifier

                            .padding(20.dp))
                }
            }
            
        }

    }
    return selectedItem
}


