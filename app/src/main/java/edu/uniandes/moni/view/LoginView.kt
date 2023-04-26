package edu.uniandes.moni.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun LoginMaterialView(navController: NavController,viewModel: UserViewModel) {

    var email = ""
    var password = ""

    var pressedButton = false
    val filledEmail = remember { mutableStateOf(true) }
    val filledPassword = remember { mutableStateOf(true) }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log In",
                textAlign = TextAlign.Center,
                fontFamily = moniFontFamily,
                fontWeight = FontWeight.Bold,
                color = main,
                fontSize = 38.sp,
                modifier = Modifier.padding(top = 15.dp)
            )
            Column(modifier = Modifier.padding(top = 25.dp, bottom = 15.dp)) {
                EmailInput {
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

            Column(modifier = Modifier.padding(bottom = 95.dp)) {
                PasswordInput("Password") {
                    password = it
                    if(pressedButton)
                        filledPassword.value = it.isNotBlank()
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


            val show = remember { mutableStateOf("") }

            Row(modifier = Modifier.padding(bottom = 15.dp)) {
                MainButton(text = "Log In") {
                    pressedButton = true
                    if (email.isBlank() || password.isBlank()) {
                        if (email.isBlank())
                            filledEmail.value = false
                        if (password.isBlank())
                            filledPassword.value = false
                    } else
                        viewModel.loginUser(email, password, navController)

                }
            }


            SecondaryButton(text = "Sign Up") {
                navController.navigate(route = AppScreens.SignUpScreen.route)
            }
        }
    }
}