package edu.uniandes.moni.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uniandes.moni.data.Tutor
import edu.uniandes.moni.data.TutorProvider

class TutorViewModel  {
    fun generarLista(tema: String) : List<String> {
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

