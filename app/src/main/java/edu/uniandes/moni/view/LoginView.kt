package edu.uniandes.moni.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.EmailInput
import edu.uniandes.moni.view.components.MainButton
import edu.uniandes.moni.view.components.PasswordInput
import edu.uniandes.moni.view.components.SecondaryButton
import edu.uniandes.moni.view.theme.main
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.UserViewModel

@Composable
fun LoginMaterialView(navController: NavController) {

    val userViewModel: UserViewModel = UserViewModel()
    var email: String = ""
    var password: String = ""

    Scaffold() { contentPadding ->
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
            Row(modifier = Modifier.padding(top = 25.dp, bottom = 15.dp)) {
                EmailInput() {
                    email = it.text
                }
            }
            Row(modifier = Modifier.padding(bottom = 95.dp)) {
                PasswordInput("Password") {
                    password = it
                }
            }
            Row(modifier = Modifier.padding(bottom = 15.dp)) {
                MainButton(text = "Log In") {
                    println("Email: " + email + " Pass: " + password)
                    userViewModel.loginUser(email, password, navController)
                }
            }
            SecondaryButton(text = "Sign Up") {
                navController.navigate(route = AppScreens.SignUpScreen.route)
            }
        }
    }
}