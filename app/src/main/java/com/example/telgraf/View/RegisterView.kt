package com.example.telgraf.View

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.Components.Topbar
import com.example.telgraf.Network.Models.GraduateRegisterBody
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

//MEZUN UYELIK
@Composable
fun RegisterView(navigator: DestinationsNavigator, vm: UserViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val vmState = remember(key1 = vm.renewk.value) { mutableStateOf(vm) }
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val mExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = vm.registerError.value, block = {
        if(vm.registerError.value) {
            snackbarHostState.showSnackbar(message = vm.registerErrorMessage.value)
            vm.registerError.value = false
            vm.registerErrorMessage.value = ""
        }
    })
    Scaffold(topBar = { Topbar(navigator = navigator, name = "Mail ile Kayıt ol") }) {


        Column(
            Modifier
                .background(telgraf_color)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            SnackbarHost(hostState = snackbarHostState,
                snackbar = { snackbarData: SnackbarData ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .width(screenWidth * 0.91f)
                            .padding(bottom = screenWidth * 0.02f),
                        backgroundColor = Color.Black
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = snackbarData.message, fontFamily = glight, color = Color.White)
                        }
                    }

                }
            )

            Column() {

                Row(
                    Modifier
                        .width(screenWidth * 0.91f)
                        .aspectRatio(ratio = 35 / 05f)
                        .clip(shape = RoundedCornerShape(percent = 3))
                        .background(buttonColor)
                        .clickable {
                            Log.d("MEXPANDED", mExpanded.toString())
                            mExpanded.value = !mExpanded.value
                            Log.d("MEXPANDED", mExpanded.toString())
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        modifier = Modifier.padding(start = screenWidth * 0.04f),
                        text = vm.selecteddepartment?.name ?: "Bölümünüzü Seçiniz",
                        color = Color.White,
                        fontFamily = gbook,
                        fontSize = 17.sp
                    )
                }

                DropdownMenu(
                    expanded = mExpanded.value,
                    onDismissRequest = { mExpanded.value = false },
                    modifier = Modifier
                        .width(width = screenWidth * 0.91f)
                        .height(screenWidth * 0.4f)
                        .background(
                            buttonColor
                        )
                ) {
                    vm.department.value.forEach { department ->
                        Log.d("DEPARTMENT", department.name.toString())
                        DropdownMenuItem(onClick = {
                            vm.selecteddepartment = department
                            mExpanded.value = false

                        }) {
                            Text(
                                text = department.name,
                                color = Color.White,
                                fontFamily = gbook,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))

            //NAME
            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = vm.name,
                label = {
                    Text(
                        text = "İsminizi giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    vm.name = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = borderColor,
                    textColor = arsenic
                )
            )

            Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))

            //SURNAME
            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = vm.surname,
                label = {
                    Text(
                        text = "Soyisminizi giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    vm.surname = it
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
                value = vm.pas,
                label = {
                    Text(
                        text = "Parolanızı giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    vm.pas = it
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
                value = vm.emai,
                label = {
                    Text(
                        text = "Emailinizi giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    vm.emai = it
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
                value = vm.schooln,
                label = {
                    Text(
                        text = "Okul Numaranızı giriniz",
                        color = arsenic,
                        fontFamily = glight
                    )
                },
                onValueChange = {
                    vm.schooln = it
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = borderColor,
                    textColor = arsenic
                )
            )
            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))


            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .clip(RoundedCornerShape(50))
                    .background(color = buttonColor)
                    .clickable {
                        cs.launch {
                            val sb = StringBuilder()

                            var message: String = ""
                            if (vm.selecteddepartment?.id == null) {
                                message = "Lütfen departman seçiniz\n"
                                sb.append(message)
                            }
                            if (checkEmail(vm.emai.text) == false) {
                                sb.append("Lütfen geçerli bir mail adresi giriniz\n")
                                message = sb.toString()
                            }
                            if (vm.pas.text == "") {
                                Log.d("xxx", "cc")
                                sb.append("Lütfen şifre giriniz\n")
                                message = sb.toString()

                            }
                            if (vm.schooln.text == "") {
                                sb.append("Lütfen okul numarası giriniz\n")
                                message = sb.toString()

                            }
                            if (vm.surname.text == "") {
                                sb.append("Lütfen soyadınızı giriniz\n")
                                message = sb.toString()

                            }
                            if (message != "") {
                                snackbarHostState.showSnackbar(message)
                            } else {
                                vmState.value.doGraduateRegister(
                                    context = context,
                                    body = GraduateRegisterBody(
                                        departmentId = vm.selecteddepartment!!.id!!,
                                        email = vm.emai.text,
                                        name = vm.name.text,
                                        password = vm.pas.text,
                                        schoolNumber = vm.schooln.text,
                                        surname = vm.surname.text
                                    )
                                )
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (vm.isAuthing.value == true) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(14.dp))
                }
                Text(
                    text = "Kayıt Ol",
                    color = Color.White,
                    fontFamily = gbook,
                    fontSize = 17.sp
                )
            }


        }
    }
}