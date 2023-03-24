package edu.uniandes.moni.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import edu.uniandes.moni.ui.theme.azul1
import edu.uniandes.moni.ui.theme.azul3
import edu.uniandes.moni.viewmodel.TutorViewModel
import edu.uniandes.moni.viewmodel.UserAction
import edu.uniandes.moni.viewmodel.UserSearchViewModel


@Composable
fun SearchBarTopic(
    viewModelUser: UserSearchViewModel
){
    val state = viewModelUser.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Crossfade(targetState = state.isSearchBarvisible, animationSpec = tween(500))
        {
            if (it) {
                searchAppBar(onClosedIconClick = {

                    viewModelUser.onAction(UserAction.CloseIconClick)
                },
                    searchText = state.searchText,
                    onTextChange = {new_text->
                        viewModelUser.onAction(UserAction.TextFieldInput(new_text))
                    }
                )
            } else {
                TopBar(
                    onsearchIconClick = {
                        viewModelUser.onAction(UserAction.SearchIconClick)
                    }
                )
            }
        }
        LazyColumn{
            items(state.list){
                item ->
                SingleItemCard(name = item)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}
@Composable
fun SingleItemCard(name:String){
    Card(backgroundColor=Color.White,
        contentColor = Color.Black){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = name)
        }
    }
}
@Composable
fun TopBar(
    onsearchIconClick:()->Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
    ) {
        Text(
            text="Search",
            color = azul1,
            fontSize=30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)

        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp)
            .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.TopEnd)

        {
            IconButton(onClick =  onsearchIconClick ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(20.dp),
                    tint=azul3
                )
            }
        }


    }
}
@Composable
fun searchAppBar(
    onClosedIconClick:()->Unit,
    searchText: String,
    onTextChange:(String)->Unit
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = searchText,
        onValueChange ={
            onTextChange(it)
        },
        textStyle = TextStyle(
            color= Color(0xFF173066),
            fontSize = 18.sp
        ),
    leadingIcon = {
        Icon(imageVector = Icons.Filled.Search, contentDescription ="search Icon",tint=azul3)
    },
    trailingIcon = {
        IconButton(onClick = onClosedIconClick) {
            Icon(imageVector = Icons.Filled.Close, contentDescription ="Close Icon" ,tint=azul3)
        }
    },
    colors=TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = Color.White,
        focusedBorderColor = Color.White
    )
    )
}
@Preview
@Composable
fun prev(){
    SearchBarTopic(
        viewModelUser = UserSearchViewModel()
    )
}