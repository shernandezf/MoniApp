package edu.uniandes.moni.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
fun ProfileScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TitleWithButtons("Profile", true, true) },
        bottomBar = { BottomPart(navController) }
    ) {contentPadding ->
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
                ImageWithName(name = UserViewModel.getUser().name, image = painterResource(R.drawable.profile_photo))
            }

            item {
                ChangePassword()
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
fun ChangePassword() {
    val userViewModel = UserViewModel()
    var currentPassword = ""
    var newPassword = ""
    var confirmPassword = ""


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Current password",
            fontFamily = moniFontFamily,
            fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 15.dp))
        Row(modifier = Modifier.padding(bottom = 15.dp)) {
            PasswordInput("Current password") {
                currentPassword = it
            }
        }


        Text(text = "New password",
            fontFamily = moniFontFamily,
            fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(15.dp))
        Row(modifier = Modifier.padding(bottom = 15.dp)) {
            PasswordInput("New password") {
                newPassword = it
            }
        }

        Row(modifier = Modifier.padding(bottom = 15.dp)) {
            PasswordInput("Confirm password") {
                confirmPassword = it
            }
        }

        var i = remember { mutableStateOf(10) }

        Row(modifier = Modifier.padding(bottom = 15.dp, top = 25.dp)) {
            MainButton(text = "Save changes") {
                userViewModel.changePassword(currentPassword, newPassword, confirmPassword) {
                    i.value = it
                }

            }
        }

        if(i.value == 0) {
            CreateDialog("Change password", "The password have been changed correctly") {
                i.value = 10
            }

        }
        else if(i.value == 1) {
            CreateDialog("Change password", "The current password is not the same") {
                i.value = 10
            }

        }
        else if(i.value == 2) {
            CreateDialog("Change password", "New password and confirm password don't match") {
                i.value = 10
            }

        }
        else if(i.value == 3) {
            CreateDialog("Change password", "Fill al the fields") {
                i.value = 10
            }

        }

    }
}

@Composable
fun ImageWithName(name: String, image: Painter) {

    Column (horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 30.dp)){

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

