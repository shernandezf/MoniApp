package edu.uniandes.moni.data.provider

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.domain.Tutor
import edu.uniandes.moni.domain.Tutoria

class TutorProvider {
    val firestore = Firebase.firestore
    val tutores = mutableListOf<Tutor>()
    fun retriveTutores() {
        firestore.collection("tutors")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var tutor =
                        Tutor(
                            document.data?.get("nombre").toString(),
                            document.data?.get("email").toString(),
                            document.data?.get("rating") as Float,
                            document.data?.get("topic") as Tutoria
                        )
                    tutores.add(tutor)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}