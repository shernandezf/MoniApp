package edu.uniandes.moni.viewmodel

import edu.uniandes.moni.model.TutorModel
import edu.uniandes.moni.model.provider.TutorAdapter

class TutorViewModel {
    fun generarLista(tema: String): List<String> {
        var sortedEmployees: List<String> = mutableListOf()
        val tutorAdapter = TutorAdapter()
        tutorAdapter.retriveTutores { response ->
            val lista: MutableList<TutorModel> = response
            val listaTema = mutableListOf<TutorModel>()
            for (tutor in lista) {
                if (tutor.tutoringModel.topic == tema) {
                    listaTema.add(tutor)
                }
            }
            sortedEmployees = listaTema
                .sortedByDescending { it.rating }
                .map { it.nombre }
            sortedEmployees.toMutableList()
        }
        return sortedEmployees
    }
}

