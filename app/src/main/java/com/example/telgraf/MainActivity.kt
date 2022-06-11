package com.example.telgraf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.telgraf.View.PostDetailView
import com.example.telgraf.View.ProfileView
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.UserViewModel
import com.example.telgraf.n.*
import com.example.telgraf.n.destinations.*
import com.example.telgraf.ui.theme.TelgrafTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelgrafTheme {

                var vm : UserViewModel = UserViewModel()
                var postv : PostsVM = PostsVM()

                // A surface container using the 'background' color from the theme

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    DestinationsNavHost(navGraph = NavGraphs.root){

                        composable(LoginScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            LoginScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }

                        composable(MainLoginScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            MainLoginScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }
                        composable(RegisterWithSchoolScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            RegisterWithSchoolScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }

                        composable(RegisterScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            RegisterScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }
                        composable(HomeScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            HomeScreen(
                                navigator = destinationsNavigator,
                                vm = postv,
                                uvm = vm
                            )
                        }
                        composable(AddPostScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            AddPostScreen(
                                navigator = destinationsNavigator,
                                vm = postv
                            )
                        }
                        composable(MemorandumsScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            MemorandumsScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }

                        composable(NotificationsScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            NotificationsScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }

                        composable(AnnouncementsScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            AnnouncementsScreen(
                                navigator = destinationsNavigator,
                                vm = vm
                            )
                        }

                        composable(ProfileScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            ProfileView(
                                navigator = destinationsNavigator,
                                vmz = vm
                            )
                        }

                        composable(PostDetailScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                            PostDetailView(
                                navigator = destinationsNavigator,
                                vm = postv,
                                singlePostid = navArgs.id
                            )
                        }


                    }
                }
            }
        }
    }
}
