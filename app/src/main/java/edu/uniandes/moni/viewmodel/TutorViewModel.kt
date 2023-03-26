package edu.uniandes.moni.viewmodel

import edu.uniandes.moni.data.provider.TutorProvider
import edu.uniandes.moni.domain.Tutor

class TutorViewModel {
    fun generarLista(tema: String): List<String> {
        val tutorprovide = TutorProvider()
        tutorprovide.retriveTutores()
        var lista = tutorprovide.tutores
        var lista_tema = mutableListOf<Tutor>()
        for (tutor in lista) {
            if (tutor.tutoria.topic == tema) {
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

