package edu.uniandes.moni.viewmodel


import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.uniandes.moni.model.TutoringModel
import edu.uniandes.moni.model.dao.TutoringDAO

class TutoringViewModel {
    companion object {
        private lateinit var tutories: MutableList<TutoringDAO>
        private var lastTutorie: String = "0"
        private lateinit var oneTutoring: TutoringDAO
        private lateinit var tutoriesInterst1: MutableList<TutoringDAO>
        private lateinit var tutoriesInterst2: MutableList<TutoringDAO>

        @JvmStatic
        fun initTutories() {
            tutories = mutableListOf<TutoringDAO>()
            tutoriesInterst1 = mutableListOf<TutoringDAO>()
            tutoriesInterst2 = mutableListOf<TutoringDAO>()
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
        fun getTutoriesInterst1(): MutableList<TutoringDAO> {
            return tutoriesInterst1
        }

        @JvmStatic
        fun getTutoriesInterst2(): MutableList<TutoringDAO> {
            return tutoriesInterst2
        }

        @JvmStatic
        fun getOneTutoring(): TutoringDAO {
            return oneTutoring
        }

        @JvmStatic
        fun setOneTutoring(tutoring: TutoringDAO) {
            oneTutoring = tutoring
        }


        @JvmStatic
        fun retriveRangeTutorias() {
            val firestore = Firebase.firestore
            firestore.collection("tutorings")
                .orderBy(FieldPath.documentId())
                .startAt(lastTutorie)
                .limit(5)
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



                        addTutoryTutories(tutoria)
                    }



                    lastTutorie = tutories[tutories.lastIndex].id
                    Log.d("TAG", lastTutorie)
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                }
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
        val tutoringModel =
            TutoringModel(description, inUniversity, price, title, topic, tutorEmail)
        val db = FirebaseFirestore.getInstance()

        db.collection("tutorings").document().set(tutoringModel)
    }

    fun retriveTutorias() {
        val firestore = Firebase.firestore
        TutoringViewModel.initTutories()
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

                    TutoringViewModel.addTutoryTutories(tutoria)
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
                TutoringViewModel.setOneTutoring(documentSnapshot.toObject(TutoringDAO::class.java)!!)
            }
        }.addOnFailureListener { exception ->
            Log.w(ContentValues.TAG, "Error getting the tutoring.", exception)
        }
    }

    fun editTutoria(id: String, tutoringModel: TutoringModel) {
        val db = FirebaseFirestore.getInstance()
        val tutoriaRef = db.collection("tutorings").document(id)
        val newData = mapOf(
            "title" to tutoringModel.title,
            "description" to tutoringModel.description,
            "topic" to tutoringModel.topic,
            "price" to tutoringModel.price,
            "tutorEmail" to tutoringModel.tutorEmail,
            "inUniversity" to tutoringModel.inUniversity
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

