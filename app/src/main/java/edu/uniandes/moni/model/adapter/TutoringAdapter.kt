package edu.uniandes.moni.model.adapter

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.model.TutoringModel
import edu.uniandes.moni.model.dto.TutoringDTO

class TutoringAdapter {

    private val db = FirebaseFirestore.getInstance()

    fun getAllTutorings(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        val tutoringModels = mutableListOf<TutoringDTO>()
        db.collection("tutorings")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tutoringModel =
                        TutoringDTO(
                            document.data["description"].toString(),
                            document.data["inUniversity"] as Boolean,
                            document.data["price"].toString(),
                            document.data["title"].toString(),
                            document.data["topic"].toString(),
                            document.data["tutorEmail"].toString(),
                            document.id
                        )
                    tutoringModels.add(tutoringModel)
                }
                callback(tutoringModels)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getTutoringById(id: String, callback: (tutoring: TutoringDTO) -> Unit) {
        val tutoringRef = db.collection("tutorings").document(id)
        tutoringRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val tutoring =
                    TutoringDTO(
                        documentSnapshot.data!!["description"].toString(),
                        documentSnapshot.data!!["inUniversity"] as Boolean,
                        documentSnapshot.data!!["price"].toString(),
                        documentSnapshot.data!!["title"].toString(),
                        documentSnapshot.data!!["topic"].toString(),
                        documentSnapshot.data!!["email"].toString(),
                        documentSnapshot.id
                    )
                callback(tutoring)
            }

        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting the tutoring.", exception)
        }
    }

    fun createTutoring(
        description: String,
        inUniversity: Boolean,
        price: String,
        title: String,
        topic: String,
        tutorEmail: String
    ) {
        db.collection("tutorings")
            .document()
            .set(
                TutoringModel(
                    description,
                    inUniversity,
                    price,
                    title,
                    topic,
                    tutorEmail
                )
            )
    }

}