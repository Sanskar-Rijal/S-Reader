package com.example.books.Screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {

  //  val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth:FirebaseAuth=Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInwithEmailAndPassword(email:String,password:String,home:()-> Unit)
    =viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Log.d("login", "signInwithEmailAndPassword:${task.result}")
                        //take them to HomeScreen
                        home()
                    }
                    else{
                        Log.d("FB", "signInwithEmailAndPassword: ${task.result}")
                    }
                }

        }catch (ex:Exception){
            Log.d("signin", "signInwithEmailAndPassword: ${ex.message}")
        }
    }

    fun CreateUserWithEmailandPassword(email: String,password: String,home: () -> Unit){
     if(_loading.value == false){
         _loading.value=true
         auth.createUserWithEmailAndPassword(email,password)
             .addOnCompleteListener{task-> //task with auth result
                 if(task.isSuccessful){
                     //take to home screen
                 }
                 else{
                     Log.d("cr", "CreateUserWithEmailandPassword: ${task.result} ")
                 }
                 _loading.value=false
             }
     }
    }

    fun CreateLoginUser(){

    }


}