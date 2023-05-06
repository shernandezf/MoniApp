package edu.uniandes.moni.model.adapter

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.model.TutoringModel
import edu.uniandes.moni.model.dao.TutoringDAO

class TutoringAdapter {

    private val db = FirebaseFirestore.getInstance()

    companion object {
        val getTutoringsRangeResponse: MutableList<Any> = mutableListOf()
    }

    fun getAllTutorings(callback: (response: MutableList<TutoringDAO>) -> Unit) {
        val tutoringModels = mutableListOf<TutoringDAO>()
        db.collection("tutorings")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tutoringModel =
                        TutoringDAO(
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

    fun getTutoringsRange(
        limit: Long,
        lastTutoring: String,
        callback: (response: MutableList<Any>) -> Unit
    ) {
        val responseList: MutableList<Any> = mutableListOf()
        val tutoringList: MutableList<TutoringDAO> = mutableListOf()
        db.collection("tutorings")
            .orderBy(FieldPath.documentId())
            .startAt(lastTutoring)
            .limit(limit)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val tutoring =
                        TutoringDAO(
                            document.data["description"].toString(),
                            document.data["inUniversity"] as Boolean,
                            document.data["price"].toString(),
                            document.data["title"].toString(),
                            document.data["topic"].toString(),
                            document.data["email"].toString(),
                            document.id
                        )
                    tutoringList.add(tutoring)
                }
                responseList.add(tutoringList[tutoringList.lastIndex].id)
                responseList.add(tutoringList)
                callback(responseList)

            }.addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getTutoringById(id: String, callback: (tutoring: TutoringDAO) -> Unit) {
        lateinit var tutoring: TutoringDAO
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
            Log.w(ContentValues.TAG, "Error getting the tutoring.", exception)
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

    fun editTutoring(id: String, tutoringModel: TutoringModel) {
        val tutoringRef = db.collection("tutorings").document(id)
        val newData = mapOf(
            "title" to tutoringModel.title,
            "description" to tutoringModel.description,
            "topic" to tutoringModel.topic,
            "price" to tutoringModel.price,
            "tutorEmail" to tutoringModel.tutorEmail,
            "inUniversity" to tutoringModel.inUniversity
        )

        tutoringRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    tutoringRef.update(newData)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error updating the tutoring", exception)
            }
    }
}