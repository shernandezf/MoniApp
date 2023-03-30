package edu.uniandes.moni.model

import edu.uniandes.moni.R

class Datasource() {
    fun loadAffirmations(): List<Affirmation> {
        return listOf<Affirmation>(
            Affirmation(R.string.name1, R.string.description1, R.drawable.andres),
            Affirmation(R.string.name2, R.string.description2, R.drawable.pipe),
            Affirmation(R.string.name3, R.string.description3, R.drawable.santiago)
        )
    }
}
