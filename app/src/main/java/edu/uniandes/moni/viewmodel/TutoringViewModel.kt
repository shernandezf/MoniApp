package edu.uniandes.moni.viewmodel


import edu.uniandes.moni.model.TutoringModel
import edu.uniandes.moni.model.adapter.TutoringAdapter
import edu.uniandes.moni.model.dto.TutoringDTO

class TutoringViewModel {

    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()

    companion object {

        private var lastTutoring: String = "0"
        private var tutoringList: MutableList<TutoringDTO> = mutableListOf()
        private var oneTutoring: TutoringDTO = TutoringDTO()

        @JvmStatic
        fun getTutoringList(): MutableList<TutoringDTO> {
            return tutoringList
        }

        @JvmStatic
        fun getOneTutoring(): TutoringDTO {
            return oneTutoring
        }

        @JvmStatic
        fun setOneTutoring(tutoring: TutoringDTO) {
            oneTutoring = tutoring
        }
    }

    fun getTutoringsRange() {
        tutoringAdapter.getTutoringsRange(5, lastTutoring) { response ->
            lastTutoring = response[0] as String
            tutoringList = response[1] as MutableList<TutoringDTO>
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
        tutoringAdapter.createTutoring(description, inUniversity, price, title, topic, tutorEmail)
    }

    fun getAllTutorings() {
        tutoringAdapter.getAllTutorings { response ->
            println(response.size)
            tutoringList = response
        }
    }

    fun getTutoringById(id: String) {
        tutoringAdapter.getTutoringById(id) { response ->
            setOneTutoring(response)
        }
    }

    fun editTutoria(id: String, tutoringModel: TutoringModel) {
        tutoringAdapter.editTutoring(id, tutoringModel)
    }
}

