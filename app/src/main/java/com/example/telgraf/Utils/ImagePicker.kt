package com.example.telgraf.Utils

import android.R.attr.data
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ui.theme.gbold
import com.example.telgraf.ui.theme.gbook
import com.example.telgraf.ui.theme.telgraf_color2
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Matcher
import java.util.regex.Pattern


@Composable
fun AddPhoto(vm: PostsVM) {
    val context = LocalContext.current

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val configuration = LocalConfiguration.current
    var counter by remember { mutableStateOf(1) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .width(screenWidth * 0.19f)
                .height(screenWidth * 0.1f)
                .clip(RoundedCornerShape(5.dp))
                .background(telgraf_color2)
                .clickable {
                    launcher.launch("image/*")

                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Dosya seçin", color = Color.White, fontFamily = gbook, fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(screenWidth * 0.04f))

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                vm.bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, it)
                vm.bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            vm.bitmap.value?.let { btm ->
                if (counter == 1) {
                    try {

                        Log.d("FILE_NAME", imageUri.toString())
                        Log.d("FILE_NAME", imageUri!!.path.toString())

                        val root = Environment.getExternalStorageDirectory().toString()
                        vm.file = File(root, "sketchpad2.jpg")
                        val fOut = FileOutputStream(vm.file)

                        btm.compress(Bitmap.CompressFormat.JPEG, 15, fOut)
                        fOut.flush()
                        fOut.close()
                        MediaStore.Images.Media.insertImage(
                            context.contentResolver,
                            vm.file!!.getAbsolutePath(),
                            vm.file!!.getName(),
                            vm.file!!.getName()
                        );

                    } catch (e: Exception) {
                        Log.i("Seiggailion", "Failed to save image.")
                    }

                    Log.d("FILE_NAME", vm.file?.getName().toString())

                    vm.fileBody = MultipartBody.Part.createFormData(
                        "image",
                        vm.file?.getName(),
                        RequestBody.create(MediaType.parse("image/*"), vm.file)
                    )

                    vm.createPhoto = true
                }
                counter += 1

                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(screenWidth * 0.65f)
                )
            }

        }

    }
}
