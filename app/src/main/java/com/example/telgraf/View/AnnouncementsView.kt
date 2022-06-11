package com.example.telgraf.View

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.telgraf.Components.AnnouncementCard
import com.example.telgraf.R
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.n.Bottombar
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun AnnouncementsView(navigator: DestinationsNavigator, vm: UserViewModel) {
    var vm: PostsVM = PostsVM()
    var announcement by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit, block = {
        vm.getAnn()
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
                Text(text = "Duyurular", color = Color.Black, fontFamily = gbook, fontSize = 17.sp)

            }
        }, content = {

            if (vm.jannouncementsState != null) {

                Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ){
                Row(verticalAlignment = Alignment.CenterVertically) {


                    //SURNAME
                    OutlinedTextField(
                        modifier = Modifier
                            .width(screenWidth * 0.91f),
                        value = announcement,
                        label = {
                            Text(
                                text = "Duyuru Ara",
                                color = arsenic,
                                fontFamily = glight
                            )
                        },
                        onValueChange = {
                            announcement = it
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Black,
                            textColor = arsenic
                        )
                    )  }
                    Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))
                    if (vm.announcementsState != null) {
                        LazyColumn() {
                            items(vm.announcementsState.value) {
                                if ((announcement.text != "") && ((it.title.lowercase(Locale.getDefault())).contains(
                                        announcement.text.lowercase(
                                            Locale.getDefault()
                                        )
                                    ))
                                ) {
                                    AnnouncementCard(item = it)
                                }
                                if (announcement.text == "") {
                                    AnnouncementCard(item = it)

                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(height = screenWidth * 0.04f))

                }  }

        }, bottomBar = { Bottombar(navigator = navigator) })
}