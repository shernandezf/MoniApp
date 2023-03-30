package edu.uniandes.moni.viewmodel

import edu.uniandes.moni.model.TutorModel
import edu.uniandes.moni.model.provider.TutorProvider

class TutorViewModel {
    fun generarLista(tema: String): List<String> {
        val tutorprovide = TutorProvider()
        tutorprovide.retriveTutores()
        var lista = tutorprovide.tutores
        var lista_tema = mutableListOf<TutorModel>()
        for (tutor in lista) {
            if (tutor.tutoringModel.topic == tema) {
                lista_tema.add(tutor)
            }
        }
        var sortedEmployees = lista_tema
            .sortedByDescending { it.rating }
            .map { it.nombre }
        sortedEmployees.toMutableList()
        return sortedEmployees

    }
}

