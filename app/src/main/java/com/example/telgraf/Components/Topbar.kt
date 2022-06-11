package com.example.telgraf.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.telgraf.R
import com.example.telgraf.ui.theme.arsenic
import com.example.telgraf.ui.theme.gbook
import com.example.telgraf.ui.theme.telgraf_color
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
fun Topbar(navigator: DestinationsNavigator,
           name: String = "",
           color: Color = telgraf_color,
            iconColor: Color =arsenic) {
    val configuration = LocalConfiguration.current

    val context = LocalContext.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Row(
        Modifier
            .fillMaxWidth()
            .height(screenWidth * 0.15f)
            .background(color),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(screenWidth * 0.06f))

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
            contentDescription = "",
            modifier = Modifier.clickable { navigator.navigateUp() },
            tint = iconColor
        )
       Spacer(modifier = Modifier.width(screenWidth * 0.04f))
        Text(text = name, color = iconColor, fontFamily = gbook, fontSize = 17.sp)

    }
}