package com.example.telgraf.View


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.Components.NotificationCard
import com.example.telgraf.R
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.n.Bottombar
import com.example.telgraf.ui.theme.gbold
import com.example.telgraf.ui.theme.gbook
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun NotificationsView(navigator: DestinationsNavigator, vm: UserViewModel) {
    var announcement by remember { mutableStateOf(TextFieldValue("")) }

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit, block = {
        vm.getNotifications()
    })

    BackHandler(true) {
        // Or do nothing
    }

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(screenWidth * 0.15f)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(screenWidth * 0.06f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "",
                    modifier = Modifier.clickable { navigator.navigateUp() },
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(screenWidth * 0.04f))
                Text(text = "Bildirimler", color = Color.Black, fontFamily = gbold, fontSize = 17.sp)

            }
        }, content = {

            if (vm.notifcationsList != null) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))
                    if (vm.notifcationsList != null) {
                        LazyColumn() {
                            items(vm.notifcationsList) {

                                NotificationCard(navigator = navigator, item = it)


                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

                }
            } else {
                Text(
                    text = "Bildirim bulunamadı",
                    fontSize = 20.sp,
                    fontFamily = gbook,
                    textAlign = TextAlign.Center
                )
            }

        }, bottomBar = { Bottombar(navigator = navigator) })
}