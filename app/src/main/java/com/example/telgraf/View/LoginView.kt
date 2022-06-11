package com.example.telgraf.View

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.auth0.android.jwt.JWT
import com.example.telgraf.R
import com.example.telgraf.Utils.TimeFunctions.DateTest
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.ViewModels.getToken
import com.example.telgraf.ViewModels.token
import com.example.telgraf.ViewModels.user_id
import com.example.telgraf.n.destinations.HomeScreenDestination
import com.example.telgraf.n.destinations.MainLoginScreenDestination
import com.example.telgraf.n.destinations.RegisterScreenDestination
import com.example.telgraf.n.destinations.RegisterWithSchoolScreenDestination
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.security.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.util.*


@Composable
fun LoginView(navigator: DestinationsNavigator, vm: UserViewModel) {
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    var pas by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    val vmState = remember(key1 = vm.renewk.value) { mutableStateOf(vm) }
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit, block = {

        context.let {
            getToken(context = it).collect { value ->
                token.value = value
                Log.d(
                    "GET_TOKEN", token.value
                )

                if (token.value!=""){


                    val jwt = JWT(token.value)

                    var claim: String? = jwt.getClaim("userId").asString()
                    var exp: String? = jwt.getClaim("exp").asString()

                    var date = DateTest(expDate = exp?:"")
                    date.compareDates()

                    user_id.value = claim.toString()
                    if (claim != null) {
                        vm.user_id_flow.value = claim.toInt()
                    }
                    if(date.compareResult==1){
                        navigator.navigate(HomeScreenDestination)
                    }

                //    Log.d("TIME", x.toString())



                }
            }



        }
    } )

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

        Surface(elevation = 1.dp, shape = RoundedCornerShape(50)) {


            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .background(color = buttonColor)
                    .clickable {
                        navigator.navigate(MainLoginScreenDestination)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Giriş Yap",
                    color = buttonTextColor,
                    fontFamily = gbook,
                    fontSize = 17.sp
                )
            }
        }


        Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))

        Surface(elevation = 1.dp, shape = RoundedCornerShape(50)) {

            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .background(color = buttonColor2)
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(50))
                    .clickable {
                        navigator.navigate(RegisterWithSchoolScreenDestination)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "A.Ü Mail ile Kaydol",
                    color = buttonTextColor,
                    fontFamily = gbook,
                    fontSize = 17.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))
        Surface(elevation = 1.dp, shape = RoundedCornerShape(50)) {

            Row(
                Modifier
                    .width(width = screenWidth * 0.91f)
                    .aspectRatio(ratio = 35 / 05f)
                    .background(color = buttonColor2)
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(50))

                    .clickable {
                        navigator.navigate(RegisterScreenDestination)

                    },

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (vm.isAuthing.value == true) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(14.dp))
                }
                Text(
                    text = "Mail ile Kaydol",
                    color = buttonTextColor,
                    fontFamily = gbook,
                    fontSize = 17.sp

                )
            }
        }

    }


}

