package edu.uniandes.moni.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.*
import edu.uniandes.moni.view.theme.main
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupMaterialView(navController: NavController,viewModel: UserViewModel) {
    val userViewModel = viewModel
    var name = ""
    var email = ""
    var password = ""
    var passwordRepeat = ""
    var interest1 = ""
    var interest2 = ""

    var pressedButton = false
    val filledName = remember { mutableStateOf(true) }
    val filledEmail = remember { mutableStateOf(true) }
    val filledPassword = remember { mutableStateOf(true) }
    val filledPasswordRepeat = remember { mutableStateOf(true) }
    val filledInterest1 = remember { mutableStateOf(true) }
    val filledInterest2 = remember { mutableStateOf(true) }
    val matchPassword = remember { mutableStateOf(true) }

    Scaffold() { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Sign Up",
                    textAlign = TextAlign.Center,
                    fontFamily = moniFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = main,
                    fontSize = 38.sp,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }

            item {
                Column(modifier = Modifier.padding(top = 25.dp, bottom = 15.dp)) {
                    NameInput() {
                        name = it.text
                        if(pressedButton)
                            filledName.value = it.text.isNotBlank()
                    }
                    if(!filledName.value) {
                        Text(
                            text = "Please fill the name",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(bottom = 15.dp)) {
                    EmailInput() {
                        email = it.text
                        if(pressedButton)
                            filledEmail.value = it.text.isNotBlank()
                    }
                    if(!filledEmail.value) {
                        Text(
                            text = "Please fill the email",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(bottom = 15.dp)) {
                    PasswordInput("Password") {
                        password = it
                        if(pressedButton)
                            filledPassword.value = it.isNotBlank()
                        if (passwordRepeat.isNotBlank()){
                            matchPassword.value = passwordRepeat==password
                        }
                    }
                    if(!filledPassword.value) {
                        Text(
                            text = "Please fill the password",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(bottom = 25.dp)) {
                    PasswordInput("Repeat your password") {
                        passwordRepeat = it
                        if(pressedButton) {
                            filledPasswordRepeat.value = it.isNotBlank()
                        }
                        matchPassword.value = passwordRepeat==password
                    }
                    if(!filledPasswordRepeat.value) {
                        Text(
                            text = "Please fill the password confirmation",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    if(!matchPassword.value){
                        Text(
                            text = "This password do not match the previously typed password",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(bottom = 25.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Interests: ",
                        textAlign = TextAlign.Start,
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0, 0, 0),
                        fontSize = 18.sp,
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(bottom = 15.dp)) {
                    Select(
                        label = "Select interest 1",
                        optionList = mutableListOf("Calculus", "Physics", "Dancing", "Fitness"),
                        interest1
                    ) {
                        interest1 = it
                        if(pressedButton)
                            filledInterest1.value = it.isNotBlank()
                    }
                    if(!filledInterest1.value) {
                        Text(
                            text = "Please fill the first interest",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }

                }
            }

            item {
                Column(modifier = Modifier.padding(bottom = 55.dp)) {
                    Select(
                        label = "Select interest 2",
                        optionList = mutableListOf("Calculus", "Physics", "Dancing", "Fitness"),
                        interest2
                    ) {
                        interest2 = it
                        if(pressedButton)
                            filledInterest2.value = it.isNotBlank()
                    }
                    if(!filledInterest2.value) {
                        Text(
                            text = "Please fill the second interest",
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = Color.Red,
                            fontFamily = moniFontFamily,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

            item {

                //val coroutineScope = rememberCoroutineScope()
                val i = remember { mutableStateOf(1000) }
                val mensajeErr = remember { mutableStateOf("") }
                Row(modifier = Modifier.padding(bottom = 15.dp)) {
                    MainButton(text = "Sign Up") {
                        pressedButton = true
                        if(name.isBlank() || email.isBlank() || password.isBlank() || passwordRepeat.isBlank() || interest1.isBlank() || interest2.isBlank()) {
                            if(name.isBlank())
                                filledName.value = false
                            if(email.isBlank())
                                filledEmail.value = false
                            if(password.isBlank())
                                filledPassword.value = false
                            if(passwordRepeat.isBlank())
                                filledPasswordRepeat.value = false
                            if(interest1.isBlank())
                                filledInterest1.value = false
                            if(interest2.isBlank())
                                filledInterest2.value = false
                        }
                        else {
                            if (matchPassword.value) {

                                    userViewModel.registerUser(name, email, password, interest1, interest2) {numero,mensaje->
                                        if (numero == 0) {
                                            navController.navigate(route = AppScreens.MarketScreen.route)
                                        }
                                        else if (numero == 3) {
                                            i.value = 3
                                        }else if (numero==1){
                                            i.value = 1
                                            mensajeErr.value=mensaje
                                        }
                                    }

                            }
                        }

                    }
                }
                if(i.value == 3) {
                    CreateDialog("No internet connection", "Please check your internet connection") {
                        i.value = 10000
                    }
                }
                if(i.value == 1){
                    Text(
                        text = mensajeErr.value,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        color = Color.Red,
                        fontFamily = moniFontFamily,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }

            item {
                TextButton(text = "Do you already have an account? Log In") {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            }


        }
    }
}