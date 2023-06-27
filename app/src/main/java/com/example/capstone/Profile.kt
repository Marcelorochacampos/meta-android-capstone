package com.example.capstone

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Profile(navController: NavHostController, sharedPreferences: SharedPreferences) {
    val firstName: String = sharedPreferences.getString("FirstName", "N/A") ?: "N/A"
    val lastName: String = sharedPreferences.getString("LastName", "") ?: "N/A"
    val emailAddress: String = sharedPreferences.getString("EmailAddress", "") ?: "N/A"
    Column() {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "App logo", modifier = Modifier.size(80.dp))
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Personal information")
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 20.dp)
        ) {
            Text(text = "First name: ", fontSize = 18.sp)
            Text(text = firstName)
            Text(text = "Last name: ", fontSize = 18.sp)
            Text(text = lastName)
            Text(text = "Email address: ", fontSize = 18.sp)
            Text(text = emailAddress)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Button(
                onClick = {
                    sharedPreferences.edit().putBoolean("IsAuthenticated", false).apply()
                    sharedPreferences.edit().putString("FirstName", "").apply()
                    sharedPreferences.edit().putString("LastName", "").apply()
                    sharedPreferences.edit().putString("EmailAddress", "").apply()
                    navController.navigate(Onboarding.route) {
                        popUpTo(Onboarding.route)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF4CE14))
            ) {
                Text(text="Log out")
            }
        }
    }
}