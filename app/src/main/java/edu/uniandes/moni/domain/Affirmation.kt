package edu.uniandes.moni.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(@StringRes val nameId: Int,
                       @StringRes val descriptionId: Int,
                       @DrawableRes val imageResourceId: Int)
