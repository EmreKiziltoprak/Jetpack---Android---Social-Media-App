package com.example.telgraf.View

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.transition.Visibility
import com.example.telgraf.Components.Topbar
import com.example.telgraf.R
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.n.destinations.ForgotPasswordSDestination
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Composable
fun MainLoginView(navigator: DestinationsNavigator, vm: UserViewModel) {
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    var pas by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }
    val vmState = remember(key1 = vm.renewk.value) { mutableStateOf(vm) }
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }


    Scaffold(topBar = { Topbar(navigator = navigator, name = "Giriş Yap") }) {


        Column(
            Modifier
                .background(telgraf_color)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            SnackbarHost(hostState = snackbarHostState)

            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = emai,

                label = {
                    Text(
                        text = "Emailinizi giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    emai = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = borderColor,
                    textColor = arsenic

                )


            )

            Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))


            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = pas,
                onValueChange = { pas = it },
                label = {
                    Text(
                        "Şifrenizi giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = borderColor,
                    textColor = arsenic

                )
            )

            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

            if (vmState.value.isLoginSuccess == false) {
                Row() {


                    Text(
                        text = "* Lütfen bilgilerinizi kontrol ediniz",
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )


                }
            }

            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))


            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .clip(RoundedCornerShape(50))
                    .background(color = buttonColor)
                    .clickable {
                        cs.launch {

                            vmState.value.dologin(
                                context = context,
                                email = emai.text,
                                password = pas.text,
                                navigator = navigator
                            )

                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (vm.isAuthing.value == true) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(14.dp))
                }
                Text(
                    text = "Giriş Yap",
                    color = buttonTextColor,
                    fontFamily = gbook,
                    fontSize = 17.sp
                )
            }
            Spacer(modifier = Modifier.height(screenHeight * 0.04f))
            Text(
                text = "Parolamı unuttum",
                fontFamily = gbook,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.clickable {
                    navigator.navigate(ForgotPasswordSDestination)
                })


        }
    }


}