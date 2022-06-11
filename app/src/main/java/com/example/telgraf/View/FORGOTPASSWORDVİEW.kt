package com.example.telgraf.View

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.R
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ForgotPasswordView(navigator: DestinationsNavigator) {
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    var userViewModel: UserViewModel = remember { UserViewModel() }
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    Column(
        Modifier
            .background(telgraf_color)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(contentAlignment = Alignment.BottomCenter) {

            Image(
                painterResource(R.drawable.telgraff_main2),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.35f)
            )

            Text(
                modifier = Modifier.padding(bottom = screenWidth * 0.11f),
                text = "Ankara Üniversitesi",
                color = Color.White,
                fontFamily = gbold,
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
            Text(
                modifier = Modifier.padding(bottom = screenWidth * 0.03f),
                text = "Telgraf Sistemi",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = gbold,
                fontSize = 35.sp
            )
        }

        Spacer(modifier = Modifier.height(screenWidth * 0.02f))


        Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

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
        Spacer(modifier = Modifier.height(screenWidth * 0.02f))
        if (userViewModel.forgotPasswordSuccess == 1) {
            Text(
                text = "Şifre sıfırlama maili başarıyla gönderildi",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = gbook
            )
        } else if (userViewModel.forgotPasswordSuccess == -1) {
            Text(
                text = "Girdiğiniz maili kontrol ediniz...",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = gbook
            )

        }
        Spacer(modifier = Modifier.height(screenWidth * 0.02f))

        Surface(elevation = 1.dp, shape = RoundedCornerShape(50)) {


            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .background(color = buttonColor)
                    .clickable(enabled =
                    if (userViewModel.forgotPasswordSuccess == 0
                        || userViewModel.forgotPasswordSuccess == -1
                        || userViewModel.forgotPasswordSuccess == 1) {
                        true
                    }
                   else if(userViewModel.forgotPasswordSuccess == -2){
                        false
                    }
                    else {false}, onClick = {
                        userViewModel.forgotPassword(email = emai.text)
                    }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                if(userViewModel.forgotPasswordSuccess == -2){
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(14.dp))

                }
                Text(
                    text = "Parola Sıfırla",
                    color = buttonTextColor,
                    fontFamily = gbook,
                    fontSize = 17.sp
                )
            }
        }


    }


}

