package com.example.telgraf.Components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.HtmlCompat
import com.example.telgraf.Network.Models.PostCommentsData
import com.example.telgraf.Network.Models.PostData
import com.example.telgraf.R
import com.example.telgraf.Utils.LinkText
import com.example.telgraf.Utils.LinkTextData
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.user_id
import com.example.telgraf.n.Html
import com.example.telgraf.n.destinations.PostDetailScreenDestination
import com.example.telgraf.n.destinations.ShowProfileScreenDestination
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.lang.Integer.sum


@Composable
fun PostCard(
    item: PostData,
    vmm: PostsVM,
    navigator: DestinationsNavigator
) {

    Log.d("ITEM_ID", item.author.id.toString())
    val currentPostId: Int? = item.id


    var likeCount by remember { mutableStateOf(item.likeCount) }
    var commentCount by remember { mutableStateOf(item.commentCount) }

    var item_user_id by remember { mutableStateOf(item.author.id.toString()) }

    var user_id by remember { mutableStateOf(user_id.value) }

    val vm by remember(key1 = vmm.renewk.value) { mutableStateOf(vmm) }
    val dropdownShower = remember { mutableStateOf(false) }
    var editPost by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var comment_text by remember { mutableStateOf("") }
    val isLiked = remember { mutableStateOf(0) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    val cs = rememberCoroutineScope()
    LaunchedEffect(key1 = item, block = {
        Log.d("INITIAL", ".")
        if (item.userLike) {
            isLiked.value = 1
        } else {
            isLiked.value = 0
        }
    })

    var list = mutableListOf<PostCardEditOptions>()
    list.add(0, PostCardEditOptions(1, "Düzenle"))
    list.add(0, PostCardEditOptions(2, "Sil"))

    LaunchedEffect(key1 = isLiked.value, block = {

        if (isLiked.value == 1) {
            Log.d("ADDLIKE", isLiked.value.toString())
            vm.LikePost(context = context, post_id = item.id)
            val handler = Handler()
            handler.postDelayed(Runnable {
                // yourMethod();
                vm.getPostWithId(context = context, id = item.id, isRenewing = true)

            }, 2000) //5 seconds
            vm.getPostWithId(context = context, id = item.id, isRenewing = true)

        }
        if (isLiked.value == -1) {
            Log.d("REMOVELIKE", isLiked.value.toString())
            vm.removeLike(context = context, post_id = item.id)
            val handler = Handler()
            handler.postDelayed(Runnable {
                // yourMethod();
                vm.getPostWithId(context = context, id = item.id, isRenewing = true)
            }, 2000) //5 seconds
        }
    })


    LaunchedEffect(key1 = vm.isFetched.value, block = {
        if ((vm.currentPostId ?: 0) == item.id) {
            likeCount = vm.likeCount!!
            vm.renewk.value = vm.renewk.value + 32
            Log.d("LIKE_COUNT", likeCount.toString())

        }
    })

    androidx.compose.material.Surface(
        elevation = 3.dp,
        modifier = Modifier
            .width(screenWidth * 1.0f)

            .clickable {
                Log.d("POST-CONTENT", item.content.toString())

                Log.d("POST-İD", item.id.toString())

                vm.singlePostid = item.id
                navigator.navigate(PostDetailScreenDestination(id = item.id))
            }
    ) {


        Column(
            Modifier
                .background(postBackground)
                .width(width = screenWidth * 1.0f)

                .padding(horizontal = screenWidth * 0.04f)

        ) {
            Spacer(modifier = Modifier.height(screenWidth * 0.04f))

            Row() {

                Surface(elevation = 10.dp, shape = RoundedCornerShape(90), color = Color.White) {


                    Row(
                        Modifier
                            .size(screenWidth * 0.12f)
                            .clip(RoundedCornerShape(90))
                            .clickable{
                                navigator.navigate(ShowProfileScreenDestination(id = item.author.id))

                            }
                        ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        //   Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                        if (item.author.profilePicture != null) {
                            GlideImage(
                                imageModel = "https://telgraf.ankara.edu.tr/api/" + item.author.profilePicture,
                                contentScale = ContentScale.Fit,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
                                contentDescription = "", tint = arsenicBold,
                                modifier = Modifier.size(screenWidth * 0.09f)

                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                Column {
                    Text(
                        modifier = Modifier.clickable {
                            navigator.navigate(ShowProfileScreenDestination(id = item.author.id))
                        },
                        text = item.author.name + " " + item.author.surname, color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(screenWidth * 0.02f))
                    Text(
                        text = item.createdAt.substring(0, 10) + " " + item.createdAt.substring(
                            11,
                            16
                        ), color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Log.d("ITEM_AUTHOR_ID", item.author.id.toString())
                Log.d("USER_İD", user_id.toString())

                if (item.author.id.toString() == user_id) {
                    Surface() {
                        Column() {
                            Icon(
                                modifier = Modifier
                                    .size(screenWidth * 0.04f)
                                    .clickable {
                                        Log.d("DROPDOWNSHOWER", dropdownShower.toString())

                                        if (dropdownShower.value == true) {
                                            dropdownShower.value = false
                                        } else {
                                            dropdownShower.value = true
                                        }
                                        Log.d("DROPDOWNSHOWER", dropdownShower.value.toString())
                                    },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic___vertical_dots),
                                contentDescription = "Some icon"
                            )


                            DropdownMenu(
                                expanded = dropdownShower.value,
                                onDismissRequest = { dropdownShower.value = false },
                                modifier = Modifier
                                    .width(width = screenWidth * 0.25f)
                                    .height(screenWidth * 0.26f)
                                    .background(
                                        Color.White
                                    )
                            ) {
                                list.forEach { department ->
                                    DropdownMenuItem(onClick = {
                                        if (department.id == 2) {
                                            //TODO delete function
                                            Log.d("DELETE_POST_START", "1")
                                            cs.launch {
                                                currentPostId?.let {
                                                    vm.deletePost(
                                                        context = context,
                                                        post_id = it
                                                    )
                                                    dropdownShower.value = false

                                                }
                                            }
                                        }
                                        if (department.id == 1) {
                                            cs.launch {

                                                //TODO navigator.
                                                vmm.editPostText =
                                                    HtmlCompat.fromHtml(
                                                        item.content,
                                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                                    )
                                                        .toString()
                                                editPost = true
                                                dropdownShower.value = false
                                                //navigator.navigate(EditPost)
                                            }
                                        }
                                    }) {
                                        Text(
                                            text = department.name,
                                            color = arsenic,
                                            fontFamily = gbook,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            Column {

                Html(text = item.content)
                if (item.isFileUploaded)
                    LinkText(
                        linkTextData = listOf(
                            LinkTextData(
                                text = "Ek dosya: ",
                            ),
                            LinkTextData(
                                text = item.file?.originalname ?: "",
                                tag = "icon_1_author",
                                annotation = "https://telgraf.ankara.edu.tr/api/" + item.file?.path
                                    ?: "",
                                onClick = {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW, Uri.parse(
                                                "https://telgraf.ankara.edu.tr/api/" + item.file?.path?.drop(
                                                    7
                                                )
                                                    ?: ""
                                            )
                                        )
                                    )
                                    Log.d("Link text", "${it.tag} ${it.item}")
                                },
                            ),
                        )
                    )

                Row() {
                    Column(
                        Modifier
                            .size(screenWidth * 0.1f)
                            .clickable {
                                // navigator.navigate(HomeScreenDestination)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (isLiked.value == 1)
                            Icon(
                                modifier = Modifier.clickable {
                                    isLiked.value = -1
                                },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_favorite_24),
                                contentDescription = "Some icon",
                                tint = Color.Red
                            )
                        else
                            Icon(
                                modifier = Modifier.clickable {
                                    isLiked.value = 1
                                },
                                imageVector = ImageVector.vectorResource(id = R.drawable.favorite_24),
                                contentDescription = "Some icon",
                                tint = Color.Black
                            )
                    }
                    Column(
                        Modifier
                            .size(screenWidth * 0.1f)
                            .clickable {
                                //  navigator.navigate(HomeScreenDestination)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Image(
                            painterResource(R.drawable.ic_comment),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = androidx.compose.ui.Modifier,
                            colorFilter = ColorFilter.tint(Color.Black)


                        )
                    }
                    Row(
                        modifier = Modifier.height(screenWidth * 0.1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = commentCount.toString(), fontFamily = gbold, fontSize = 15.sp
                        )
                        Text(text = "  yorum", fontSize = 15.sp, fontFamily = glight)
                        Spacer(modifier = Modifier.weight(1.0f))

                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                }

                Row() {
                    Text(
                        text = likeCount.toString(), fontFamily = gbold, fontSize = 15.sp
                    )
                    Text(text = " kişi beğendi", fontSize = 15.sp, fontFamily = glight)
                    Spacer(modifier = Modifier.weight(1.0f))

                }

            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            Row(
                Modifier
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {


                BasicTextField(
                    modifier = Modifier.fillMaxWidth(0.86f),
                    value = comment_text,
                    onValueChange = { comment_text = it },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (comment_text.isEmpty()) {
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
                        .clickable(enabled = if (!comment_text.isEmpty()) true else false) {
                            cs.launch {
                                if (comment_text != "") {
                                    vm.doComment(
                                        context = context,
                                        post_id = item.id,
                                        text = comment_text
                                    )
                                    commentCount = commentCount + 1
                                    comment_text = ""

                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Paylaş", color = Color.White, fontFamily = gbook, fontSize = 10.sp)
                }
            }
            Spacer(modifier = Modifier.height(screenWidth * 0.05f))

        }

    }

    if (editPost) {
        EditPostDialog(
            openDialog = editPost,
            onResponseChange = { editPost = it },
            postsVM = vmm,
            context = context,
            post_id = item.id.toString())
    }
}

@Composable
fun PostCommentCard(
    item: PostCommentsData,
    vm: PostsVM,
    navigator: DestinationsNavigator
) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    androidx.compose.material.Surface(
        elevation = 3.dp,
        modifier = Modifier
            .width(screenWidth * 1.0f)

    ) {


        Column(
            Modifier
                .background(postBackground)
                .width(width = screenWidth * 1.0f)
                .padding(horizontal = screenWidth * 0.04f)

        ) {
            Spacer(modifier = Modifier.height(screenWidth * 0.04f))

            Row() {


                Surface(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(90),
                    color = arsenicBold
                ) {


                    Row(
                        Modifier
                            .size(screenWidth * 0.12f)
                            .clip(RoundedCornerShape(90)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        //Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                        if (item.author.profilePicture != null) {
                            GlideImage(
                                imageModel = "https://telgraf.ankara.edu.tr/api/" + item.author.profilePicture
                                    ?: "",
                                // Crop, Fit, Inside, FillHeight, FillWidth, None
                                contentScale = ContentScale.Fit,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
                                contentDescription = "", tint = Color.White,
                                modifier = Modifier.size(screenWidth * 0.09f)

                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(screenWidth * 0.02f))


                Column {
                    Text(modifier = Modifier.clickable{
                        navigator.navigate(ShowProfileScreenDestination(id = item.author.id))

                    },text = item.author.name + " " + item.author.surname)
                    Spacer(modifier = Modifier.height(screenWidth * 0.02f))
                    Text(
                        text = item.createdAt.substring(0, 10) + " " + item.createdAt.substring(
                            11,
                            16
                        ), color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            Column() {
                Html(text = item.text)
                Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            }
            Spacer(modifier = Modifier.height(screenWidth * 0.04f))

        }

    }
}

@Composable
fun PostDetailCard(
    item: PostData,
    vmm: PostsVM,
    navigator: DestinationsNavigator
) {
    val currentPostId: Int? = null
    var likeCount by remember { mutableStateOf(item.likeCount) }
    var commentCount by remember { mutableStateOf(item.commentCount) }

    val vm by remember(key1 = vmm.renewk.value) { mutableStateOf(vmm) }

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val isLiked = remember { mutableStateOf(0) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val cs = rememberCoroutineScope()
    LaunchedEffect(key1 = item, block = {
        Log.d("INITIAL", ".")
        if (item.userLike) {
            isLiked.value = 1
        } else {
            isLiked.value = 0
        }
    })


    androidx.compose.material.Surface(
        elevation = 0.dp,
        modifier = Modifier
            .width(screenWidth * 1.0f)

    ) {


        Column(
            Modifier
                .background(postBackground)
                .width(width = screenWidth * 1.0f)
                .border(width = 0.11.dp, color = post_border, shape = RoundedCornerShape(0.dp))

                .padding(horizontal = screenWidth * 0.04f)

                .clickable {
                    Log.d("POST-İD", item.id.toString())

                    vm.singlePostid = item.id
                    navigator.navigate(PostDetailScreenDestination(id = item.id))
                }
        ) {
            Spacer(modifier = Modifier.height(screenWidth * 0.04f))

            Row() {

                Surface(elevation = 10.dp, shape = RoundedCornerShape(90), color = Color.White) {


                    Row(
                        Modifier
                            .size(screenWidth * 0.12f)
                            .clip(RoundedCornerShape(90)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        //   Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                        if (item.author.profilePicture != null) {
                            GlideImage(
                                imageModel = "https://telgraf.ankara.edu.tr/api/" + item.author.profilePicture,
                                contentScale = ContentScale.Fit,
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
                                contentDescription = "", tint = arsenicBold,
                                modifier = Modifier.size(screenWidth * 0.09f)

                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.width(screenWidth * 0.02f))

                Column {
                    Text(text = item.author.name + " " + item.author.surname, color = Color.Black)
                    Spacer(modifier = Modifier.height(screenWidth * 0.02f))
                    Text(
                        text = item.createdAt.substring(0, 10) + " " + item.createdAt.substring(
                            11,
                            16
                        ), color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            Column(Modifier.clickable{
                navigator.navigate(PostDetailScreenDestination(id = item.id))

            }) {

                Html(text = item.content)

            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
        }

    }

}

@Composable
fun NewPostCard(
    item: PostData,
    vmm: PostsVM,
    navigator: DestinationsNavigator
) {
    val currentPostId: Int? = null
    var likeCount by remember { mutableStateOf(item.likeCount) }
    var commentCount by remember { mutableStateOf(item.commentCount) }

    val vm by remember(key1 = vmm.renewk.value) { mutableStateOf(vmm) }

    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val isLiked = remember { mutableStateOf(0) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val cs = rememberCoroutineScope()
    LaunchedEffect(key1 = item, block = {
        Log.d("INITIAL", ".")
        if (item.userLike) {
            isLiked.value = 1
        } else {
            isLiked.value = 0
        }
    })


    androidx.compose.material.Surface(
        elevation = 0.dp,
        modifier = Modifier
            .width(screenWidth * 1.0f)

    ) {


        Column(
            Modifier
                .background(postBackground)
                .width(width = screenWidth * 1.0f)
                .border(width = 0.11.dp, color = post_border, shape = RoundedCornerShape(0.dp))
                .padding(horizontal = screenWidth * 0.04f)

        ) {
            Row(
                Modifier
                    .height(screenHeight * 0.1f)
                    .padding(horizontal = screenWidth * 0.04f)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
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

                Row(
                    Modifier
                        .height(screenWidth * 0.08f)
                        .width(screenWidth * 0.14f)
                        .clip(RoundedCornerShape(percent = 15))
                        .background(telgraf_color2)
                        .clickable(enabled = if (!vm.comment_text.isEmpty()) true else false) {
                            cs.launch {
                                if (vm.comment_text != "") {


                                    //  File file = // initialize file here

                                    //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                                    //Call<MyResponse> call = api.uploadAttachment(filePart);
                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Paylaş", color = Color.White, fontFamily = gbook, fontSize = 10.sp)
                }
            }
            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
        }

    }

}

data class PostCardEditOptions(
    var id: Int,
    var name: String
)


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditPostDialog(
    openDialog: Boolean,
    onResponseChange: (Boolean) -> Unit,
    postsVM: PostsVM,
    post_id: String,
    context: Context
) {
    var cs = rememberCoroutineScope()
    var editText by remember { mutableStateOf(postsVM.editPostText) }
    val openDialog = remember { mutableStateOf(openDialog) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .width(screenWidth * 0.8f)
            .wrapContentHeight(),

        onDismissRequest = {
            openDialog.value = false
            onResponseChange(openDialog.value)
        },
        title = {
        },
        text = {
            Column() {


                BasicTextField(
                    modifier = Modifier.fillMaxWidth(0.86f),
                    value = editText,
                    onValueChange = { editText = it },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (editText.isEmpty()) {
                                Text(
                                    text = "Gönderi Düzenle...",
                                    color = post_border,
                                    fontFamily = gbook,
                                    fontSize = 15.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.04f))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        Modifier
                            .height(screenWidth * 0.08f)
                            .width(screenWidth * 0.14f)
                            .clip(RoundedCornerShape(percent = 15))
                            .background(telgraf_color2)
                            .clickable {
                                Log.d("EDİT", editText.toString())
                                cs.launch {
                                    postsVM.editPost(
                                        context = context,
                                        post_id = post_id,
                                        post_text = editText
                                    )
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Paylaş",
                            color = Color.White,
                            fontFamily = gbook,
                            fontSize = 10.sp
                        )
                    }
                }

            }
        },
        confirmButton = {

        },
        dismissButton = {

        }
    )
}
