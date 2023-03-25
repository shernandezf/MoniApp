package edu.uniandes.moni.viewmodel


import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.data.dao.TutoriaDAO
import edu.uniandes.moni.domain.Tutoria

class TutoriaViewModel {
    companion object {
        private lateinit var tutories: MutableList<TutoriaDAO>
        private lateinit var oneTutoring: TutoriaDAO

        @JvmStatic
        fun initTutories() {
            tutories = mutableListOf<TutoriaDAO>()
            oneTutoring = TutoriaDAO()
        }

        @JvmStatic
        fun addTutoryTutories(tutory: TutoriaDAO) {
            tutories.add(tutory)
        }

        @JvmStatic
        fun getTutories(): MutableList<TutoriaDAO> {
            return tutories
        }

        @JvmStatic
        fun getOneTutoring(): TutoriaDAO {
            return oneTutoring
        }

        @JvmStatic
        fun setOneTutoring(tutoring: TutoriaDAO) {
            oneTutoring = tutoring
        }
    }

    fun writeNewTutoria(
        description: String,
        inUniversity: Boolean,
        price: String,
        title: String,
        topic: String,
        tutorEmail: String?
    ) {
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
                    var tutoria =
                        TutoriaDAO(
                            document.data?.get("description").toString(),
                            document.data?.get("inUniversity") as Boolean,
                            document.data?.get("price").toString(),
                            document.data?.get("title").toString(),
                            document.data?.get("topic").toString(),
                            document.data?.get("email").toString(),
                            document.id
                        )

                    TutoriaViewModel.addTutoryTutories(tutoria)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun getTutoringById(id: String) {
        val db = FirebaseFirestore.getInstance()
        val tutoriaRef = db.collection("tutorings").document(id)

        tutoriaRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                TutoriaViewModel.setOneTutoring(documentSnapshot.toObject(TutoriaDAO::class.java)!!)
            }
        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting the tutoring.", exception)
        }
    }

    fun editTutoria(id: String, tutoria: Tutoria) {
        val db = FirebaseFirestore.getInstance()
        val tutoriaRef = db.collection("tutorings").document(id)
        val newData = mapOf(
            "title" to tutoria.title,
            "description" to tutoria.description,
            "topic" to tutoria.topic,
            "price" to tutoria.price,
            "tutorEmail" to tutoria.tutorEmail,
            "inUniversity" to tutoria.inUniversity
        )

        tutoriaRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    tutoriaRef.update(newData)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error updating the tutoring", exception)
            }
    }
}

