package com.example.books.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.log

@Composable
fun MainLogo(text:String,modifier: Modifier = Modifier,logScreen:Boolean=false) {
    Text(
        modifier = modifier,
        text = text,
        style = if(!logScreen) MaterialTheme.typography.headlineLarge
        else MaterialTheme.typography.displayMedium,
        color = if(!logScreen)Color.Red.copy(alpha = 0.5f)
        else
        MaterialTheme.colorScheme.onBackground
    )
}
@Composable
fun Emailinput(modifier: Modifier=Modifier,
               emailState: MutableState<String>,
               labelId:String="",
               enabled:Boolean=true,
               imeaction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default){

    Input(modifier = modifier,
        valueState = emailState,
        labelId=labelId,
        enabled=enabled,
        keyboardType = KeyboardType.Email,
        imeaction=imeaction,
        onAction = onAction
    )

}




//making actual input
@Composable
fun Input(modifier: Modifier=Modifier,
          valueState: MutableState<String>,
          labelId:String="Email",
          enabled:Boolean=true,
          keyboardType: KeyboardType = KeyboardType.Text,
          imeaction: ImeAction = ImeAction.Next,
          isSingleLine:Boolean=true,
          onAction: KeyboardActions = KeyboardActions.Default
){
    OutlinedTextField(value =valueState.value,
        onValueChange = {valueState.value=it},
        label = {
            Text(text = labelId)
        },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
            imeAction = imeaction)
    )
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {

    Button(onClick=onClick,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors =ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        if(loading)
            CircularProgressIndicator(modifier = Modifier
                .size(30.dp))
        else
            Text(text = textId,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                .padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
    }

}



@Composable
fun TitleSection(modifier: Modifier=Modifier,label :String){
    Surface(modifier=modifier
        .padding(start = 5.dp, end = 1.dp)){
        Column {
            Text(text = label,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
    }
}



