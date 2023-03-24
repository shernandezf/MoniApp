package edu.uniandes.moni.viewmodel


import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.data.Tutoria

class TutoriaViewModel {
}

fun writeNewTutoria(description:String,  inUniversity:Boolean,  price:String,  title: String,  topic:String) {
    val tutoria = Tutoria(description, inUniversity, price, title, topic)
    val db = FirebaseFirestore.getInstance()

    db.collection("tutorings").document().set(tutoria)

}
