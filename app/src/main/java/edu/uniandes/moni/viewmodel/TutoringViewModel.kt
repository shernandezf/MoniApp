package edu.uniandes.moni.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.model.adapter.TutoringAdapter
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.model.repository.TutoringRepository
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutoringViewModel @Inject constructor(private val tutoringRepository: TutoringRepository) :
    ViewModel() {

    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()

    fun createTutoring(
        description: String,
        inUniversity: Boolean,
        price: String,
        title: String,
        topic: String,
        tutorEmail: String,
        callback: (Int) -> Unit
    ) {
        if (MainActivity.internetStatus == "Available") {
            tutoringAdapter.createTutoring(
                description,
                inUniversity,
                price,
                title,
                topic,
                tutorEmail
            )
            callback(0)
        } else {
            callback(1)
        }
    }

    fun getAllTutorings(callback: (MutableList<TutoringDTO>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            tutoringRepository.getAllTutorings { response ->
                callback(response)
            }
        }
    }

    suspend fun getTutoringById(id: String, callback: (TutoringDTO) -> Unit) {
        tutoringRepository.getTutoringById(id) {
            callback(it)
        }
    }

}

