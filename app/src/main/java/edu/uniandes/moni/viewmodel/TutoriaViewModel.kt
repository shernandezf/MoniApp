package edu.uniandes.moni.viewmodel


import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.data.Tutoria

class TutoriaViewModel {
    companion object {
        private lateinit var tutories: MutableList<Tutoria>

        @JvmStatic
        fun initTutories() {
            tutories = mutableListOf<Tutoria>()
        }

        @JvmStatic
        fun addTutoryTutories(tutory: Tutoria) {
            tutories.add(tutory)
        }

        @JvmStatic
        fun getTutories(): MutableList<Tutoria> {
            return tutories
        }
    }
}

fun writeNewTutoria(description:String,  inUniversity:Boolean,  price:String,  title: String,  topic:String, tutorEmail: String?) {
    val tutoria = Tutoria(description, inUniversity, price, title, topic, tutorEmail)
    val db = FirebaseFirestore.getInstance()

    db.collection("tutorings").document().set(tutoria)

}

fun retriveTutorias() {
    val firestore = Firebase.firestore
    TutoriaViewModel.initTutories()
    firestore.collection("tutorings")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                var tutoria=
                    Tutoria(document.data?.get("description").toString(),
                        document.data?.get("inUniversity") as Boolean,
                        document.data?.get("price").toString(),
                        document.data?.get("title").toString(),
                        document.data?.get("topic").toString(),
                        document.data?.get("email").toString()
                    )

                TutoriaViewModel.addTutoryTutories(tutoria)
            }
        }
        .addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting documents.", exception)
        }
}
