package com.example.books.Screens.Search

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.books.navigation.ReaderScreens
import com.example.books.widgets.AppBarbysans

@Preview
@Composable
fun SearchScreen(navController: NavController=NavController(LocalContext.current)){
    Scaffold(topBar = {
        AppBarbysans(title = "Search Books", showProfile = false,
            navController = NavController(LocalContext.current),
            icon = Icons.AutoMirrored.Filled.ArrowBack){
          //  navController.popBackStack() this would have also worked
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }){
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Column(modifier = Modifier.padding(10.dp)) {

                SearchBar()

            }
        }
    }
}


@Preview
@Composable
fun SearchBar(
    modifier: Modifier=Modifier,
    loading:Boolean=true,
    hint:String="Book Name",
    onSearch:(String)->Unit={}
){
    val SearchQueryState= rememberSaveable{
        mutableStateOf("")
    }

    val keyboardController= LocalSoftwareKeyboardController.current

    //checking whether search bar is empty or not
    val valid = remember(SearchQueryState.value){
        SearchQueryState.value.trim().isNotEmpty() //if it's not empty then it's true
    }
    /**
     * Here, SearchQueryState.value is used as a key for the remember
     * function. When the value of SearchQueryState.value changes,
     * the block inside remember will be recomputed. This ensures that
     * valid gets updated whenever the SearchQueryState.value changes.
     */
    //In this case, remember does not have any keys.
// It will compute the value only once during the initial composition and will not recompute it even if SearchQueryState.value changes later.

    OutlinedTextField(
        value = SearchQueryState.value,
        onValueChange = {SearchQueryState.value=it},
        label = { Text(hint) },
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        keyboardActions = KeyboardActions {
            if(!valid){ //if the text field is empty then it will return
                return@KeyboardActions
            }
            onSearch(SearchQueryState.value.trim())
            //removing the search query state value after Search Query
            keyboardController?.hide() //we are closing our keyboard now
        }
    )

}