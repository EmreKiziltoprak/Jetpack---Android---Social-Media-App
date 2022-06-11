package com.example.telgraf.View

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.Components.PostCard
import com.example.telgraf.Components.PostCommentCard
import com.example.telgraf.Components.PostDetailCard
import com.example.telgraf.Components.Topbar
import com.example.telgraf.Network.Models.PostCommentsData
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.n.Bottombar
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Composable
fun PostDetailView(navigator: DestinationsNavigator, vm: PostsVM, singlePostid: Int) {
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    var pas by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val context = LocalContext.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    LaunchedEffect(key1 = Unit, block = {
            vm.getPostWithId(context = context, singlePostid!!)
            vm.getPostsComments(context = context, post_id = singlePostid!!)


    })
    Scaffold(
        topBar = { Topbar(navigator = navigator, name = "Yorumlar", color = Color.White, iconColor = Color.Black) },
        content = {
            if(vm.postcomments.value!= emptyList<PostCommentsData>() && vm.singlePoststate.value != null )
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.85f)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(0.05f))


                    Text(
                        text = "Gönderi",
                        textAlign = TextAlign.Start,
                        fontFamily = gbold,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.weight(0.95f))
                }
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))

                if (vm.singlePoststate.value != null) {
                    PostDetailCard(
                        item = vm.singlePoststate.value!!,
                        vmm = vm,
                        navigator = navigator
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(0.05f))


                    Text(
                        text = "Yorumlar",
                        textAlign = TextAlign.Start,
                        fontFamily = gbold,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.weight(0.95f))
                }

                if (vm.postst.value != null) {
                    LazyColumn(modifier = Modifier.height(screenHeight * 0.45f)) {
                        itemsIndexed(vm.postcomments.value) { index, item ->
                            PostCommentCard(item = item, vm = vm, navigator = navigator)

                        }
                    }
                }
            }
        },
        bottomBar = {
            Row(
                Modifier
                    .height(screenHeight * 0.1f)
                    .padding(horizontal = screenWidth * 0.04f)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                BasicTextField(
                    modifier = Modifier.fillMaxWidth(0.86f),
                    value = vm.comment_text,
                    onValueChange = { vm.comment_text = it },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (vm.comment_text.isEmpty()) {
                                Text(
                                    text = "Yorum ekle...",
                                    color = post_border,
                                    fontFamily = gbook,
                                    fontSize = 15.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Row(
                    Modifier
                        .height(screenWidth * 0.08f)
                        .width(screenWidth * 0.14f)
                        .clip(RoundedCornerShape(percent = 15))
                        .background(telgraf_color2)
                        .clickable(enabled = if (!vm.comment_text.isEmpty()) true else false) {
                            cs.launch {
                                if (vm.comment_text != "") {
                                        vm.doComment(
                                            context = context,
                                            post_id = singlePostid,
                                            text = vm.comment_text
                                        )
                                        vm.getPostWithId(context = context, singlePostid)
                                        vm.getPostsComments(
                                            context = context,
                                            post_id = singlePostid
                                        )

                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Paylaş", color = Color.White, fontFamily = gbook, fontSize = 10.sp)
                }
            }
        })
}