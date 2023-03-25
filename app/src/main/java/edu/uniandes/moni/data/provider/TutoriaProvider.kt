package edu.uniandes.moni.data.provider
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.data.Tutoria

class TutoriaProvider {
    val firestore = Firebase.firestore
    val tutorias = mutableListOf<Tutoria>()
    private fun retriveTutoria(){
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
                    tutorias.add(tutoria)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}