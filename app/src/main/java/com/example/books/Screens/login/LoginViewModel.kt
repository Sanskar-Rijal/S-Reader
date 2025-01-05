package com.example.books.Screens.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.EVENT
import com.example.books.model.Suser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

const val USERS = "users"


class LoginViewModel:ViewModel() {

  //  val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth:FirebaseAuth=Firebase.auth

    private val _loading = MutableLiveData(false)

    private val _state= MutableStateFlow(LoadingState.IDLE)

    val state= _state.asStateFlow()

    val loading: LiveData<Boolean> = _loading

    //making toast messages
    val popUpNotification = mutableStateOf<EVENT<String>?>(null)

    fun signInwithEmailAndPassword(email:String,password:String,home:()-> Unit)
    =viewModelScope.launch{
        try {
            _state.value= LoadingState.LOADING
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("login", "signInwithEmailAndPassword:${task.exception}")
                        //take them to HomeScreen
                        _state.value=LoadingState.SUCCESS
                        home()
                    } else {
                        Log.d("fuck", "signInwithEmailAndPassword: ${task.exception}")
                        _state.value= LoadingState.FAILED
                        handleException(task.exception, "Login Failed")
                    }
                }
        }catch(ex:Exception){
            Log.d("fuck", "signInwithEmailAndPassword in catchblock ")
            handleException(ex,"Signin Failed")
        }
    }

    fun CreateUserWithEmailandPassword(email: String,password: String,home: () -> Unit) {
        Log.d("april", "entered create userwith emaill ")
        //we need to check whether user is unique or not so we check email address
     if(_loading.value==false){

         _state.value=LoadingState.LOADING

         _loading.value= true

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task -> //task with auth result
                    Log.d("july", "i'm inside this")
                    if (task.isSuccessful) {
                        //sans@gmail.com i will just get the name before @onSignup
                        _state.value=LoadingState.SUCCESS
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName, email)
                        home()
                        _loading.value=false
                    } else {
                        _state.value= LoadingState.FAILED
                        Log.d("july", "i am in else block ${task.exception?.message} ")
                        //we are creating a funciton to handle exception so that our app wont crash
                        handleException(task.exception, "Signup Failed")
                        _loading.value=false
                    }
                }
         _loading.value=false
        }
    }

    private fun createUser(displayName:String?,email: String){

        //attaching the randomly generated user id from authentication
        val userId=auth.currentUser?.uid
        //to save anything in firestore we  are creating user
      //  val user= mutableMapOf<String,Any>()

        val user=Suser(userId=userId.toString(), email=email,
            displayName = displayName.toString(), profileIcon = "",
            quote = "Life is beautiful enjoy it!!", proffession = "Android Developer", id = null).toMap()
        //adding to firebase databse
        FirebaseFirestore.getInstance().collection(USERS).add(user)

    }

    private fun handleException(exception: Exception?=null,CustomMsg:String=""){

        exception?.printStackTrace() //printStactTrace helps to print the exact error

        val errorMessage = exception?.localizedMessage?:" "

        Log.d("june", "handleException is called")
        /**
         * If exception is not null, it retrieves the localizedMessage (a human-readable description of the exception).
         * If exception is null, the result is null.
         * using Elvis operator , ternary operator
         */
        val message = if(CustomMsg.isEmpty()) errorMessage else "$CustomMsg :$errorMessage"
        popUpNotification.value = EVENT(message)
    }
}

