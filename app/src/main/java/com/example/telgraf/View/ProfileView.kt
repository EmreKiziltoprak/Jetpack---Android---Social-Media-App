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
import com.example.telgraf.Network.Models.EditMainUserModel
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


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileView(navigator: DestinationsNavigator, vmz: UserViewModel) {
    var vm2: PostsVM by remember { mutableStateOf(PostsVM())}
    var vm by remember { mutableStateOf(vmz) }
    var emai by remember { mutableStateOf(TextFieldValue("")) }
    var pas by remember { mutableStateOf(TextFieldValue("")) }
    val snackbarHostState = remember { SnackbarHostState() }
    val filePermissions =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val context = LocalContext.current

    //FOR IMAGE SELECTING
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var counter by remember { mutableStateOf(1) }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
//GET USER INFO
    LaunchedEffect(key1 = Unit, block = {
        filePermissions.launchMultiplePermissionRequest()



    })


    Scaffold(content = {
        if (vmz.isLoaded == true) {
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
                            filePermissions.launchMultiplePermissionRequest()

                            launcher.launch("image/*")
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    //Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                    if (vm2.bitmap.value != null) {
                        Image(
                            bitmap = vm2.bitmap.value!!.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(screenWidth * 0.65f)
                        )
                    } else if (vmz.userInfo?.profilePicture != null) {
                        GlideImage(
                            imageModel = "https://telgraf.ankara.edu.tr/api/" + vmz.userInfo!!.profilePicture,
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

                if(vm2.ppsuccess.value==1) {
                    Text(text = "Profil fotoğrafı başarıyla değiştirildi")
                    val handler = Handler()
                    handler.postDelayed(Runnable {

                        cs.launch {
                            vm2.ppsuccess.value = 0
                           vm.getUserInfo(context = context)
                        }
                    }, 700)
                }
                else if(vm2.ppsuccess.value==-1){
                    Text(text = "Profil fotoğrafı değiştirilemedi tekrar deneyin")
                    val handler = Handler()
                    handler.postDelayed(Runnable {

                        cs.launch {
                            vm2.ppsuccess.value = 0
                            vm.getUserInfo(context = context)
                        }
                    }, 700)
                }
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
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = telgraf_color2,
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
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = telgraf_color2,
                        textColor = arsenic
                    )
                )
                Spacer(modifier = Modifier.height(height = screenWidth * 0.02f))
                OutlinedTextField(
                    modifier = Modifier
                        .width(screenWidth * 0.91f),
                    value = vm.bio,
                    label = {
                        Text(
                            text = "Hakkında",
                            color = arsenic,
                            fontFamily = glight
                        )
                    },
                    onValueChange = {
                        vm.bio = it
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = telgraf_color2,
                        textColor = arsenic
                    )
                )

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
                text = vm.userInfo?.name + " " + (vm.userInfo?.surname ?: ""),
                color = Color.Black,
                fontFamily = gbook,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.weight(1.0f))
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_done_24),
                contentDescription = "",
                modifier = Modifier.clickable {
                    cs.launch {

                        vmz.editUserInfo(
                            context = context,
                            body = EditMainUserModel(
                                bio = vm.bio.text,
                                name = vm.name.text,
                                surname = vm.surname.text
                            )
                        )

                        val handler = Handler()
                        handler.postDelayed({
                            cs.launch {
                                // yourMethod();
                                vm.getUserInfo(context = context)
                            }
                        }, 500) //5 seconds

                    }
                },
                tint = telgraf_color2
            )
            Spacer(modifier = Modifier.width(screenWidth * 0.06f))

        }
    })

    imageUri?.let {
        if (Build.VERSION.SDK_INT < 28) {
            vm2.bitmap.value = MediaStore.Images
                .Media.getBitmap(context.contentResolver, it)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, it)
            vm2.bitmap.value = ImageDecoder.decodeBitmap(source)
        }
        vm2.bitmap.value?.let { btm ->
            try {

                Log.d("FILE_NAME", imageUri.toString())
                Log.d("FILE_NAME", imageUri!!.path.toString())

                val root = Environment.getExternalStorageDirectory().toString()
                vm2.file = File(root, "sketchpad2.jpg")
                val fOut = FileOutputStream(vm2.file)

                btm.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.flush()
                fOut.close()
                MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    vm2.file!!.getAbsolutePath(),
                    vm2.file!!.getName(),
                    vm2.file!!.getName()
                );

            } catch (e: Exception) {
                Log.i("Seiggailion", "Failed to save image.")
            }

            Log.d("FILE_NAME", vm2.file?.getName().toString())

            vm2.fileBody = MultipartBody.Part.createFormData(
                "image",
                vm2.file?.getName(),
                RequestBody.create(MediaType.parse("image/*"), vm2.file)
            )

        }

        counter += 1

        cs.launch {
            vm2.changePP()
            imageUri = null
            vm2.bitmap.value = null
            vmz.userInfo = null
            val handler = Handler()
            handler.postDelayed(Runnable {
                // yourMethod();
                cs.launch {

                    vm.getUserInfo(context = context)
                }
            }, 700)

        }


    }

}