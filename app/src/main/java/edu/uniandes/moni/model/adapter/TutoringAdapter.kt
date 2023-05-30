package edu.uniandes.moni.model.adapter

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.uniandes.moni.model.TutoringModel
import edu.uniandes.moni.model.dto.TutoringDTO
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

class TutoringAdapter {

    private val db = FirebaseFirestore.getInstance()

    fun getAllTutorings(callback: (response: MutableList<TutoringDTO>) -> Unit) {
        val tutoringModels = mutableListOf<TutoringDTO>()
        db.collection("tutorings")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val reviews = (document.data["reviews"] as? List<*>)?.map { it.toString() }
                        ?.toTypedArray() ?: arrayOf<String>()
                    val scores =
                        (document.data["scores"] as? List<*>)?.map { (it as? Number)?.toInt() }
                            ?.toTypedArray() ?: arrayOf<Int>()

                    val tutoringModel =
                        TutoringDTO(
                            document.data["description"].toString(),
                            document.data["inUniversity"] as Boolean,
                            document.data["price"].toString(),
                            document.data["title"].toString(),
                            document.data["topic"].toString(),
                            document.data["tutorEmail"].toString(),
                            reviews,
                            scores as Array<Int>,
                            document.id
                        )
                    tutoringModels.add(tutoringModel)
                }
                Log.d("Lolsito", "estamos en repository")
                callback(tutoringModels)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getAllTutoringsTopic(
        topic: String,
        callback: (response: MutableList<TutoringDTO>) -> Unit
    ) {
        val tutoringModels = mutableListOf<TutoringDTO>()
        db.collection("tutorings")
            .whereEqualTo("topic", topic.capitalize())
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val reviews = (document.data["reviews"] as? List<*>)?.map { it.toString() }
                        ?.toTypedArray() ?: arrayOf<String>()
                    val scores =
                        (document.data["scores"] as? List<*>)?.map { (it as? Number)?.toInt() }
                            ?.toTypedArray() ?: arrayOf<Int>()
                    val tutoringModel =
                        TutoringDTO(
                            document.data["description"].toString(),
                            document.data["inUniversity"] as Boolean,
                            document.data["price"].toString(),
                            document.data["title"].toString(),
                            document.data["topic"].toString(),
                            document.data["tutorEmail"].toString(),
                            reviews,
                            scores as Array<Int>,
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
                val reviews =
                    (documentSnapshot.data?.get("reviews") as? List<*>)?.map { it.toString() }
                        ?.toTypedArray() ?: arrayOf<String>()
                val scores =
                    (documentSnapshot.data?.get("scores") as? List<*>)?.map { (it as? Number)?.toInt() }
                        ?.toTypedArray() ?: arrayOf<Int>()
                val tutoring =
                    TutoringDTO(
                        documentSnapshot.data!!["description"].toString(),
                        documentSnapshot.data!!["inUniversity"] as Boolean,
                        documentSnapshot.data!!["price"].toString(),
                        documentSnapshot.data!!["title"].toString(),
                        documentSnapshot.data!!["topic"].toString(),
                        documentSnapshot.data!!["email"].toString(),
                        reviews,
                        scores as Array<Int>,
                        documentSnapshot.id
                    )
                callback(tutoring)
            }

        }.addOnFailureListener { exception ->
            Log.w(TAG, "Error getting the tutoring.", exception)
        }
    }

    suspend fun getTutoringByIdSync(id: String): TutoringDTO? {
        return try {
            val tutoringRef = db.collection("tutorings").document(id)
            val documentSnapshot = withTimeoutOrNull(5000) { // 5 seconds timeout
                tutoringRef.get().await()
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                val reviews =
                    (documentSnapshot.data?.get("reviews") as? List<*>)?.map { it.toString() }
                        ?.toTypedArray() ?: arrayOf<String>()
                val scores =
                    (documentSnapshot.data?.get("scores") as? List<*>)?.map { (it as? Number)?.toInt() }
                        ?.toTypedArray() ?: arrayOf<Int>()
                TutoringDTO(
                    documentSnapshot.data!!["description"].toString(),
                    documentSnapshot.data!!["inUniversity"] as Boolean,
                    documentSnapshot.data!!["price"].toString(),
                    documentSnapshot.data!!["title"].toString(),
                    documentSnapshot.data!!["topic"].toString(),
                    documentSnapshot.data!!["email"].toString(),
                    reviews,
                    scores as Array<Int>,
                    documentSnapshot.id
                )
            } else {
                null
            }
        } catch (exception: Exception) {
            Log.w(TAG, "Error getting the tutoring.", exception)
            null
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
                    tutorEmail,
                    arrayOf<String>(),
                    arrayOf<Int>()
                )
            )
    }

}