package com.example.videocall.di
import com.example.videocall.VideoCallingApp
import com.example.videocall.connect.ConnectViewModel
import com.example.videocall.video.VideoCallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    factory {
        val app = androidContext().applicationContext as VideoCallingApp
        app.client
    }
 
    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoCallViewModel)
}