package com.example.telgraf.View


import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.R
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.n.Bottombar
import com.example.telgraf.ui.theme.telgraf_color
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Composable
fun MemorandumsView(navigator: DestinationsNavigator, vm: UserViewModel) {
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    var pas by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Scaffold(content = {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.12f)
                    .background(telgraf_color),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ankara Üniversitesi Telgraf Sistemi",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(screenWidth * 0.04f))

            Image(
                painterResource(R.drawable.telgraf),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(screenWidth * 0.5f)
                    .aspectRatio(162f / 153f)
            )

            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            SnackbarHost(hostState = snackbarHostState)

            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = emai,
                label = { Text(text = "Emailinizi giriniz") },
                onValueChange = {
                    emai = it
                }
            )

            Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))

            OutlinedTextField(
                modifier = Modifier
                    .width(screenWidth * 0.91f),
                value = pas,
                label = { Text(text = "Şifrenizi giriniz") },
                onValueChange = {
                    pas = it
                }
            )

            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))
            Row(
                Modifier
                    .width(width = screenWidth * 0.33f)
                    .aspectRatio(ratio = 35 / 12f)
                    .clip(RoundedCornerShape(50))
                    .background(color = Color.Black)
                    .clickable {
                        cs.launch {
                            vm.dologin(
                                email = emai.text,
                                password = pas.text
                            )

                            if (vm.isLoginSuccess == false) {
                                snackbarHostState.showSnackbar("Lütfen Bilgilerinizi kontrol ediniz")
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Giriş Yap", color = Color.White, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

            Text(text = "Ya da")

            Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

        }
    }, bottomBar = { Bottombar(navigator = navigator) })}