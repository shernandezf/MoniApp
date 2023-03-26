package edu.uniandes.moni.viewmodel


import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.data.TutoringDAO
import edu.uniandes.moni.domain.Tutoria

class TutoriaViewModel {
    companion object {
        private lateinit var tutories: MutableList<TutoringDAO>
        private lateinit var oneTutoring: TutoringDAO

        @JvmStatic
        fun initTutories() {
            tutories = mutableListOf<TutoringDAO>()
            oneTutoring = TutoringDAO()
        }

        @JvmStatic
        fun addTutoryTutories(tutory: TutoringDAO) {
            tutories.add(tutory)
        }

        @JvmStatic
        fun getTutories(): MutableList<TutoringDAO> {
            return tutories
        }

        @JvmStatic
        fun getOneTutoring(): TutoringDAO {
            return oneTutoring
        }

        @JvmStatic
        fun setOneTutoring(tutoring: TutoringDAO) {
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
                        TutoringDAO(
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
                TutoriaViewModel.setOneTutoring(documentSnapshot.toObject(TutoringDAO::class.java)!!)
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

