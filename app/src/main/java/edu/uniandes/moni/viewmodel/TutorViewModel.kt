package edu.uniandes.moni.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uniandes.moni.data.Tutor
import edu.uniandes.moni.data.TutorProvider

class TutorViewModel : ViewModel(){
    val lista_tutores = MutableLiveData<List<String>>()
    
    fun generarLista(tema:String){
        val tutorprovide=TutorProvider()
        tutorprovide.retriveTutores()
        var lista = tutorprovide.tutores
        var lista_tema=mutableListOf<Tutor>()
        for (tutor in lista){
            if (tutor.tutoria.topic==tema){
                lista_tema.add(tutor)
            }
        }
        var sortedEmployees = lista_tema
            .sortedByDescending { it.rating }
            .map { it.nombre }
        sortedEmployees.toMutableList()

        lista_tutores.postValue(sortedEmployees)
    }

}