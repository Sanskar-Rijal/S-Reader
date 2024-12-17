package com.example.books.Screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.books.components.Emailinput
import com.example.books.components.MainLogo

@Preview
@Composable
fun LoginScreen(){
    Surface(modifier = Modifier
        .fillMaxSize()) {
        Column(modifier = Modifier.padding(17.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            MainLogo(modifier = Modifier.padding(top = 30.dp, bottom = 15.dp))
            Userform(loading = false,
                isCreateAccount = false){email,pass ->
                Log.d("FORM", "LoginScreen: email=$email,pass=$pass")
            }
        }

    }
}



@Preview
@Composable
fun Userform(
    loading:Boolean=false, // this is for login or create acc button , to make it enable or disabled
    isCreateAccount:Boolean=false,
    onDone:(String,String) -> Unit ={email,pwd -> }
){

    val email = rememberSaveable {
        mutableStateOf("")
    }

    val password = rememberSaveable{
        mutableStateOf("")
    }

        //to show password
    val passwordVisibility = rememberSaveable {
        mutableStateOf(false)
    }

    val passwordFocusRequest= FocusRequester.Default

    val keyboardController = LocalSoftwareKeyboardController.current //we can hide keyboard

    val valid = remember(email.value,password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() //if its empty it will written false
    }

    //keyboard may  overlap on our login screen , i mean the text field where we enter password and email
    //so it's better to make it scrollable
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Emailinput(emailState = email,
            enabled = !loading,
            onAction = KeyboardActions{
                passwordFocusRequest.requestFocus() //itmeans when we click done it will focus on next input field
            },
            labelId = "Email")

        PasswordInput(modifier=Modifier.focusRequester(passwordFocusRequest),//focus will come here from above
            passwordstate = password ,
            labelid="Password",
            enabled=!loading,
            passwordvisibility=passwordVisibility,
            onAction=KeyboardActions{
                if(!valid)
                     return@KeyboardActions
                else
                onDone(email.value.trim(),password.value.trim())
            }
        )

    }

}
@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordstate:MutableState<String>,
    labelid: String,
    enabled: Boolean,
    imeAction: ImeAction=ImeAction.Done,
    passwordvisibility: MutableState<Boolean>,
    onAction: KeyboardActions=KeyboardActions.Default
) {

    val visualTransformation = if(passwordvisibility.value) VisualTransformation.None
    else
        PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordstate.value,
        onValueChange = {passwordstate.value=it},
        label = { Text(text = labelid) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled =enabled,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordvisibility=passwordvisibility)}
    )

}

@Composable
fun PasswordVisibility(passwordvisibility: MutableState<Boolean>) {
    val visible=passwordvisibility.value
    IconButton(onClick ={passwordvisibility.value= !visible} ) {
        Icons.Default.Close
    }
}
