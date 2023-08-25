package com.example.simpletimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.simpletimer.screens.EnterTimeForm
import com.example.simpletimer.screens.TimerList

@Composable
fun TimerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "timerList"
    ) {
        composable(route = "timerList") {
            TimerList(navController)
        }

        composable(route = "newTimerForm") {
            EnterTimeForm(navController)
        }
    }
}