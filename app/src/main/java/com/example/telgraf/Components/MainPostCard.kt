package com.example.telgraf.Components

import com.example.telgraf.Network.Models.AnnouncementsModelData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.telgraf.Network.Models.JobAnnouncementsData
import com.example.telgraf.Network.Models.PostCommentsData
import com.example.telgraf.Network.Models.PostData
import com.example.telgraf.R
import com.example.telgraf.Utils.LinkText
import com.example.telgraf.Utils.LinkTextData
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.user_id
import com.example.telgraf.n.Html
import com.example.telgraf.n.destinations.PostDetailScreenDestination
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun AnnouncementCard(
    item: AnnouncementsModelData,
) {

    Log.d("ITEM_ID", item.id.toString())
    val currentPostId: Int? = item.id


    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var comment_text by remember { mutableStateOf("") }
    val isLiked = remember { mutableStateOf(0) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(postBackground)
            .width(width = screenWidth * 1.0f)
            .padding(horizontal = screenWidth * 0.02f)
            .clickable {
                isExpanded = !isExpanded
            }
            .animateContentSize()

    ) {
        Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(screenWidth * 0.02f))

        Row() {

            Spacer(modifier = Modifier.width(screenWidth * 0.02f))

            Column(modifier = Modifier.fillMaxWidth(0.85f)) {
                Text(
                    text = item.title, color = Color.Black
                )
                Spacer(modifier = Modifier.height(screenWidth * 0.02f))
                Text(
                    text = item.createdAt.substring(0, 10) + " " + item.createdAt.substring(
                        11,
                        16
                    ), color = Color.Black
                )
            }
            Row() {
                Spacer(modifier = Modifier.width(screenWidth * 0.02f))
                if (isExpanded) {
                    Icon(
                        painter = painterResource(id = R.drawable.arroww_down),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            isExpanded = !isExpanded
                        },
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.arroww_upp),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            isExpanded = !isExpanded
                        },
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1.0f))


        }
        Spacer(modifier = Modifier.height(screenWidth * 0.02f))
        if (isExpanded) {
            Column(Modifier.padding(horizontal = screenWidth * 0.02f)) {

                Html(text = item.content)
                Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            }
        }
        Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())

    }

}


@Composable
fun JobAnnouncementsCard(
    item: JobAnnouncementsData,
) {

    Log.d("ITEM_ID", item.id.toString())
    val currentPostId: Int? = item.id


    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    var comment_text by remember { mutableStateOf("") }
    val isLiked = remember { mutableStateOf(0) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(postBackground)
            .width(width = screenWidth * 1.0f)
            .padding(horizontal = screenWidth * 0.04f)
            .clickable {
                isExpanded = !isExpanded
            }
    ) {
        Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(screenWidth * 0.02f))

        Row() {
            Spacer(modifier = Modifier.width(screenWidth * 0.02f))
            if (isExpanded) {
                Icon(
                    painter = painterResource(id = R.drawable.arroww_down),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    },
                    tint = Color.Black
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.arroww_upp),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    },
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(screenWidth * 0.02f))

            Column {
                Text(
                    text = item.title, color = Color.Black
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


        }
        Spacer(modifier = Modifier.height(screenWidth * 0.02f))
        if (isExpanded) {
            Column {

                Html(text = item.content)
                Spacer(modifier = Modifier.height(screenWidth * 0.02f))

            }
        }
        Divider(color = post_border, thickness = (0.51).dp, modifier = Modifier.fillMaxWidth())

    }

}