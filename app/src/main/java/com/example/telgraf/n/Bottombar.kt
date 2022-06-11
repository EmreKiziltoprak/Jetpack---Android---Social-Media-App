package com.example.telgraf.n

import android.graphics.Typeface
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.example.telgraf.R
import com.example.telgraf.n.destinations.*
import com.example.telgraf.ui.theme.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun Bottombar(navigator: DestinationsNavigator) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    androidx.compose.material.Surface(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWidth * 0.15f),
        shape = RoundedCornerShape(percent = 0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenWidth * 0.15f),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Divider(thickness = (0.5).dp, color = arsenic, modifier = Modifier.fillMaxWidth())

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(screenWidth * 0.33f)
                        .clickable {
                            navigator.navigate(HomeScreenDestination)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(R.drawable.ic_home),
                        contentDescription = "",
                        modifier = androidx.compose.ui.Modifier,
                        tint = Color.Black
                    )
                    Text(text = "Ana Sayfa", fontFamily = gbook, fontSize = 14.sp)

                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(screenWidth * 0.33f)
                        .clickable {
                            navigator.navigate(AnnouncementsScreenDestination)

                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painterResource(R.drawable.ic_promotion_1),
                        contentDescription = "",
                        modifier = androidx.compose.ui.Modifier,
                        tint = Color.Black

                    )
                    Text(text = "Duyurular", fontFamily = gbook, fontSize = 14.sp)

                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .width(screenWidth * 0.33f)
                        .clickable {
                            navigator.navigate(JAScreenDestination)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(R.drawable.ic_recruitment_1),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = androidx.compose.ui.Modifier
                    )
                    Text(text = "İlanlar", fontFamily = gbook, fontSize = 14.sp)

                }

            }

        }

    }
}

@Composable
fun Html(text: String) {

    AndroidView(
        factory = {

                context ->

            val textView = TextView(context)
            textView.setTextColor(android.graphics.Color.parseColor("#020427"))
            textView.apply {
                setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
                setMovementMethod(LinkMovementMethod.getInstance())
                val font = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf")
                typeface = font
                setLineSpacing(2.0f, 1.4f)

            }
        })
}