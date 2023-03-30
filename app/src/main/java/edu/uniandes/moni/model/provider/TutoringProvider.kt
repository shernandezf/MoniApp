package edu.uniandes.moni.model.provider

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.TutoringModel

class TutoringProvider {
    val firestore = Firebase.firestore
    val tutoringModels = mutableListOf<TutoringModel>()
    private fun retriveTutoria() {
        firestore.collection("tutorings")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var tutoringModel =
                        TutoringModel(
                            document.data?.get("description").toString(),
                            document.data?.get("inUniversity") as Boolean,
                            document.data?.get("price").toString(),
                            document.data?.get("title").toString(),
                            document.data?.get("topic").toString(),
                            document.data?.get("email").toString()
                        )
                    tutoringModels.add(tutoringModel)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}