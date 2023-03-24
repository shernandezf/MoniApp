package edu.uniandes.moni.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.navigation.AppNavigation
import edu.uniandes.moni.ui.theme.MoniTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.uniandes.moni.viewmodel.UserSearchViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoniTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {

                    AppNavigation()
                    //val viewModel= viewModel<UserSearchViewModel>()
                    //SearchBarTopic(
                    //    viewModelUser = viewModel
                    //)
                }
            }
        }
    }
}

