package com.example.telgraf.Components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.telgraf.Network.Models.NotificationsModelData
import com.example.telgraf.R
import com.example.telgraf.n.destinations.PostDetailScreenDestination
import com.example.telgraf.n.destinations.ShowProfileScreenDestination
import com.example.telgraf.ui.theme.arsenicBold
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage

//TODO show profile id ile geçilcek user ismine basınca user id ile
//
@Composable
fun NotificationCard(
    navigator: DestinationsNavigator,
    item: NotificationsModelData
) {
    val configuration = LocalConfiguration.current

    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Row {

        Surface(elevation = 10.dp, shape = RoundedCornerShape(90), color = Color.White) {


            Row(
                Modifier
                    .size(screenWidth * 0.12f)
                    .clip(RoundedCornerShape(90))
                    .clickable {
                        navigator.navigate(ShowProfileScreenDestination(id = item.other.id))

                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                //   Log.d("PROFILE_PIC", item.author.profilePicture.toString())
                if (item.other.profilePicture != null) {
                    GlideImage(
                        imageModel = "https://telgraf.ankara.edu.tr/api/" + item.other.profilePicture,
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
            Row() {
                Text(
                    modifier = Modifier.clickable {
                        navigator.navigate(ShowProfileScreenDestination(id = item.other.id))
                    },
                    text = item.other.name + " " + item.other.surname, color = Color.Black
                )
                Text(
                    modifier = Modifier.clickable {
                        navigator.navigate(PostDetailScreenDestination(id = item.postId))
                    },
                    text = if (item.notificationtype == "like") {
                        "Gönderini beğendi"
                    } else {
                        "Gönderine yorum yaptı"
                    }, color = Color.Black

                )
            }

            Spacer(modifier = Modifier.height(screenWidth * 0.02f))
            Text(
                text = item.createdAt.substring(0, 10) + " " + item.createdAt.substring(
                    11,
                    16
                ), color = Color.Black
            )
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Log.d("ITEM_AUTHOR_ID", item.other.id.toString())

    }

}