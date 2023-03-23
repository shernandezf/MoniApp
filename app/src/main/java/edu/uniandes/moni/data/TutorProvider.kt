package edu.uniandes.moni.data
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class TutorProvider {
    val firestore = Firebase.firestore
    val tutores = mutableListOf<Tutor>()
    private fun retriveTutores(){
        firestore.collection("tutors")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tutor=
                        Tutor(document.data?.get("nombre").toString(),
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