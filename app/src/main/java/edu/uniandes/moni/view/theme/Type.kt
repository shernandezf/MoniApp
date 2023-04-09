package edu.uniandes.moni.view.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.R

val moniFontFamily: FontFamily = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_italic, style = FontStyle.Italic),
);


val Typography = Typography(
    body1 = TextStyle(
        fontFamily = moniFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)