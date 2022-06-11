package com.example.telgraf.View


import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auth0.android.jwt.JWT
import com.example.telgraf.Components.PostCard
import com.example.telgraf.R
import com.example.telgraf.Utils.TimeFunctions.DateTest
import com.example.telgraf.ViewModels.*
import com.example.telgraf.n.Bottombar
import com.example.telgraf.n.destinations.*
import com.example.telgraf.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun HomeView(navigator: DestinationsNavigator, vm: PostsVM, uvm: UserViewModel) {
    var colorList = mutableListOf<Color>()
    val context = LocalContext.current

    colorList.add(telgraf_color_gradient)
    colorList.add(telgraf_color_gradient2)
    var search by remember { mutableStateOf(TextFieldValue("")) }

    val configuration = LocalConfiguration.current
    val cs = rememberCoroutineScope()
    val limit = remember { mutableStateOf(5) }
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var isRefreshing = rememberSwipeRefreshState(isRefreshing = false)

    LaunchedEffect(key1 = Unit, block = {

        context.let {
            getToken(context = it).collect { value ->
                token.value = value
                Log.d(
                    "GET_TOKEN", token.value
                )

                if (token.value!=""){


                    val jwt = JWT(token.value)

                    var claim: String? = jwt.getClaim("userId").asString()
                    var exp: String? = jwt.getClaim("exp").asString()

                    var date = DateTest(expDate = exp?:"")
                    date.compareDates()

                    user_id.value = claim.toString()
                    if (claim != null) {
                        uvm.user_id_flow.value = claim.toInt()
                    }

                    vm.getPosts(limit = limit.value, reset = true)
                    uvm.getNotifications()
                    uvm.getUserInfo(context = context)



                }
            }



        }
        vm.getPosts(limit = limit.value, reset = true)
        uvm.getNotifications()
        uvm.getUserInfo(context = context)

    })

    BackHandler(true) {
        // Or do nothing
    }

    Scaffold(content = {

        SwipeRefresh(state = isRefreshing, onRefresh = {
            cs.launch {
                vm.getPosts(limit = limit.value, reset = true)
                uvm.getNotifications()
                uvm.getUserInfo(context = context)
            }
        }) {


            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row(
                    Modifier
                        .padding(start = screenWidth * 0.04f)
                        .fillMaxWidth()
                        .height(screenWidth * 0.15f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    BasicTextField(
                        modifier = Modifier
                            .height(screenWidth * 0.15f)
                            .width(screenWidth * 0.5f),
                        value = search,
                        onValueChange = { search = it },
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (search.text.isEmpty()) {
                                    Text(
                                        text = "Arama Yap...",
                                        color = post_border,
                                        fontFamily = gbook,
                                        fontSize = 15.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Column(
                        Modifier
                            .width(screenWidth * 0.11f)
                            .clickable {
                                navigator.navigate(AddPostScreenDestination)
                            },
                        verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            painterResource(R.drawable.ic_baseline_add_24),
                            contentDescription = "",
                            modifier = androidx.compose.ui.Modifier.size(screenWidth * 0.08f),
                            tint = arsenic

                        )

                    }
                    Column(
                        Modifier
                            .width(screenWidth * 0.11f)
                            .clickable {
                                navigator.navigate(NotificationsScreenDestination)
                            },
                        verticalArrangement = Arrangement.Center
                    ) {

                        if (uvm.notifcationsList != null) {
                            Icon(
                                modifier = Modifier.clickable {
                                    navigator.navigate(NotificationsScreenDestination)
                                },
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_favorite_24),
                                contentDescription = "Some icon",
                                tint = Color.Red
                            )
                        } else {
                            Icon(
                                modifier = Modifier.clickable {
                                    navigator.navigate(NotificationsScreenDestination)
                                },
                                imageVector = ImageVector.vectorResource(id = R.drawable.favorite_24),
                                contentDescription = "Some icon",
                                tint = Color.Black
                            )
                        }
                    }
                    Column(
                        Modifier
                            .width(screenWidth * 0.1f)
                            .clickable {
                                navigator.navigate(ProfileScreenDestination)

                            },
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Icon(
                            painterResource(R.drawable.ic_user_1),
                            contentDescription = "",
                            modifier = androidx.compose.ui.Modifier.size(screenWidth * 0.05f),
                            tint = arsenic

                        )

                    }

                    Column(
                        Modifier
                            .padding(end = screenWidth * 0.02f)
                            .width(screenWidth * 0.07f)
                            .clickable {
                                navigator.navigate(MemorandumsScreenDestination)
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Icon(
                            painterResource(R.drawable.ic_logout),
                            contentDescription = "",
                            modifier = androidx.compose.ui.Modifier
                                .size(screenWidth * 0.045f)
                                .clickable {

                                    user_id.value = ""
                                    uvm.name = TextFieldValue("")
                                    uvm.surname = TextFieldValue("")
                                    uvm.schooln = TextFieldValue("")
                                    uvm.institude = TextFieldValue("")
                                    uvm.bio = TextFieldValue("")
                                    uvm.emai = TextFieldValue("")
                                    uvm.pas = TextFieldValue("")
                                    uvm.isAuthing.value = false

                                    cs.launch {
                                        saveToken(
                                            context = context,
                                            token = ""
                                        )
                                        navigator.navigate(LoginScreenDestination)
                                    }
                                },
                            tint = arsenic

                        )

                    }
                }


                if (vm.postst.value != null) {
                    LazyColumn(modifier = Modifier.height(screenHeight * 0.85f)) {
                        itemsIndexed(vm.postst.value) { index, item ->

                            if ((search.text != "") && ((item.content.toString()
                                    .lowercase(Locale.getDefault())).contains(
                                        search.text.lowercase(Locale.getDefault())
                                    ))
                            ) {
                                PostCard(item = item, vmm = vm, navigator = navigator)
                            }
                            if (search.text == "") {
                                PostCard(item = item, vmm = vm, navigator = navigator)
                            }

                            Log.d("LİST_SİZE", (vm.postsSize ?: 0).toString())
                            Log.d("ındex", (index).toString())

                            if (((index + 1) >= vm.postsSize!!) && (vm.postsSize!! < vm.total!!)) {

                                if (vm.total ?: 0 > (index + 1)) {
                                    vm.getPosts(limit = vm.postsSize!! + 5)
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
                Spacer(modifier = Modifier.height(screenWidth * 0.2f))

            }
        }
    }, bottomBar = { Bottombar(navigator = navigator) })
}