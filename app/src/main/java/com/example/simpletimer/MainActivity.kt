package com.example.simpletimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.simpletimer.navigation.TimerNavHost
import com.example.simpletimer.ui.theme.SimpleTimerTheme

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            SimpleTimerTheme {
                TimerNavHost(navController)
            }
        }
    }
}