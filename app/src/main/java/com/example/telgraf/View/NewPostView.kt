package com.example.telgraf.View

import android.Manifest
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.Components.Topbar
import com.example.telgraf.R
import com.example.telgraf.Utils.AddPhoto
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.ui.theme.gbook
import com.example.telgraf.ui.theme.post_border
import com.example.telgraf.ui.theme.telgraf_color
import com.example.telgraf.ui.theme.telgraf_color2
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NewPostView(navigator: DestinationsNavigator, vm: PostsVM) {

    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val filePermissions =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

    //GET USER INFO
    LaunchedEffect(key1 = Unit, block = {
        filePermissions.launchMultiplePermissionRequest()



    })
Scaffold(
    content = {Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .height(screenHeight * 0.1f)
                .padding(horizontal = screenWidth * 0.04f)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {


            BasicTextField(
                modifier = Modifier
                    .height(screenWidth * 0.15f)
                    .fillMaxWidth(1f),
                value = vm.comment_text,
                onValueChange = { vm.comment_text = it },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (vm.comment_text.isEmpty()) {
                            Text(
                                text = "Gönderi ekle...",
                                color = post_border,
                                fontFamily = gbook,
                                fontSize = 15.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Divider(thickness = (0.35).dp,color = telgraf_color, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(screenWidth * 0.02f))

        Row(
            Modifier
                .height(screenWidth * 0.08f)
                .width(screenWidth * 0.14f)
                .clip(RoundedCornerShape(percent = 15))
                .background(telgraf_color2)
                .clickable(enabled = if (!vm.comment_text.isEmpty()) true else false) {
                    cs.launch {
                        if (vm.comment_text != "") {
                            vm.sendPost(context = context, text = vm.comment_text, navigator = navigator)
                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Paylaş", color = Color.White, fontFamily = gbook, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(screenWidth * 0.04f))
        AddPhoto(vm = vm)
    }},
    topBar = { Topbar(navigator = navigator, name = "Gönderi Ekle", color = Color.White, iconColor = Color.Black) }
        )
}