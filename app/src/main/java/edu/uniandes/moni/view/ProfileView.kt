package edu.uniandes.moni.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.monitores.BottomPart
import com.example.monitores.TitleWithButtons
import edu.uniandes.moni.R
import edu.uniandes.moni.model.UserModel
import edu.uniandes.moni.navigation.AppScreens
import edu.uniandes.moni.view.components.CancelTextButton
import edu.uniandes.moni.view.components.CreateDialog
import edu.uniandes.moni.view.components.MainButton
import edu.uniandes.moni.view.components.PasswordInput
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.viewmodel.UserViewModel


@Composable
fun ProfileScreen(navController: NavController,userViewModel: UserViewModel) {
    val scaffoldState = rememberScaffoldState()
    var currentPassword = ""
    var newPassword = ""
    var confirmPassword = ""

    var pressedButton = false
    val filledCurrentPassword = remember { mutableStateOf(true) }
    val filledNewPassword = remember { mutableStateOf(true) }
    val filledConfirmPassword = remember { mutableStateOf(true) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Profile", false, false) },
        bottomBar = { BottomPart(navController) }
    ) { contentPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .padding(15.dp)
                .fillMaxHeight()
        )
        {
            item {
                ImageWithName(
                    name = UserViewModel.getUser().name,
                    image = painterResource(R.drawable.profile_photo)
                )
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Current password",
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                    Column(modifier = Modifier.padding(bottom = 15.dp)) {
                        PasswordInput("Current password") {
                            currentPassword = it
                            if (pressedButton)
                                filledCurrentPassword.value = it.isNotBlank()
                        }
                        println(filledCurrentPassword.value)
                        if (!filledCurrentPassword.value) {
                            Text(
                                text = "Please fill the current password",
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = Color.Red,
                                fontFamily = moniFontFamily,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }
                    }


                    Text(
                        text = "New password",
                        fontFamily = moniFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(15.dp)
                    )
                    Column(modifier = Modifier.padding(bottom = 15.dp)) {
                        PasswordInput("New password") {
                            newPassword = it
                            if (pressedButton)
                                filledNewPassword.value = it.isNotBlank()
                        }
                        if (!filledNewPassword.value) {
                            Text(
                                text = "Please fill the new password",
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = Color.Red,
                                fontFamily = moniFontFamily,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(bottom = 15.dp)) {
                        PasswordInput("Confirm password") {
                            confirmPassword = it
                            if (pressedButton)
                                filledConfirmPassword.value = it.isNotBlank()
                        }
                        if (!filledConfirmPassword.value) {
                            Text(
                                text = "Please confirm the password",
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = Color.Red,
                                fontFamily = moniFontFamily,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }
                    }

                    val i = remember { mutableStateOf(10) }

                    Row(modifier = Modifier.padding(bottom = 15.dp, top = 25.dp)) {
                        MainButton(text = "Save changes") {
                            pressedButton = true

                            if (currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
                                if (currentPassword.isBlank())
                                    filledCurrentPassword.value = false
                                if (newPassword.isBlank())
                                    filledNewPassword.value = false
                                if (confirmPassword.isBlank())
                                    filledConfirmPassword.value = false
                            } else {
                                userViewModel.changePassword(
                                    currentPassword,
                                    newPassword,
                                    confirmPassword
                                ) {
                                    i.value = it
                                }
                            }
                        }
                    }
                    if(i.value == 0)
                        CreateDialog("Change password", "Password changed successfully") {
                            i.value = 10
                            currentPassword = ""
                            newPassword = ""
                            confirmPassword = ""
                            pressedButton = false
                        }
                }
            }

            item {
                CancelTextButton("Log Out") {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                    UserViewModel.setUser(UserModel())
                }
            }
        }
    }
}

@Composable
fun ImageWithName(name: String, image: Painter) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 30.dp)
    ) {

        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = name,
            fontSize = 20.sp,
            fontFamily = moniFontFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(0.dp, 20.dp)
        )

    }

}

