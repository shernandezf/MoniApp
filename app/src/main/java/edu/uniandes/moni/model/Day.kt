package edu.uniandes.moni.model

import java.time.LocalDate

data class Day(val date: LocalDate, val events: List<SessionModel>)
