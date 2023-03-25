package edu.uniandes.moni.ui

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.launch

@Composable
fun HelpBottomSheetDialog(onCloseClick: () -> Unit, onReportClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Close Button",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onCloseClick)
            )
        }

        Text(
            text = "Do you have any problem in your tutoring?",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Report any inconvenient you may have in order to improve your experience in tutoring sessions.",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = onReportClick,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(23,48,102)),
            shape = RectangleShape
        ) {
            Text(
                text = "Report a problem",
                color = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RenderHelpBottomSheetDialog(){
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded)
    val courtineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            HelpBottomSheetDialog(
                onCloseClick = {
                    courtineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                onReportClick = {
                    courtineScope.launch {
                        bottomSheetState.hide()
                    }
                    //makePhoneCall
                }
            )
        }) {
    }
}
