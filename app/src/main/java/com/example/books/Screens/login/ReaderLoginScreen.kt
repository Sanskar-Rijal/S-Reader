package com.example.books.Screens.login

import android.graphics.Paint.Align
import android.inputmethodservice.Keyboard.Row
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.books.R
import com.example.books.components.Emailinput
import com.example.books.components.MainLogo
import com.example.books.components.NotificationMessage
import com.example.books.components.SubmitButton
import com.example.books.navigation.ReaderScreens


@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel= viewModel()) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = if(isSystemInDarkTheme()) painterResource(R.drawable.darkmode)
            else
                painterResource(R.drawable.lightmode),
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.46f),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds
        )

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.Transparent
        ) {

            Column( modifier = Modifier
                .padding(top = 30.dp, start = 17.dp, end = 17.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                MainLogo(
                    text = "S. Reader",
                    modifier = Modifier.padding(top = 35.dp, bottom = 15.dp),
                    logScreen = true)

                Spacer(modifier = Modifier.height(50.dp))
                Column(
                    modifier = Modifier.padding(top=50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    MainLogo(
                        text = if(showLoginForm.value)"Login" else "Signup",
                        modifier = Modifier
                            .padding(top = 25.dp, bottom = 15.dp),
                        logScreen = true
                    )
                    if (showLoginForm.value) {
                        Userform(
                            loading = false,
                            isCreateAccount = false
                        ) { email, pass ->
                            Log.d("sans", "LoginScreen: email=$email,pass=$pass")
                            viewModel.signInwithEmailAndPassword(email, pass) {
                                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                            }
                        }
                        NotificationMessage(viewModel)
                    } else {
                        //means we want to show create account Screen
                        Userform(
                            loading = false,
                            isCreateAccount = true
                        ) { email, pass ->
                            //we have to create the  user
                            viewModel.CreateUserWithEmailandPassword(email, pass) {
                                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                            }
                        }
                        NotificationMessage(viewModel)
                    }

                    Row(
                        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val text1 = if (showLoginForm.value) "Sign up" else "Login "
                        val text2 =
                            if (showLoginForm.value) "New User?" else "Already have an Account?"
                        Text(text = text2)
                        Text(
                            text1,
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .clickable {
                                    //go to create account
                                    showLoginForm.value = !showLoginForm.value //making it true and false
                                },
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
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
        .height(270.dp)
        //.background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState()) //allows us to scroll when the screen size is too small

    Column(modifier=modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        if(isCreateAccount)
        {
            Text(text = "Please enter a valid Email and Password",
                modifier = Modifier.padding(10.dp))
        }
        else{
            Text(text = "")
        }
        Emailinput(emailState = email,
            enabled = !loading,
            labelId = "Email")

        PasswordInput(modifier=Modifier.focusRequester(passwordFocusRequest),//focus will come here from above
            passwordstate = password ,
            labelid="Password",
            enabled=!loading,
            passwordvisibility=passwordVisibility,
            onAction=KeyboardActions{
                if(!valid) { //checking whether the inputs are not empty
                   return@KeyboardActions
                }
                onDone(email.value.trim(),password.value.trim())
                keyboardController?.hide()
            }
        )

        SubmitButton(
            textId= if(isCreateAccount) "Create Account"
            else
            "Login",
            loading=loading,
            validInputs= valid){
            //when submit button is clicked
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }


    }

}


@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordstate:MutableState<String>,
    labelid: String,
    enabled: Boolean,
    imeAction: ImeAction=ImeAction.Default,
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
       // keyboardActions = onAction,
        enabled =enabled,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {
            //Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "Show Password")
            //PasswordVisibility(passwordvisibility=passwordvisibility)

            if(passwordvisibility.value){
                IconButton(onClick ={passwordvisibility.value= !passwordvisibility.value} ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "icon")
                }
            }
            else{
                IconButton(onClick = {passwordvisibility.value =!passwordvisibility.value}) {
                    Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "icon")
                }
            }

        }
    )



}

@Composable
fun PasswordVisibility(passwordvisibility: MutableState<Boolean>) {
    var visible=passwordvisibility.value
    if(visible){
    IconButton(onClick ={passwordvisibility.value= !visible} ) {
        Icon(imageVector = Icons.Default.Close, contentDescription = "icon")
        }
    }
    else{
        IconButton(onClick = {passwordvisibility.value =!visible}) {
            Icon(imageVector = Icons.Default.RemoveRedEye, contentDescription = "icon")
            passwordvisibility.value=true
        }
    }
}
