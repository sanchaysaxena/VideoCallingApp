package com.example.videocall

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videocall.connect.ConnectScreen
import com.example.videocall.connect.ConnectViewModel
import com.example.videocall.ui.theme.VideoCallTheme
import com.example.videocall.video.CallState
import com.example.videocall.video.VideoCallScreen
import com.example.videocall.video.VideoCallViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideoCallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController= rememberNavController()
                    NavHost(navController = navController, startDestination = ConnectRoute, modifier = Modifier.padding(innerPadding) ){
                        composable<ConnectRoute> {
                            val viewmodel= koinViewModel<ConnectViewModel>()
                            val state=viewmodel.state
                            
                            LaunchedEffect(key1 = state.isConnected) {
                                if(state.isConnected) {
                                    navController.navigate(VideoCallRoute) {
                                        popUpTo(ConnectRoute) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            ConnectScreen(state = state, onAction = viewmodel::onAction )
                        }
                        composable<VideoCallRoute> {
                            val viewModel= koinViewModel<VideoCallViewModel>()
                            val state=viewModel.state
                            
                            LaunchedEffect(key1 = state.callState) {
                                if(state.callState==CallState.ENDED){
                                    navController.navigate(ConnectRoute){
                                        popUpTo(VideoCallRoute){
                                            inclusive=true
                                        }
                                    }
                                }
                            }
                            VideoCallScreen(state = state, onAction = viewModel::onAction)
                        }
                    }
                }
            }
        }
    }
}

@Serializable
data object ConnectRoute
@Serializable
data object VideoCallRoute
