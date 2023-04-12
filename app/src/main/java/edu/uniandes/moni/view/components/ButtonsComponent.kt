package edu.uniandes.moni.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.view.theme.main
import edu.uniandes.moni.view.theme.moniFontFamily
import edu.uniandes.moni.view.theme.secondary

@Composable
fun MainButton(text: String, enabled: Boolean = true, onClickFunction: () -> Unit) {
    Button(
        onClick = onClickFunction,
        Modifier
            .fillMaxWidth(0.95f)
            .height(50.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(23, 48, 102)
        ),
        enabled = enabled
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

@Composable
fun CancelTextButton(text: String, onClickFunction: (Int) -> Unit) {
    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontFamily = moniFontFamily,
            color = Color.Red,
            fontSize = 15.sp,
            textDecoration = TextDecoration.Underline
        ),
        onClick = onClickFunction,
        modifier = Modifier.padding()
    )
}

@Composable
fun TextButton(text: String, onClickFunction: (Int) -> Unit) {
    ClickableText(
        text = AnnotatedString(text),
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontFamily = moniFontFamily,
            color = main,
            fontSize = 15.sp,
            textDecoration = TextDecoration.Underline
        ),
        onClick = onClickFunction,
        modifier = Modifier.padding()
    )
}