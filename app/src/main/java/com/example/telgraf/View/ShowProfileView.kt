package com.example.telgraf.View


import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.Components.PostCard
import com.example.telgraf.Network.Models.EditMainUserModel
import com.example.telgraf.Network.Models.PostData
import com.example.telgraf.R
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.ViewModels.user_id
import com.example.telgraf.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.*


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ShowProfileView(navigator: DestinationsNavigator, id: Int) {

    var postsVM = PostsVM()
    var userViewModel = UserViewModel()
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current
    val limit = remember { mutableStateOf(5) }

    //GET USER INFO
    LaunchedEffect(key1 = Unit, block = {
        postsVM.userpostst.value = mutableListOf<PostData>()
        userViewModel.getCustomUser(context = context, id = id)
        postsVM.getUserPosts(limit = limit.value, reset = true, userId = id)
        Log.d("POSTS_OF_USER", postsVM.postst.value.toString())
    })


    Scaffold(content = {
        if (userViewModel.isLoaded == true) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    Modifier
                        .size(screenWidth * 0.2f)
                        .clip(RoundedCornerShape(90))
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(90))
                        .clickable {

                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                    if (userViewModel.userInfo?.profilePicture != null) {
                        GlideImage(
                            imageModel = "https://telgraf.ankara.edu.tr/api/" + userViewModel.userInfo!!.profilePicture,
                            // Crop, Fit, Inside, FillHeight, FillWidth, None
                            contentScale = ContentScale.FillBounds,
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
                            contentDescription = "", tint = Color.Black,
                            modifier = Modifier.size(screenWidth * 0.2f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(screenWidth * 0.04f))

                //NAME
                Text(
                    modifier = Modifier
                        .width(screenWidth * 0.91f),
                    text = userViewModel.userInfo?.name + " " + (userViewModel.userInfo?.surname ?: ""),
                    fontFamily = gbook,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))

                userViewModel.userInfo?.bio?.let { it1 ->
                    Text(
                        modifier = Modifier
                            .width(screenWidth * 0.91f),
                        text = it1,
                        fontFamily = gbook,
                        fontSize = 17.sp
                    )
                }



                if (postsVM.userpostst.value != null) {
                    LazyColumn(modifier = Modifier.height(screenHeight * 0.85f)) {
                        itemsIndexed(postsVM.userpostst.value) { index, item ->

                            PostCard(item = item, vmm = postsVM, navigator = navigator)

                            Log.d("LİST_SİZE", (postsVM.postsSize ?: 0).toString())
                            Log.d("ındex", (index).toString())

                            if (((index + 1) >= postsVM.postsSize!!) && (postsVM.postsSize!!< postsVM.total!!)) {

                                if (postsVM.total ?: 0 > (index + 1)) {
                                    postsVM.getPosts(limit = postsVM.postsSize!! + 5)
                                }
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier
                                        .size(15.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                        }
                    }
                }
            }
        }
    }, topBar = {
        Row(
            Modifier
                .fillMaxWidth()
                .height(screenWidth * 0.15f),
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
            Text(
                text = "Kullanıcı",
                color = Color.Black,
                fontFamily = gbook,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Spacer(modifier = Modifier.width(screenWidth * 0.06f))

        }
    })


}