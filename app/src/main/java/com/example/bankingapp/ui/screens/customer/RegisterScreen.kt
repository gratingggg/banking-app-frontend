package com.example.bankingapp.ui.screens.customer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bankingapp.ui.components.NormalButton
import com.example.bankingapp.ui.components.NormalDatePicker
import com.example.bankingapp.ui.components.NormalDropdownMenuBox
import com.example.bankingapp.ui.components.NormalTextField
import com.example.bankingapp.ui.components.PasswordField
import com.example.bankingapp.ui.theme.lightCoralPink
import com.example.bankingapp.utils.Gender

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    isErrorTriggered: Boolean = false,
    onButtonClicked: (Map<String, String>) -> Unit,
    onSignInClicked: () -> Unit
){
    var scrollState = rememberScrollState()

    var name by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var genderStr by rememberSaveable {
        mutableStateOf("")
    }
    var dateOfBirthStr by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }
    var address by rememberSaveable {
        mutableStateOf("")
    }
    var aadharNumber by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalTextField(
            fieldValue = name,
            fieldLabel = "Name",
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Person
        ) {
            name = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalTextField(
            fieldValue = email,
            fieldLabel = "Email",
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Email
        ) {
            email = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalTextField(
            fieldLabel = "Username",
            fieldValue = username,
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.AccountCircle
        ) {
            username = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        PasswordField(
            fieldValue = password,
            fieldLabel = "Password",
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Lock
        ) {
            password = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalDropdownMenuBox(
            fieldValue = genderStr,
            fieldLabel = "Gender",
            options = Gender.entries.map { it.name },
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Wc
        ) {
            genderStr = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalDatePicker(
            fieldValue = dateOfBirthStr,
            fieldLabel = "Date of Birth",
            fieldPlaceholder = "DD/MM/YYYY",
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Cake
        ) {
            dateOfBirthStr = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalTextField(
            fieldValue = phoneNumber,
            fieldLabel = "Phone Number",
            isErrorTriggered = isErrorTriggered,
            enablePhoneOnlyKeyboard = true,
            imageVector = Icons.Default.Phone
        ) {
            phoneNumber = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalTextField(
            fieldLabel = "Address",
            fieldValue = address,
            isErrorTriggered = isErrorTriggered,
            imageVector = Icons.Default.Home
        ) {
            address = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        NormalTextField(
            fieldLabel = "Aadhar Number",
            fieldValue = aadharNumber,
            isErrorTriggered = isErrorTriggered,
            enablePhoneOnlyKeyboard = true,
            imageVector = Icons.Default.CreditCard
        ) {
            aadharNumber = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        var enableButton = name.isNotEmpty() &&
                email.isNotEmpty() &&
                username.isNotEmpty() &&
                password.isNotEmpty() &&
                genderStr.isNotEmpty() &&
                dateOfBirthStr.isNotEmpty() &&
                phoneNumber.isNotEmpty() &&
                address.isNotEmpty() &&
                aadharNumber.isNotEmpty()

        NormalButton(
            btnText = "Register",
            enabled = enableButton
        ) {
            onButtonClicked(
                mapOf(
                    "name" to name,
                    "email" to email,
                    "username" to username,
                    "password" to password,
                    "genderStr" to genderStr,
                    "dateOfBirthStr" to dateOfBirthStr,
                    "phoneNumber" to phoneNumber,
                    "address" to address,
                    "aadharNumber" to aadharNumber
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already registered? ",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Text(
                text = "Sign in",
                modifier = Modifier.clickable(
                    onClick = onSignInClicked
                ),
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}