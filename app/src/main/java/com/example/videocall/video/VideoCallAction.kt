package com.example.videocall.video

sealed interface VideoCallAction {
    data object OnDisconnectClick:VideoCallAction
    data object JoinCall:VideoCallAction
}
