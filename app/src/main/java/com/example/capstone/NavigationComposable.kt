package com.example.capstone

import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    sharedPreferences: SharedPreferences,
    database: AppDatabase
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController, sharedPreferences)
        }
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController, sharedPreferences)
        }
    }
}