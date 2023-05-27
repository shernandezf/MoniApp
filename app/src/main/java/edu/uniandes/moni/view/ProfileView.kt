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
        topBar = { TitleWithButtons("Profile") },
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
                                if(newPassword.length>=6||confirmPassword.length>=6) {
                                    userViewModel.changePassword(
                                        currentPassword,
                                        newPassword,
                                        confirmPassword
                                    ) {
                                        i.value = it
                                    }
                                }else{
                                    i.value=5
                                }
                            }
                        }
                    }
                    if(i.value == 0) {
                        CreateDialog("Change password", "Password changed successfully") {
                            i.value = 10
                            pressedButton = false
                            navController.navigate(route = AppScreens.LoginScreen.route)
                        }
                    }
                    if(i.value == 1) {
                        CreateDialog("Something went wrong", "The current password is not correct") {
                            i.value = 10
                            pressedButton = false
                        }
                    }
                    if(i.value == 2) {
                        CreateDialog("Something went wrong", "password missmatching") {
                            i.value = 10
                            pressedButton = false
                        }
                    }
                    if(i.value == 3) {
                        CreateDialog("Something went wrong", "fill all the fields") {
                            i.value = 10
                            pressedButton = false
                        }
                    }
                    if(i.value == 4) {
                        CreateDialog("Something went wrong", "There is no internet connection, please try it later") {
                            i.value = 10
                            pressedButton = false
                        }
                    }
                    if(i.value == 5) {
                        CreateDialog("Something went wrong", "passwords have to have at least 6 characters") {
                            i.value = 10
                            pressedButton = false
                        }
                    }
                }
            }

            item {
                CancelTextButton("Log Out") {
                    navController.popBackStack(route = AppScreens.LoginScreen.route,inclusive = false)
                    //navController.popBackStack(route = AppScreens.ProfileScreen.route,inclusive = false)
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

