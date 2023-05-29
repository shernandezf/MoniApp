package edu.uniandes.moni.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uniandes.moni.model.adapter.TutoringAdapter
import edu.uniandes.moni.model.dto.TutoringDTO
import edu.uniandes.moni.model.repository.TutoringRepository
import edu.uniandes.moni.view.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutoringViewModel @Inject constructor(private val tutoringRepository: TutoringRepository) :
    ViewModel() {

    private val tutoringAdapter: TutoringAdapter = TutoringAdapter()

    private val _searchText= MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching= MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
    private val _tutorings= MutableStateFlow(listOf<TutoringDTO>())

    val tutorings= searchText.
    onEach { _isSearching.update { true } }
        .debounce(1500)
        .combine(_tutorings){text,tutorings ->
            if (text.isBlank()){
                tutorings
            }else{
                delay(2000)
                tutorings
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _tutorings.value
        )
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
    fun getAllTutoringstopic(topic: String,callback: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            tutoringRepository.getAllTutoringsTopic(topic) { response, numero ->
                _tutorings.value=response
                callback(numero)
            }
        }
    }
    suspend fun getTutoringById(id: String, callback: (TutoringDTO) -> Unit) {
        tutoringRepository.getTutoringById(id) {
            callback(it)
        }
    }

}

