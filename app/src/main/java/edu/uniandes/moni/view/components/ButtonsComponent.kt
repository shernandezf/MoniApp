package edu.uniandes.moni.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.view.theme.secondary

@Composable
fun MainButton(text: String, onClickFunction: () -> Unit) {
    Button(
        onClick = onClickFunction,
        Modifier
            .fillMaxWidth(0.95f)
            .height(50.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(23, 48, 102)
        )
    ) {
        Text(
            text = text,
            fontSize = with(LocalDensity.current) { (25.dp * 0.6f).toSp() },
            letterSpacing = 0.5.sp,
            color = Color(255, 255, 255),
            fontFamily = moniFontFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SecondaryButton(text: String, onClickFunction: () -> Unit) {
    Button(
        onClick = onClickFunction,
        Modifier
            .fillMaxWidth(0.95f)
            .height(50.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = secondary
        )
    ) {
        Text(
            text = text,
            fontSize = with(LocalDensity.current) { (25.dp * 0.6f).toSp() },
            letterSpacing = 0.5.sp,
            color = Color(255, 255, 255),
            fontFamily = moniFontFamily,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun test1() {
    MainButton(text = "Log In") {
    }
}

@Preview
@Composable
fun test2() {
    SecondaryButton(text = "Sign Up") {}
}