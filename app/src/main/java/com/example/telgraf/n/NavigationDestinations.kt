package com.example.telgraf.n

import androidx.compose.runtime.Composable
import com.example.telgraf.View.*
import com.example.telgraf.ViewModels.PostsVM
import com.example.telgraf.ViewModels.UserViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(start = true)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {

    LoginView(navigator = navigator, vm = vm) }


@Destination(start = false)
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {

    RegisterView(navigator = navigator, vm = vm) }



@Destination(start = false)
@Composable
fun RegisterWithSchoolScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    RegisterWithSchoolView(navigator = navigator, vm = vm) }



@Destination(start = false)
@Composable
fun AnnouncementsScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    AnnouncementsView(navigator = navigator, vm = vm) }


@Destination(start = false)
@Composable
fun JAScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel = UserViewModel()
) {
    JobAnnouncementsView(navigator = navigator, vm = vm) }



@Destination(start = false)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    vm: PostsVM,
    uvm: UserViewModel
) {
    HomeView(navigator = navigator, vm = vm, uvm = uvm) }


@Destination(start = false)
@Composable
fun MemorandumsScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    MemorandumsView(navigator = navigator, vm = vm) }


@Destination(start = false)
@Composable
fun NotificationsScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    NotificationsView(navigator = navigator, vm = vm) }


@Destination(start = false)
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    ProfileView(navigator = navigator, vmz = vm) }

@Destination(start = false)
@Composable
fun PostDetailScreen(
    navigator: DestinationsNavigator,
    vm: PostsVM,
    id: Int
) {
    PostDetailView(navigator = navigator, vm = vm, singlePostid = id) }

@Destination(start = false)
@Composable
fun MainLoginScreen(
    navigator: DestinationsNavigator,
    vm: UserViewModel
) {
    MainLoginView(navigator = navigator, vm = vm) }

@Destination(start = false)
@Composable
fun AddPostScreen(
    navigator: DestinationsNavigator,
    vm: PostsVM
) {
    NewPostView(navigator = navigator, vm = vm) }

@Destination(start = false)
@Composable
fun ShowProfileScreen(
    navigator: DestinationsNavigator,
    id: Int
) {
    ShowProfileView(navigator = navigator, id = id) }

@Destination(start = false)
@Composable
fun ForgotPasswordS(
    navigator: DestinationsNavigator
) {
    ForgotPasswordView(navigator = navigator) }