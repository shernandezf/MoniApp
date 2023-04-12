package edu.uniandes.moni.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
    @StringRes val nameId: Int,
    @StringRes val descriptionId: Int,
    @DrawableRes val imageResourceId: Int
)
