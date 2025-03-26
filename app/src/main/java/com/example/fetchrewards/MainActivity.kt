package com.example.fetchrewards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fetchrewards.presentation.screen.HomeScreen
import com.example.fetchrewards.ui.theme.FetchRewardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

            setContent {
                setContent {
                    FetchRewardsTheme {
                        HomeScreen()
                    }
                }
            }
    }
}