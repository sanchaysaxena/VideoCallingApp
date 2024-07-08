package com.example.videocall.connect

sealed interface ConnectAction {
    data class OnNameChange(val name:String):ConnectAction
    data object OnConnectClick:ConnectAction
}

//in MVI architecture we define all the actions a user can potentially do on a screen in this file