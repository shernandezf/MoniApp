package edu.uniandes.moni.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.auth.User

class UserSearchViewModel: ViewModel() {
    var state by mutableStateOf(searchState())
    fun onAction(userAction: UserAction){
        when(userAction){
            UserAction.CloseIconClick-> {
                state=state.copy(
                    isSearchBarvisible = false
                )
            }
            UserAction.SearchIconClick-> {
                state=state.copy(
                    isSearchBarvisible = true
                )
            }
            is UserAction.TextFieldInput ->{
                state=state.copy(
                    searchText = userAction.text
                )
                searchTopicList(userAction.text)
            }
        }
    }
    private fun searchTopicList(searchTopic: String){
        val tutorVM=TutorViewModel()
        val new_List=tutorVM.generarLista(searchTopic)
        state =state.copy(list = new_List)
    }
}




sealed class UserAction{
    object SearchIconClick: UserAction()
    object CloseIconClick: UserAction()
    data class TextFieldInput(val text:String): UserAction()
}
data class searchState(
    var isSearchBarvisible: Boolean=false,
    var searchText: String="",
    var list: List<String> = listOf<String>()
)