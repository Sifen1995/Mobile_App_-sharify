package com.example.sharifytest2.Components.authComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharifytest2.R
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle


@Composable
fun NormalTextComponent( value:String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
//            .heightIn(min = 40.dp)
            .padding(10.dp)
            .padding(top = 0.dp),

        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal

        )
        ,color = colorResource(id = R.color.small_text),
        textAlign = TextAlign.Center

    )

}


@Composable
fun HeadingTextComponent( value:String) {
    Text(
        text = value,

        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(bottom = 5.dp)
            .padding(top = 20.dp),

        style = TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal

        )
        ,textAlign = TextAlign.Center,
        color = colorResource(id = R.color.black),



    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue: String, painterResource: Painter,onTextChange: (String) -> Unit) {
    val textValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.textfield))
            .border(0.dp, Color.Transparent),


        label = {Text(labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.textfield),
//            unfocusedIndicatorColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.small_text),
            unfocusedLabelColor = colorResource(id = R.color.small_text),

        ),

        value = textValue.value,

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
        textValue.value = it
            onTextChange(it)
    },
        leadingIcon = {
            Icon(painter = painterResource,

                contentDescription = "profile",
                modifier = Modifier.size(24.dp)


            )
        })

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun PasswordTextFieldComponent(labelValue: String, painterResource: Painter,onTextChange: (String) -> Unit) {
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.textfield))
            .border(0.dp, Color.Transparent),
        label = { Text(labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.textfield),
            focusedLabelColor = colorResource(id = R.color.small_text),
            unfocusedLabelColor = colorResource(id = R.color.small_text)
        ),
        value = password.value,
        onValueChange = { password.value = it
                        onTextChange(it)},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = "profile",
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible.value) "Hide password" else "Show password"
                )
            }
        },
        visualTransformation = if (passwordVisible.value) {
            VisualTransformation.None // Show plain text
        } else {
            PasswordVisualTransformation() // Mask text with dots
        }
    )
}

@Composable
fun checkboxComponent( value:String, onTextSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,

    ){
        val checkedState = remember{
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(id = R.color.button),

            ),
            onCheckedChange = {
                checkedState.value = it}

        )
        ClickableTestComponent(value, onTextSelected)

    }
    
}


@Composable
fun ClickableTestComponent( value:String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing, you agree to our"
    val termsText = "Terms of Service"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(SpanStyle(color = colorResource(id = R.color.button))) {
            pushStringAnnotation(tag = termsText, annotation = termsText)
            append(termsText)

        }
    }
    ClickableText(text = annotatedString,onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also{ span ->
                Log.d("TAG", "ClickableTestComponent: ${span.item}")

                if(span.item == termsText){
                    onTextSelected(span.item)

                }
            }


    })

}

@Composable

fun ButtonComponent(value: String, function: () -> Unit) {
    Button(
        onClick = {
            function()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.button), // Button background color
            contentColor = Color.White // Text color
        ),
        shape = RoundedCornerShape(8.dp)

    ) {
        Text(
            text = value,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 15.sp,

                fontStyle = FontStyle.Normal
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LoginClickableTestComponent( value:String, onTextSelected: (String) -> Unit){
    val initialText = "Already register?"
    val termsText = " Login"

    val annotatedString = buildAnnotatedString{
        append(initialText)
        withStyle(SpanStyle(color = colorResource(id = R.color.button))) {
            pushStringAnnotation(tag = termsText, annotation = termsText)
            append(termsText)

        }
    }
    ClickableText(text = annotatedString,onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also{ span ->
                Log.d("TAG", "ClickableTestComponent: ${span.item}")

                if(span.item == termsText){
                    onTextSelected(span.item)

                }
            }


    })

}

@Composable
fun LoginMethod( value:String, onTextSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center


        ){

        LoginClickableTestComponent(value, onTextSelected)

    }

}



